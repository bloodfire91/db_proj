/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
public class Trip {
    private String trip_number, airline, price, departure, destination, number_of_legs;
    
    public Trip(String trip_number, String airline, String price, String departure, String destination, String number_of_legs)
    {
        this.trip_number = trip_number;
        this.airline = airline;
        this.price = price;
        this.departure = departure;
        this.destination = destination;
        this.number_of_legs = number_of_legs;
    }

    /**
     * @return the trip_number
     */
    public String getTrip_number() {
        return trip_number;
    }

    /**
     * @return the airline
     */
    public String getAirline() {
        return airline;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @return the departure
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @return the number_of_legs
     */
    public String getNumber_of_legs() {
        return number_of_legs;
    }
    
}
