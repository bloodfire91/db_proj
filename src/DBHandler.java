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
    
    public List<Trip> getAllTrips()
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
        
        return allTrips;
    }
}
