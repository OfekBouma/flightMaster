package flightMaster;

import flightMaster.structures.BoardingQueue;
import flightMaster.structures.DeboardingStack;

public class Plane implements Schedulable {
    private String id;
    private String model;
    private int rows;
    private int cols;
    private Passenger[][] seats;
    private Schedule[] schedules = new Schedule[10]; // max 10 scheduled flights per day
    private DeboardingStack deboardingStack = new DeboardingStack();

    public Plane(String id, String model, int rows, int cols) {
        this.id = id;
        this.model = model;
        this.rows = rows;
        this.cols = cols;
        this.seats = new Passenger[rows][cols];
    }

    // Convert seat label (e.g. "12C") to row and col indexes
    private int[] seatLabelToIndex(String seatLabel) {
        seatLabel = seatLabel.toUpperCase().trim();
        int row = 0;
        int i = 0;
        while (i < seatLabel.length() && Character.isDigit(seatLabel.charAt(i))) {
            i++;
        }
        if (i == 0) return null;

        try {
            row = Integer.parseInt(seatLabel.substring(0, i)) - 1;
        } catch (NumberFormatException e) {
            return null;
        }

        if (i >= seatLabel.length()) return null;
        char colChar = seatLabel.charAt(i);
        int col = colChar - 'A';

        if (row < 0 || row >= rows || col < 0 || col >= cols) return null;

        return new int[]{row, col};
    }

    public boolean assignSeatByLabel(String seatLabel, Passenger passenger) {
        int[] indexes = seatLabelToIndex(seatLabel);
        
        if (indexes == null) return false;

        int row = indexes[0];
        int col = indexes[1];

        if (seats[row][col] == null) {
            seats[row][col] = passenger;
            return true;
        }
        return false;
    }

    public Passenger getPassengerAtLabel(String seatLabel) {
        int[] indexes = seatLabelToIndex(seatLabel);
        if (indexes == null) return null;
        return seats[indexes[0]][indexes[1]];
    }

    // Boarding logic: fills seats row-by-row and stores boarding order in stack
    public void boardPassengers(BoardingQueue queue) {
        System.out.println("Boarding passengers...");
        Passenger passenger;
        while(!queue.isEmpty()) {
        	passenger=queue.dequeue();
        	deboardingStack.push(passenger);
        	this.assignSeatByLabel(passenger.getTicket().getSeatNumber(),passenger);
        }
        if (!queue.isEmpty()) {
            System.out.println(" Not enough seats for all passengers!");
        }
    }

    // Deboarding in reverse boarding order
    public void deboardPassengers() {
        System.out.println(" Deboarding passengers in reverse boarding order...");
        while (!deboardingStack.isEmpty()) {
            Passenger p = deboardingStack.pop();
            for(int i=0;i<this.rows;i++)
            	for(int j=0;j<this.cols;j++) {
            		if(p.equals(this.seats[i][j])){
            			this.seats[i][j]=null;
            		}
            	}
            System.out.println("Deboarded: " + p.getName());
        }
    }

    public void printSeatMap() {
        System.out.println("\n Seat Map:");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (seats[r][c] != null) {
                    System.out.print("[" + seats[r][c].getName() + "] ");
                } else {
                    System.out.print("[Empty] ");
                }
            }
            System.out.println();
        }
    }

    // Schedulable
    @Override
    public boolean isAvailable(Schedule requested) {
        for (Schedule s : schedules) {
            if (s != null && s.overlapsWith(requested)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Schedule[] getSchedule() {
        return schedules;
    }

    public boolean assignToSchedule(Schedule newSchedule) {
        if (!isAvailable(newSchedule)) {
            return false;
        }
        for (int i = 0; i < schedules.length; i++) {
            if (schedules[i] == null) {
                schedules[i] = newSchedule;
                return true;
            }
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }
}
