/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Robin
 */
public class DBHandler {
    private Connection conn;
    private final String URL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
    private final String USERNAME = "s03022dt";
    private String password;
    private final String ADMIN = "A";
    private final String RESTRICTED = "R";
    private String userAccess;
    private Controller controller;

    public DBHandler(String password)
    {
        this.password = password;
        
        initConnection();
    }
    
    public void initConnection()
    {
        try
        {
             DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
             conn = DriverManager.getConnection (URL,USERNAME, password);
        }
        catch (SQLException e) 
        { 
            System.out.println(e.getMessage());
        }
    }
    
    public boolean validateUser(String username, String password) throws SQLException
    {
        boolean validated = false;
        Statement stmt = null;
        
        try
        {
            // Create a Statement
            stmt = conn.createStatement();
            //check for user
            ResultSet rset = stmt.executeQuery ("select * from USERS where username = '" + username + "' and user_password = '" + password + "'");
            
            if(!rset.next())
            {
                //System.out.println("user not found");
                validated = false;                      
            }
            else
            {
                //System.out.println("user found: " + rset.getString("USERNAME"));
                //System.out.println("passwor found" + rset.getString("USER_PASSWORD"));
                userAccess = new String(rset.getString("USER_ACCESS"));
                //System.out.println(userAccess);
                validated = true;
            }       
            return validated;
        }
        catch (SQLException e) 
        { 
            System.out.println(e.getMessage());
        }
        finally
        {
            if(stmt != null)
            {
                stmt.close();
            }
        }
        
        return validated;
    }
    
