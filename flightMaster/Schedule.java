package flightMaster;

import java.time.LocalDateTime;

public class Schedule {
    private LocalDateTime start;
    private LocalDateTime end;

    public Schedule(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean overlapsWith(Schedule other) {
        return !start.isAfter(other.end) && !end.isBefore(other.start);
    }

    @Override
    public String toString() {
        return "From " + start + " to " + end;
    }
}
