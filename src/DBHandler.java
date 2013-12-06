/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
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
    
    public List<Trip> getFlightHistory()
    {
        
    }
}
