package flightMaster;

public class Passenger extends Person {
    private Ticket ticket;

    // Constructor with name, id, age, and ticket
    public Passenger(String name, String id, int age, Ticket ticket) {
        super(name, id, age);
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return super.toString() + 
               ", " + (ticket != null ? ticket.toString() : "No ticket");
    }
}
