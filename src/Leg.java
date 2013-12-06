/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
public class Leg {
    private String leg_number, trip_number, seats_available, flight_date, assignment;
    
    public Leg(String leg_number, String trip_number, String seats_available, String flight_date, String assignment)
    {
        this.leg_number = leg_number;
        this.trip_number = trip_number;
        this.seats_available = seats_available;
        this.flight_date = flight_date;
        this.assignment = assignment;
    }

    /**
     * @return the leg_number
     */
    public String getLeg_number() {
        return leg_number;
    }

    /**
     * @return the trip_number
     */
    public String getTrip_number() {
        return trip_number;
    }

    /**
     * @return the seats_available
     */
    public String getSeats_available() {
        return seats_available;
    }

    /**
     * @return the flight_date
     */
    public String getFlight_date() {
        return flight_date;
    }

    /**
     * @return the assignment
     */
    public String getAssignment() {
        return assignment;
    }
    
}
