package flightMaster;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import flightMaster.structures.*;

public class Menu {

    // Removed pilots ArrayList:
    // private static ArrayList<Pilot> pilots = new ArrayList<>();

    private static ArrayList<Plane> planes = new ArrayList<>();
    private static PassengerList passengers = new PassengerList();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static CrewList crewList = new CrewList();

    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\n--- FlightMaster Menu ---");
            System.out.println("1. Add Pilot");
            System.out.println("2. Add Plane");
            System.out.println("3. Add Passenger");
            System.out.println("4. Assign Schedule to Pilot");
            System.out.println("5. Assign Schedule to Plane");
            System.out.println("6. Assign Passenger to Plane Seat");
            System.out.println("7. Show Pilot Schedules");
            System.out.println("8. Show Plane Schedules");
            System.out.println("9. List All Passengers");
            System.out.println("10. List Available Crew Members");
            System.out.println("11. List All Planes");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> addPilot(scanner);
                case "2" -> addPlane(scanner);
                case "3" -> addPassenger(scanner);
                case "4" -> assignScheduleToPilot(scanner);
                case "5" -> assignScheduleToPlane(scanner);
                case "6" -> assignPassengerToPlane(scanner);
                case "7" -> showPilotSchedules(scanner);
                case "8" -> showPlaneSchedules(scanner);
                case "9" -> listAllPassengers();
                case "10" -> crewList.listAllCrewMembers();
                case "11" -> listAllPlanes();
                case "0" -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
    }

    private static void addPilot(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Role: ");
        String role = scanner.nextLine();
        System.out.print("Employee ID: ");
        String empId = scanner.nextLine();
        System.out.print("License Number: ");
        String license = scanner.nextLine();
        System.out.print("Flight Rating: ");
        String rating = scanner.nextLine();

        Pilot pilot = new Pilot(name, id, age, role, empId, license, rating);
        crewList.add(pilot);  // Add pilot to CrewList instead of ArrayList
        System.out.println("Pilot added!");
    }

    private static void addPlane(Scanner scanner) {
        System.out.print("Plane ID: ");
        String id = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Rows: ");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.print("Columns: ");
        int cols = Integer.parseInt(scanner.nextLine());

        Plane plane = new Plane(id, model, rows, cols);
        planes.add(plane);
        System.out.println("Plane added!");
    }

    private static void addPassenger(Scanner scanner) {
        System.out.print("Passenger Name: ");
        String name = scanner.nextLine();
        System.out.print("Passenger ID: ");
        String id = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Ticket Number: ");
        String ticket = scanner.nextLine();
        System.out.print("Seat (e.g. 12A): ");
        String seat = scanner.nextLine();

        Passenger passenger = new Passenger(name, id, age, ticket, seat);
        passengers.add(passenger);
        System.out.println("Passenger added!");
    }

    private static Schedule inputSchedule(Scanner scanner) {
        try {
            System.out.print("Enter start time (yyyy-MM-dd HH:mm): ");
            LocalDateTime start = LocalDateTime.parse(scanner.nextLine(), dtf);
            System.out.print("Enter end time (yyyy-MM-dd HH:mm): ");
            LocalDateTime end = LocalDateTime.parse(scanner.nextLine(), dtf);
            if (end.isBefore(start) || end.isEqual(start)) {
                System.out.println("End time must be after start time.");
                return null;
            }
            return new Schedule(start, end);
        } catch (Exception e) {
            System.out.println("Invalid date/time format.");
            return null;
        }
    }

    private static Pilot selectPilot(Scanner scanner) {
        // Collect pilots from CrewList
        ArrayList<Pilot> pilots = new ArrayList<>();
        int size = crewList.size();
        for (int i = 0; i < size; i++) {
            CrewMember cm = crewList.get(i);
            if (cm instanceof Pilot) {
                pilots.add((Pilot) cm);
            }
        }

        if (pilots.isEmpty()) {
            System.out.println("No pilots available.");
            return null;
        }

        System.out.println("Available pilots:");
        for (int i = 0; i < pilots.size(); i++) {
            Pilot p = pilots.get(i);
            System.out.println(i + ": " + p.getName() + " (" + p.getLicenseNumber() + ")");
        }
        System.out.print("Select pilot index: ");
        int index = Integer.parseInt(scanner.nextLine());

        if (index < 0 || index >= pilots.size()) {
            System.out.println("Invalid index.");
            return null;
        }
        return pilots.get(index);
    }

    private static Plane selectPlane(Scanner scanner) {
        if (planes.isEmpty()) {
            System.out.println("No planes available.");
            return null;
        }
        System.out.println("Available planes:");
        for (int i = 0; i < planes.size(); i++) {
            Plane p = planes.get(i);
            System.out.println(i + ": " + p.getId() + " (" + p.getModel() + ")");
        }
        System.out.print("Select plane index: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index < 0 || index >= planes.size()) {
            System.out.println("Invalid index.");
            return null;
        }
        return planes.get(index);
    }

    private static void assignScheduleToPilot(Scanner scanner) {
        Pilot pilot = selectPilot(scanner);
        if (pilot == null) return;

        Schedule schedule = inputSchedule(scanner);
        if (schedule == null) return;

        if (pilot.assignToSchedule(schedule)) {
            System.out.println("Schedule assigned to pilot.");
        } else {
            System.out.println("Could not assign schedule (conflict or full).");
        }
    }

    private static void assignScheduleToPlane(Scanner scanner) {
        Plane plane = selectPlane(scanner);
        if (plane == null) return;

        Schedule schedule = inputSchedule(scanner);
        if (schedule == null) return;

        if (plane.assignToSchedule(schedule)) {
            System.out.println("Schedule assigned to plane.");
        } else {
            System.out.println("Could not assign schedule (conflict or full).");
        }
    }

    private static void assignPassengerToPlane(Scanner scanner) {
        Plane plane = selectPlane(scanner);
        if (plane == null) return;

        System.out.print("Passenger ID to assign seat: ");
        String pid = scanner.nextLine();
        Passenger passenger = passengers.searchByID(pid);  // Make sure this method exists

        if (passenger == null) {
            System.out.println("Passenger not found.");
            return;
        }

        System.out.print("Seat (e.g. 12A): ");
        String seat = scanner.nextLine();

        boolean assigned = plane.assignSeatByLabel(seat, passenger);
        System.out.println(assigned ? "Passenger assigned to seat." : "Seat is already taken or invalid.");
    }

    private static void showPilotSchedules(Scanner scanner) {
        Pilot pilot = selectPilot(scanner);
        if (pilot == null) return;

        System.out.println("Schedules for " + pilot.getName() + ":");
        for (Schedule s : pilot.getSchedule()) {
            if (s != null) System.out.println(s);
        }
    }

    private static void showPlaneSchedules(Scanner scanner) {
        Plane plane = selectPlane(scanner);
        if (plane == null) return;

        System.out.println("Schedules for plane " + plane.getId() + ":");
        for (Schedule s : plane.getSchedule()) {
            if (s != null) System.out.println(s);
        }
    }

    private static void listAllPassengers() {
        if (passengers.isEmpty()) {
            System.out.println("No passengers.");
            return;
        }
        System.out.println("Passengers:");
        passengers.listAllPassengers();
    }

    public static void generateData() {
        Random rand = new Random();

        String[] firstNames = {
            "James", "Olivia", "Michael", "Emma", "William", "Ava", "Benjamin", "Sophia",
            "Lucas", "Isabella", "Henry", "Mia", "Alexander", "Charlotte", "Daniel",
            "Amelia", "Matthew", "Harper", "Joseph", "Evelyn"
        };

        String[] lastNames = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
            "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
            "Thomas", "Taylor", "Moore", "Jackson", "Martin"
        };

        // Generate 500 passengers
        for (int i = 0; i < 500; i++) {
            String name = firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
            String id = "P" + (10000 + i);
            int age = 18 + rand.nextInt(60);
            String ticket = "T" + (20000 + i);
            // Random seat like "12A"
            int row = 1 + rand.nextInt(30);
            char col = (char) ('A' + rand.nextInt(6));
            String seat = row + "" + col;

            Passenger passenger = new Passenger(name, id, age, ticket, seat);
            passengers.add(passenger);
        }

        // Generate 50 crew members, with 10 pilots
        for (int i = 0; i < 50; i++) {
            String name = firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
            String id = "C" + (5000 + i);
            int age = 25 + rand.nextInt(40);
            String role = (i < 10) ? "Pilot" : "Crew";
            String empId = "E" + (7000 + i);

            if (i < 10) {
                // Create Pilot
                String license = "LIC" + (9000 + i);
                String rating = "Type" + (1 + rand.nextInt(3));
                Pilot pilot = new Pilot(name, id, age, role, empId, license, rating);
                crewList.add(pilot);
            } else {
                // Regular CrewMember
                CrewMember crew = new CrewMember(name, id, age, role, empId);
                crewList.add(crew);
            }
        }

        // Generate 10 planes
        for (int i = 0; i < 10; i++) {
            String planeId = "PL" + (100 + i);
            String model = "Model-" + (char) ('A' + i);
            int rows = 30;
            int cols = 6;

            Plane plane = new Plane(planeId, model, rows, cols);
            planes.add(plane);
        }

        // Generate 10 flights (Schedules) and assign to planes and pilots
        LocalDateTime baseStart = LocalDateTime.now().plusDays(1);
        for (int i = 0; i < 10; i++) {
            LocalDateTime start = baseStart.plusHours(i * 3);
            LocalDateTime end = start.plusHours(2);

            Schedule schedule = new Schedule(start, end);

            // Assign schedule to plane
            Plane plane = planes.get(i);
            plane.assignToSchedule(schedule);

            // Assign schedule to a pilot (pick pilot from crewList)
            Pilot pilot = null;
            // Find the i-th pilot
            int pilotCount = 0;
            for (int j = 0; j < crewList.size(); j++) {
                CrewMember cm = crewList.get(j);
                if (cm instanceof Pilot) {
                    if (pilotCount == i) {
                        pilot = (Pilot) cm;
                        break;
                    }
                    pilotCount++;
                }
            }
            if (pilot != null) {
                pilot.assignToSchedule(schedule);
            }
        }

        System.out.println("Generated 500 passengers, 50 crew (including 10 pilots), 10 planes, and 10 flights.");
    }
    private static void listAllPlanes() {
        if (planes.isEmpty()) {
            System.out.println("No planes.");
            return;
        }
        for (Plane p : planes) {
            System.out.println("ID: " + p.getId() + ", Model: " + p.getModel());
        }
    }

}
