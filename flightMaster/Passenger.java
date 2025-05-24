package flightMaster;

public class Passenger extends Person {
    private String ticketNumber;
    private String seatNumber;

  

    // Full constructor with name, id, age, ticketNumber, seatNumber
    public Passenger(String name, String id, int age, String ticketNumber, String seatNumber) {
        super(name, id, age);
        this.ticketNumber = ticketNumber;
        this.seatNumber = seatNumber;
    }

    // Getters and setters
    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return super.toString() + ", Ticket Number: " + ticketNumber + ", Seat Number: " + seatNumber;
    }
}
