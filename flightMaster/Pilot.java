package flightMaster;

public class Pilot extends CrewMember implements Schedulable{
    
	private String licenseNumber;
    private String flightRating;
    private Schedule[] schedules = new Schedule[4];//assuming a max flight slots per day
    
    // Constructor
    public Pilot(String name, String id, int age, String role, String employeeId, String licenseNumber, String flightRating) {
        super(name, id, age, role, employeeId); // call CrewMember constructor
        this.licenseNumber = licenseNumber;
        this.flightRating = flightRating;
    }

    // Getter and Setter for licenseNumber
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    // Getter and Setter for flightRating
    public String getFlightRating() {
        return flightRating;
    }

    public void setFlightRating(String flightRating) {
        this.flightRating = flightRating;
    }
    // Schedulable
    
    @Override
    public boolean isAvailable(Schedule newSchedule) {
        for (Schedule s : schedules) {
            if (s != null && s.overlapsWith(newSchedule)) {
                return false; // Not available due to overlap
            }
        }
        return true; // No overlaps, available
    }
    
    @Override
    public Schedule[] getSchedule() {
        return schedules;
    }


    public boolean assignToSchedule(Schedule newSchedule) {
        if (!isAvailable(newSchedule)) {
            return false; // Cannot assign due to conflict
        }
        for (int i = 0; i < schedules.length; i++) {
            if (schedules[i] == null) {
                schedules[i] = newSchedule;
                return true;
            }
        }
        return false; // No room to assign more schedules
    }
    
    @Override
    public String toString() {
        return String.format("Pilot{name=%s, license=%s, rating=%s}", getName(), licenseNumber, flightRating);
    }

}

