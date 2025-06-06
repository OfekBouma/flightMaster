package flightMaster;

public class CrewMember extends Person {
    private String role;
    private String employeeId;

    // Constructor
    public CrewMember(String name, String id, int age, String role, String employeeId) {
        super(name, id, age);  // call Person constructor
        this.role = role;
        this.employeeId = employeeId;
    }

    // Getter and Setter for role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getter and Setter for employeeId
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    @Override
    public String toString() {
        return "CrewMember{" +
               "id='" + getId() + '\'' +
               ", name='" + getName() + '\'' +
               ", role='" + getRole() + '\'' +
               ",worker ID= "+getEmployeeId()+
               '}';
}
}
