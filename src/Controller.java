/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.List;
/**
 *
 * @author Robin
 */
public class Controller {
    private final Display display;
    private User loggedInUser;
    private Payment currentPayment;
    private Trip selectedTrip;
    private DBHandler dbHandler;
    //private Connection conn;
    //private String userAccess;
    //private String ADMIN = "A";
    //private String RESTRICTED = "R";
    
    Controller(String password)
    {
        display = new Display(this);
        dbHandler = new DBHandler(password);
        //userAccess = new String("R");
        //initConnection();
        
    }
    
    /*public void initConnection()
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
    }*/
    
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
    
    public boolean validateUser(String username, String password)
    {
        boolean validated = false;
        try
        {
            validated = dbHandler.validateUser(username, password);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return validated;
    }
    
    public boolean addUser(String username, String password) throws SQLException
    {
        boolean added = false;
        try
        {
            added = dbHandler.addUser(username, password);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return added;
    }
     
    public String getUserAccess()
    {
        return dbHandler.getUserAccess();
    }
    
    public String getAdmin()
    {
        return dbHandler.getAdmin();
    }
    
    public String getRestricted()
    {
        return dbHandler.getRestricted();
    }
    
    public void fillAdminTripTable()
    {
        List<Trip> trips = dbHandler.getAllTrips();        
        display.displayAdminTrips(trips);
    }
    
    //display GUI
    public static void main(String args[])
    {
        
        if(args.length < 1 )
        {
            System.out.println("db password not provided");
            System.exit(1);
        }
        
        System.out.println("welcome");
        final Controller controller = new Controller(args[0]);
       

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                controller.showDisplay();// LEGGO
            }
        });
    }
}
