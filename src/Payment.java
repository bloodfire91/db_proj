/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author Robin
 */
public class Payment {
    private String transaction_number, trip_number, reservation_number, account_num, name_on_account;
    private Date payment_date;
    
    public Payment(String account_num, String name_on_account)
    {
        //this.transaction_number = transaction_number;
        //this.trip_number = trip_number;
        //this.reservation_number = reservation_number;
        this.payment_date = new Date();
        System.out.println(payment_date.toString());
        this.account_num = account_num;
        this.name_on_account = name_on_account;
    }

    /**
     * @return the transaction_number
     */
    public String getTransaction_number() {
        return transaction_number;
    }

    /**
     * @return the trip_number
     */
    public String getTrip_number() {
        return trip_number;
    }

    /**
     * @return the reservation_number
     */
    public String getReservation_number() {
        return reservation_number;
    }

    /**
     * @return the payment_date
     */
    public Date getPayment_date() {
        return payment_date;
    }

    /**
     * @return the account_num
     */
    public String getAccount_num() {
        return account_num;
    }

    /**
     * @return the name_on_account
     */
    public String getName_on_account() {
        return name_on_account;
    }
}
