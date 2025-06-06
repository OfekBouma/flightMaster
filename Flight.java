package flightMaster;

import flightMaster.structures.CrewList;

public class Flight implements Schedulable {
    private String flightNumber;
    private Plane plane;
    private String origin;
    private String destination;
    private String departureTime;
    private CrewList crewList = new CrewList(); 


    private Schedule[] schedules = new Schedule[10]; // max 10 schedules per flight

    public Flight(String flightNumber, Plane plane, String origin, String destination, String departureTime) {
        this.flightNumber = flightNumber;
        this.plane = plane;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    // Setters and getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    // Schedulable interface methods

    @Override
    public boolean isAvailable(Schedule requested) {
        // Check if requested schedule overlaps with any existing flight schedule
        for (Schedule s : schedules) {
            if (s != null && s.overlapsWith(requested)) {
                return false;
            }
        }
        // Also check if plane is available at this schedule
        if (plane != null && !plane.isAvailable(requested)) {
            return false;
        }
        return true;
    }

    @Override
    public Schedule[] getSchedule() {
        return schedules;
    }

    public boolean assignSchedule(Schedule newSchedule) {
        if (!isAvailable(newSchedule)) {
            return false; // Conflict with existing schedules
        }
        // Assign schedule to flight
        for (int i = 0; i < schedules.length; i++) {
            if (schedules[i] == null) {
                schedules[i] = newSchedule;
                break;
            }
        }
        // Assign schedule also to plane (to avoid double-booking plane)
        if (plane != null) {
            plane.assignToSchedule(newSchedule);
        }
        return true;
    }
    
    public boolean addCrewMember(CrewMember crewMember) {
        if (crewList.contains(crewMember)) {
            System.out.println("Crew member already assigned to this flight.");
            return false;
        }
        crewList.add(crewMember);
        return true;
    }
    
    public boolean removeCrewMember(CrewMember crewMember) {
        return crewList.remove(crewMember);
    }

    
    public CrewList getCrewList() {
        return crewList;
    }
    
    
    
    // Optional: toString for better debug printing
    @Override
    public String toString() {
        return String.format("Flight %s: %s -> %s, Departure: %s, Plane: %s",
                flightNumber, origin, destination, departureTime,
                plane != null ? plane.getModel() + " (" + plane.getId() + ")" : "No plane assigned");
    }
}