    public boolean addUser(String username, String password) throws SQLException
    {
        Statement insertStatement = null;      
    
        try
        {
            String insertUser = "INSERT INTO USERS(USERNAME, USER_PASSWORD, USER_ACCESS) VALUES('" + username + "', '" + password + "','" + RESTRICTED + "')";
            insertStatement = conn.createStatement();
            insertStatement.executeUpdate(insertUser);
            return true;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());            
        }
        finally
        {
            if(insertStatement != null)
            {
                insertStatement.close();
            }
        }
        return false;
    }
    
    public String getAdmin()
    {
        return ADMIN;
    }
    
    public String getRestricted()
    {
        return RESTRICTED;
    }
    
    public String getUserAccess()
    {
        return userAccess;
    }
    
    public List<Trip> getAllTrips() throws SQLException
    {
        Statement stmt = null;
        ResultSet trips = null;
        List<Trip> allTrips = new ArrayList<Trip>();
        
        try
        {
            String allTripsQuery = "SELECT * FROM TRIP";
            stmt = conn.createStatement();
            trips = stmt.executeQuery(allTripsQuery);
            if(!trips.next())
            {
                System.out.println("trips empty");
            }
            else
            {
                do
                {
                    String tripNum = trips.getString("TRIP_NUMBER");
                    String airline = trips.getString("AIRLINE");
                    String price = trips.getString("PRICE");
                    String depart = trips.getString("DEPARTURE");
                    String dest = trips.getString("DESTINATION");
                    String numLegs = trips.getString("NUMBER_OF_LEGS");
                    
                    allTrips.add(new Trip(tripNum, airline, price, depart, dest, numLegs));
                    
                }while(trips.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
        }
        
        return allTrips;
    }
    
    public boolean removeTrip(String tripNum) throws SQLException
    {
        boolean removed = false;
        Statement stmt = null;
        
        try
        {
            // Create a Statement
            stmt = conn.createStatement();
            //check for user
            stmt.executeQuery ("DELETE FROM TRIP where TRIP_NUMBER = '" + Integer.parseInt(tripNum) + "'");
            
            removed = true;
        }
        catch (SQLException e) 
        { 
            System.out.println(e.getMessage());
        }
        finally
        {
            if(stmt != null)
            {
                stmt.close();
            }
        }
        
        return removed;
    }
    
    public List<Leg> getLegs(String tripNum) throws SQLException
    {
        Statement stmt = null;
        Statement assnStmt = null;
        ResultSet legs = null;
        ResultSet assignedPlane = null;
        List<Leg> allLegs = new ArrayList<Leg>();
        
        try
        {
            String legsQuery = "SELECT * FROM FLIGHT_LEG WHERE TRIP_NUMBER = '" + tripNum + "'";            
            stmt = conn.createStatement();
            assnStmt = conn.createStatement();
            
            legs = stmt.executeQuery(legsQuery);
           
            if(!legs.next())
            {
                System.out.println("trips empty");
            }
            else
            {
                do
                {
                    String legNum = legs.getString("LEG_NUMBER");                    
                    String seats = legs.getString("SEATS_AVAILABLE");
                    String date = legs.getString("FLIGHT_DATE");                    
                    //allLegs.add(new Leg(legNum, tripNum, seats, date));
                   
                    String assignQuery = "SELECT ID FROM ASSIGN WHERE LEG_NUMBER = '" + legNum + "' AND TRIP_NUMBER = '" + tripNum + "'";
                    assignedPlane = assnStmt.executeQuery(assignQuery);
                    String assignment;
                    if(!assignedPlane.next())
                    {
                        assignment = "not assigned";
                    }
                    else
                    {
                        //assignment = "hi";
                        assignment = assignedPlane.getString("ID");
                        //System.out.println("assignment: " + assignment);
                    }
                    /*System.out.println(legs.getString("LEG_NUMBER"));
                    System.out.println(legs.getString("TRIP_NUMBER"));
                    System.out.println(legs.getString("SEATS_AVAILABLE"));
                    System.out.println(legs.getString("FLIGHT_DATE") + "\n"); */   
                    allLegs.add(new Leg(legNum, tripNum, seats, date, assignment));
                    
                }while(legs.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
            assnStmt.close();
        }
        return allLegs;
    }
    
    public List<String> getPlaneOptions() throws SQLException
    {
        Statement stmt = null;
        ResultSet trips = null;
        List<String> allPlanes = new ArrayList<String>();
        
        try
        {
            String allTripsQuery = "SELECT * FROM AIRPLANE";
            stmt = conn.createStatement();
            trips = stmt.executeQuery(allTripsQuery);
            if(!trips.next())
            {
                System.out.println("no airplanes empty");
            }
            else
            {
                do
                {
                    String planeID = trips.getString("ID");             
                    allPlanes.add(planeID);
                    
                }while(trips.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
        }
        
        return allPlanes;
    }
    
    public boolean changePlane(String tripNum, String legNum, String plane) throws SQLException
    {
        boolean changed = false;
        Statement stmt = null;
        ResultSet trips = null;
        List<String> allPlanes = new ArrayList<String>();
        
        try
        {
            String updatePlane = "UPDATE ASSIGN SET ID = '" + plane + "' WHERE LEG_NUMBER = '" + legNum + "' AND TRIP_NUMBER = '" + tripNum + "'";
            stmt = conn.createStatement();
            stmt.executeQuery(updatePlane);
            changed = true;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
        }
        return changed;
    }
    
    public List<Trip> getFlightHistory(String username) throws SQLException
    {
        Statement stmt = null;
        ResultSet trips = null;
        List<Trip> flightHist = new ArrayList<Trip>();
        
        try
        {
            String allTripsQuery = "SELECT * FROM TRIP,HAS_TRIP WHERE HAS_TRIP.USERNAME = '" + username + "' AND TRIP.TRIP_NUMBER = HAS_TRIP.TRIP_NUMBER";
            stmt = conn.createStatement();
            trips = stmt.executeQuery(allTripsQuery);
            if(!trips.next())
            {
                System.out.println("no flight history");
            }
            else
            {
                do
                {
                    String tripNum = trips.getString("TRIP_NUMBER");
                    String airline = trips.getString("AIRLINE");
                    String price = trips.getString("PRICE");
                    String depart = trips.getString("DEPARTURE");
                    String dest = trips.getString("DESTINATION");
                    String numLegs = trips.getString("NUMBER_OF_LEGS");
                    
                    flightHist.add(new Trip(tripNum, airline, price, depart, dest, numLegs));  
                    
                }while(trips.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
        }
        
        //System.out.println("flight hist size: " + flightHist.size());
        return flightHist;
    }
    
    public String[] getLeavingFromAirports() throws SQLException
    {
        Statement stmt = null;
        ResultSet rset = null;
        List<String> departingPorts = new ArrayList<String>();
        
        try
        {
            String allTripsQuery = "SELECT * FROM DEPARTURE";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(allTripsQuery);
            if(!rset.next())
            {
                System.out.println("no departing airports");
            }
            else
            {
                do
                {
                    String code = rset.getString("CODE");  
                    if(!departingPorts.contains(code))
                    {
                        departingPorts.add(code);
                    }
                    
                }while(rset.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
        }
        
        return departingPorts.toArray(new String[departingPorts.size()]);
    }
    
    public String[] getGoingToAirports() throws SQLException
    {
        Statement stmt = null;
        ResultSet rset = null;
        List<String> arrivingPorts = new ArrayList<String>();
        
        try
        {
            String allTripsQuery = "SELECT * FROM ARRIVAL";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(allTripsQuery);
            if(!rset.next())
            {
                System.out.println("no airplanes empty");
            }
            else
            {
                do
                {
                    String code = rset.getString("CODE");  
                    if(!arrivingPorts.contains(code))
                    {
                        arrivingPorts.add(code);
                    }
                    
                }while(rset.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
        }
        
        return arrivingPorts.toArray(new String[arrivingPorts.size()]);
    }
    
    
    
    public List<Trip> getSearchResults(String leavingCode, String goingCode, String leavingLowerBound, String leavingUpperBound) throws SQLException
    {
       
        Statement stmt = null;
        ResultSet rset = null;
        List<Trip> searchResults = new ArrayList<Trip>();
        
        try
        {
            String allTripsQuery = buildSearchQuery(leavingCode, goingCode, leavingLowerBound, leavingUpperBound);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(allTripsQuery);
            if(!rset.next())
            {
                System.out.println("no flights found");
            }
            else
            {
                do
                {             
                    String tripNum = rset.getString("TRIP_NUMBER");
                    String airline = rset.getString("AIRLINE");
                    String price = rset.getString("PRICE");
                    String depart = rset.getString("DEPARTURE");
                    String dest = rset.getString("DESTINATION");
                    String numLegs = rset.getString("NUMBER_OF_LEGS");                    
                    searchResults.add(new Trip(tripNum, airline, price, depart, dest, numLegs));                          
                }while(rset.next());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            stmt.close();
        }
        return searchResults;
    }
    
    public String buildSearchQuery(String leavingCode, String goingCode, String leavingLowerBound, String leavingUpperBound)
    {
        String query = "select * from flight_leg, trip where trip.departure = '"
                + leavingCode 
                +"' and trip.destination = '" 
                + goingCode 
                + "' and flight_leg.trip_number = trip.trip_number and flight_leg.leg_number = 1 and flight_leg.flight_date <= '" 
                + leavingUpperBound
                + "' and flight_leg.flight_date >= '"
                + leavingLowerBound
                + "'";
        
        return query;
    }
    
    public boolean addPaymentAndReservation(Payment payment, Reservation reservation, HashMap<String, String> selectedTrips, User user) throws SQLException
    {
        boolean added = false;
        PreparedStatement payStmt = null;
        PreparedStatement reservStmt = null;
        PreparedStatement hasStmt = null;
        
        PreparedStatement payStmt2 = null;
        PreparedStatement reservStmt2 = null;
        PreparedStatement hasStmt2 = null;
        
        ResultSet trips = null;
        List<String> allPlanes = new ArrayList<String>();
        
        try
        {
            conn.setAutoCommit(false);
            String insertPayment = "INSERT INTO PAYMENT"
                    + " (TRANSACTION_NUMBER, TRIP_NUMBER, RESERVATION_NUMBER, PAYMENT_DATE, ACCOUNT_NUM, NAME_ON_ACCOUNT)"
                    + " VALUES(?,?,?,?,?,?)";
            
            String insertReservation = "INSERT INTO RESERVATION"
                    + " (RESERVATION_NUMBER, EMAIL, USERNAME, ADDRESS, PHONE_NUMBER, RESERVATION_DATE)"
                    + " VALUES(?,?,?,?,?,?)";
            
            String insertHasTrip = "INSERT INTO HAS_TRIP (USERNAME, TRIP_NUMBER) "
                    + "VALUES (?,?)";
            
            List<Integer> tripNumbers = new ArrayList<Integer>();
            Set<String> set = selectedTrips.keySet();            
            for(String s: set)
            {
                tripNumbers.add(Integer.parseInt(s));
            }
            Random rand = new Random(System.currentTimeMillis());           
            int reservNum = rand.nextInt(100);       
            int transacNum = rand.nextInt(100);
            
            if(tripNumbers.size() > 1)
            {
                payStmt = conn.prepareStatement(insertPayment);
                payStmt.setInt(1, transacNum);//transac
                payStmt.setInt(2, tripNumbers.get(1));//trip
                payStmt.setInt(3, reservNum);//reservatoin
                payStmt.setDate(4, new java.sql.Date(payment.getPayment_date().getTime()));//payment date
                payStmt.setInt(5, Integer.parseInt(payment.getAccount_num()));//account num
                payStmt.setString(6, payment.getName_on_account());//name on account   
                payStmt.executeUpdate();            

                reservStmt = conn.prepareStatement(insertReservation);
                reservStmt.setInt(1, reservNum);
                reservStmt.setString(2, reservation.getEmail());
                reservStmt.setString(3, user.getUsername());
                reservStmt.setString(4, reservation.getAddress());
                reservStmt.setString(5, reservation.getPhone_number());
                reservStmt.setDate(6, new java.sql.Date(reservation.getReservation_date().getTime()));
                reservStmt.executeUpdate();

                hasStmt = conn.prepareStatement(insertHasTrip);
                hasStmt.setString(1, user.getUsername());
                hasStmt.setInt(2, tripNumbers.get(1));
                hasStmt.executeUpdate();
            }
            
            reservNum = rand.nextInt(100);       
            transacNum = rand.nextInt(100);
            
            payStmt = conn.prepareStatement(insertPayment);
            payStmt.setInt(1, transacNum);//transac
            payStmt.setInt(2, tripNumbers.get(0));//trip
            payStmt.setInt(3, reservNum);//reservatoin
            payStmt.setDate(4, new java.sql.Date(payment.getPayment_date().getTime()));//payment date
            payStmt.setInt(5, Integer.parseInt(payment.getAccount_num()));//account num
            payStmt.setString(6, payment.getName_on_account());//name on account   
            payStmt.executeUpdate();            

            reservStmt = conn.prepareStatement(insertReservation);
            reservStmt.setInt(1, reservNum);
            reservStmt.setString(2, reservation.getEmail());
            reservStmt.setString(3, user.getUsername());
            reservStmt.setString(4, reservation.getAddress());
            reservStmt.setString(5, reservation.getPhone_number());
            reservStmt.setDate(6, new java.sql.Date(reservation.getReservation_date().getTime()));
            reservStmt.executeUpdate();
            
            hasStmt = conn.prepareStatement(insertHasTrip);
            hasStmt.setString(1, user.getUsername());
            hasStmt.setInt(2, tripNumbers.get(0));
            hasStmt.executeUpdate();
            
            conn.commit();
            System.out.println("committed!");
            /*payStmt.setInt(1, Integer.parseInt(transNum));
            payStmt.setInt(2, Integer.parseInt(tripNum));
            payStmt.setInt(3, Integer.parseInt(reservNum));
            payStmt.setInt(4, Integer.parse(tripNum));
            payStmt.setInt(5, Integer.parse(tripNum));
            payStmt.setInt(6, Integer.parse(tripNum));*/           
            
            added = true;
        }        
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            if (conn != null) {
            try {
                System.err.print("Transaction is being rolled back");
                conn.rollback();
            } catch(SQLException excep) {
                System.out.println(e.getMessage());
            }
        }
        }
        finally
        {
            if(payStmt != null)
            {
                payStmt.close();
            }
            else
            {
                System.out.println("null paystmt");
            }
            if(reservStmt != null)
            {
                reservStmt.close();
            }
            else
            {
                System.out.println("null reservStmt");
            }
            //hasStmt.close();
            conn.setAutoCommit(true);
        }
        return added;
    }
}
