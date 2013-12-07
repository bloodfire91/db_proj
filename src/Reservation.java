/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Robin
 */
public class Reservation {
    private String reservation_number, email, username, address, phone_number;
    private Date reservation_date;
    
    public Reservation(String email, 
            String address, String phone_number)
    {
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.reservation_date = new Date();
        
        System.out.println(reservation_date.toString());
    }

    /**
     * @return the reservation_number
     */
    public String getReservation_number() {
        return reservation_number;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the phone_number
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * @return the reservation_date
     */
    public Date getReservation_date() {
        return reservation_date;
    }
}
