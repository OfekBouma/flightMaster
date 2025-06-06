package flightMaster;

public interface Schedulable{
	/*
	 * returns true if is available 
	 * for example if a plane was'nt assigned to a flight
	 *
	 */
    boolean isAvailable(Schedule requested);
	/*
	 *  return the schedule, for example flight times
	 */
	Schedule[] getSchedule();
}
