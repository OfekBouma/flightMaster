package flightMaster;

public class Ticket {
    private String ticketNumber;
    private String seatNumber;
    private String flightNumber;

    public Ticket(String ticketNumber, String seatNumber, String flightNumber) {
        this.ticketNumber = ticketNumber;
        this.seatNumber = seatNumber;
        this.flightNumber = flightNumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public String toString() {
        return String.format("Ticket: %s, Seat: %s, Flight: %s", ticketNumber, seatNumber, flightNumber);
    }
}
