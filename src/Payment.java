/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
public class Payment {
    private String transaction_number, trip_number, reservation_number, payment_date, account_num, name_on_account;
    
    public Payment(String transaction_number, String reservation_number, String paymentDate,
            String account_num, String name_on_account)
    {
        this.transaction_number = transaction_number;
        //this.trip_number = trip_number;
        this.reservation_number = reservation_number;
        this.payment_date = payment_date;
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
    public String getPayment_date() {
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
