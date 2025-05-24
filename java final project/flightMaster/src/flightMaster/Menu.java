package flightMaster;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import flightMaster.structures.*;

public class Menu {

    private static ArrayList<Pilot> pilots = new ArrayList<>();
    private static ArrayList<Plane> planes = new ArrayList<>();
    private static ArrayList<Passenger> passengers = new ArrayList<>();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
        pilots.add(pilot);
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
        Passenger passenger = passengers.stream()
                .filter(p -> p.getId().equals(pid))
                .findFirst()
                .orElse(null);

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
        for (Passenger p : passengers) {
            System.out.println(p);
        }
    }
    public static void testMain() {
        // Create Plane
        Plane plane = new Plane("PL100", "Boeing 737", 3, 3);

        // Create Flight
        Flight flight = new Flight("FL1000", plane, "New York", "London", "2025-05-20T09:00");

        // Create Schedule and assign it to the plane and flight
        Schedule schedule = new Schedule(
            LocalDateTime.of(2025, 5, 20, 9, 0),
            LocalDateTime.of(2025, 5, 20, 12, 0)
        );

        boolean scheduledToFlight = flight.assignSchedule(schedule);
        boolean scheduledToPlane = plane.assignToSchedule(schedule);

        System.out.println("Schedule assigned to flight? " + scheduledToFlight);
        System.out.println("Schedule assigned to plane? " + scheduledToPlane);

        // Create crew members
        Pilot pilot = new Pilot("John Captain", "P001", 50, "Pilot", "EMP100", "LIC123", "ATPL");
        CrewMember attendant1 = new CrewMember("Jane Doe", "ID001", 30, "Flight Attendant", "EMP200");
        CrewMember attendant2 = new CrewMember("Mike Smith", "ID002", 28, "Flight Attendant", "EMP201");

        // Assign schedule to crew
        pilot.assignToSchedule(schedule);
       // attendant1.assignToSchedule(schedule);
        //attendant2.assignToSchedule(schedule);

        // Add crew members to the flight's internal CrewList
        flight.getCrewList().add(pilot);
        flight.getCrewList().add(attendant1);
        flight.getCrewList().add(attendant2);

        // Create BoardingQueue and add passengers
        BoardingQueue queue = new BoardingQueue();
        queue.enqueue(new Passenger("Alice", "ID101", 28, "TCK001", "1A"));
        queue.enqueue(new Passenger("Bob", "ID102", 35, "TCK002", "2A"));
        queue.enqueue(new Passenger("Charlie", "ID103", 40, "TCK003", "3C"));

        // Board passengers through plane
        plane.boardPassengers(queue);

        // Print seat map
        System.out.println("\n--- Seat Map ---");
        plane.printSeatMap();

        // Print assigned crew
        System.out.println("\n--- Flight Crew ---");
        flight.getCrewList().printAll();

        // Deboard passengers
        plane.deboardPassengers();

        // Print seat map again to confirm deboarding
        System.out.println("\n--- Seat Map After Deboarding ---");
        plane.printSeatMap();

        System.out.println("\nTest complete.");
    }

}
