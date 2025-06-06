package flightMaster;

import flightMaster.structures.*;
import java.time.LocalDateTime;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Create passenger list and add 20 passengers (all for FL100)
        PassengerList passengerList = new PassengerList();
        for (int i = 0; i < 20; i++) {
            int row = (i / 6) + 1;
            char seatChar = (char) ('A' + (i % 6));
            String seatNumber = row + "" + seatChar;
            String flightNumber = "FL100";  // All passengers for FL100

            Ticket ticket = new Ticket("TICKET" + (2000 + i), seatNumber, flightNumber);
            Passenger p = new Passenger("Passenger" + (i + 1), "PID" + (1000 + i), 20 + (i % 30), ticket);
            passengerList.add(p);
        }

        // Create crew list
        CrewList crewList = new CrewList();
        for (int i = 0; i < 4; i++) {
            Pilot pilot = new Pilot("Pilot" + (i + 1), "CID" + (3000 + i), 40 + i, "Pilot",
                                    "EID" + (4000 + i), "LIC" + (5000 + i), "Rating" + (i + 1));
            crewList.add(pilot);
        }
        for (int i = 0; i < 8; i++) {
            CrewMember attendant = new CrewMember("Crew" + (i + 1), "CID" + (3500 + i), 30 + i,
                                                  "Flight Attendant", "EID" + (4500 + i));
            crewList.add(attendant);
        }

        // Create FlightBST
        FlightBST flightBST = new FlightBST();

        LocalDateTime baseStart = LocalDateTime.of(2025, 6, 1, 6, 0);
        LocalDateTime baseEnd = baseStart.plusHours(2);

  
        
        Random rand = new Random();
        // Insert 6 flights with unique planes
        int randomInt;
        for (int i = 0; i < 8; i++) {
            Plane assignedPlane = new Plane(
                "PL" + String.format("%03d", i + 1),
                "Model" + (i + 1),
                10, 6  // Each plane has 10 rows, 6 seats per row
            );
            randomInt = rand.nextInt(60);
            Flight flight = new Flight(
                "FL" + (100 + randomInt),
                assignedPlane,
                "City" + (i + 1),
                "City" + (i + 11),
                baseStart.plusHours(i * 3).toString()
            );

            Schedule schedule = new Schedule(baseStart.plusHours(i * 3), baseEnd.plusHours(i * 3));
            flight.assignSchedule(schedule);
            assignedPlane.assignToSchedule(schedule);

            // Assign one available pilot
            for (int j = 0; j < crewList.size(); j++) {
                if (crewList.get(j) instanceof Pilot pilot && pilot.isAvailable(schedule)) {
                    pilot.assignToSchedule(schedule);
                    flight.addCrewMember(pilot);
                    break;
                }
            }

            // Assign one flight attendant
            for (int j = 0; j < crewList.size(); j++) {
                CrewMember cm = crewList.get(j);
                if (!(cm instanceof Pilot) && "Flight Attendant".equals(cm.getRole())) {
                    flight.addCrewMember(cm);
                    break;
                }
            }

            flightBST.insert(flight);
        }

        // Print all flights before boarding
        System.out.println("=== Flights In-Order (Before Boarding) ===");
        flightBST.inOrderPrint();

        BoardingQueue boardingQueue = new BoardingQueue();

        // Board passengers on first 4 flights
        System.out.println("\n=== Boarding Passengers on First 4 Flights ===");
        String[] boardFlightNumbers = {"FL100", "FL101", "FL102", "FL103"};

        for (String fNum : boardFlightNumbers) {
            Flight flight = flightBST.search(fNum);
            if (flight == null) continue;

            Plane plane = flight.getPlane();

            System.out.println("\nBoarding flight: " + fNum);
            passengerList.enqueuePassengersForFlight(boardingQueue, fNum);

            plane.boardPassengers(boardingQueue);
            boardingQueue.clear();
            plane.printSeatMap();
        }

        // Deboard passengers on FL100
        Flight firstFlight = flightBST.search("FL100");
        if (firstFlight != null) {
            System.out.println("\n=== Deboarding Passengers from " + firstFlight.getFlightNumber() + " ===");
            firstFlight.getPlane().deboardPassengers();
        }

        // Print updated flight & crew info
        System.out.println("\n=== Flight & Crew Details ===");
        flightBST.inOrderPrint();
        System.out.println("\n=== BST Structured Print ===");
        flightBST.printTreeStructured();
        

        // Demonstrate search and delete
        String flightToDelete = "FL102";
        System.out.println("\nSearching for flight " + flightToDelete + ":");
        Flight found = flightBST.search(flightToDelete);
        if (found != null) {
            System.out.println("Found flight: " + found);
            System.out.println("Deleting flight: " + flightToDelete);
            flightBST.delete(flightToDelete);
        } else {
            System.out.println("Flight not found");
        }

        System.out.println("\n=== Flights In-Order (After Deletion) ===");
        flightBST.inOrderPrint();
    }
}
