/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
/**
 *
 * @author Robin
 */
public class Controller {
    private final Display display;
    private User loggedInUser;
    private Payment currentPayment;
    private Trip selectedTrip;
    private Connection conn;
            
    Controller()
    {
        display = new Display(this);
        
        initConnection();
        
    }
    
    public void initConnection()
    {
        try
        {
             DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
             conn = DriverManager.getConnection ("jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl",
                                   "s03022dt", "bUmb0r9s81");
        }
        catch (SQLException e) 
        { 
            System.out.println(e.getMessage());
        }
    }
    
    public User login(String username, String password )
    {
       return new User(username, password);
    }
    
    public void createNewUser(String username, String password )
    {
        
    }
    
    public void displayAllTrips()
    {
        
    }
    
    public void deleteTrip()
    {
        
    }
    
    public void assignNewPlane(int tripNumber, int legNumber, String planeID)
    {
        
    }
    
    public void viewTripLegs()
    {
        
    }
    
    public void viewTripHistory()
    {
        
    }
    
    public void searchTrips(SearchInfo searchInfo)
    {
        
    }
    
    public void setSelectedTrip()
    {
        
    }
    
    public void displayTripsSearch()
    {
        
    }
    
    public void createPayment()
    {
        
    }
    
    public void logout()
    {
        
    }
    
    public void showDisplay()
    {
        display.setVisible(true);
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
            ResultSet rset = stmt.executeQuery ("select username from USERS where username = '" + username + "' and user_password = '" + password + "'");
            
            if(!rset.next())
            {
                System.out.println("user not found");
                validated = true;         
            }
            else
            {
                System.out.println("user found: " + rset.getString("USERNAME"));
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
    
    //display GUI
    public static void main(String args[])
    {
        System.out.println("welcome");
        final Controller controller = new Controller();
       

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                controller.showDisplay();// LEGGO
            }
        });
    }
}
