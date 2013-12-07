/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

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
    private TripType tripType = TripType.ONE_WAY;
    private HashMap<String, String> selectedTrips;
    
    //private Connection conn;
    //private String userAccess;
    //private String ADMIN = "A";
    //private String RESTRICTED = "R";
    
    Controller(String password)
    {
        display = new Display(this);
        dbHandler = new DBHandler(password);
        selectedTrips = new HashMap<String, String>();
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
        try
        {
            List<Trip> trips = dbHandler.getAllTrips();        
            display.displayAdminTrips(trips);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean removeTrip(String tripNum)
    {
        boolean removed = false;
        try
        {
            removed = dbHandler.removeTrip(tripNum);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());                    
        }
        return removed;
    }
    
    public void fillAdminLegTable(String tripNum)
    {
        try
        {
            List<Leg> legs = dbHandler.getLegs(tripNum);        
            display.displayAdminLegs(legs);
            //dbHandler.getLegs(tripNum);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public List<String> getPlaneOptions()
    {
        List<String> options = null;
        
        try
        {
            options = dbHandler.getPlaneOptions();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return options;
    }
    
    public boolean changePlane(String tripNum, String legNum, String plane)
    {
        boolean changed = false;
        try
        {
            changed = dbHandler.changePlane(tripNum, legNum, plane);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());                    
        }
        return changed;
    
    }
    
    public void setLoggedInUser(String username, String password)
    {
        this.loggedInUser = new User(username, password);
    }
    
    public void fillUserFlightHistoryPanel()
    {
        
        try
        {
            String username = loggedInUser.getUsername();            
            List<Trip> history = dbHandler.getFlightHistory(username);        
            display.displayFlightHistory(history);
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void populateSearchComboBoxes()
    {
        String[] leavingFrom = null;
        String[] goingTo = null;
        
        try
        {
            leavingFrom = dbHandler.getLeavingFromAirports();
            goingTo = dbHandler.getGoingToAirports();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        display.setLeavingGoingCombos(leavingFrom, goingTo);
    }
    
    public void setTripType(TripType t)
    {
        this.tripType = t;
        System.out.println("triptype: " + this.tripType);
        
    }
    
    public TripType getTripType()
    {
        return tripType;        
    }    
    
    public void getOneWaySearchResults(String leavingCode, String goingCode, String goingLowerBound, String goingUpperBound)
    {
        try
        {
            List<Trip> searchResults = dbHandler.getSearchResults(leavingCode, goingCode, goingLowerBound, goingUpperBound);
            System.out.println("search size: " + searchResults.size());
            display.displayDepartingResultsTable(searchResults);            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }   
    }
    
    public void getRoundSearchResults(String leavingCode, String goingCode, String goingLowerBound, String goingUpperBound,
            String leavingLowerBound, String leavingUpperBound)
    {
        try
        {
            List<Trip> returnResults = dbHandler.getSearchResults(goingCode, leavingCode, goingLowerBound, goingUpperBound);//switch codes for way back
            //System.out.println("search size: " + searchResults.size());
            List<Trip> departResults = dbHandler.getSearchResults(leavingCode, goingCode, leavingLowerBound, leavingUpperBound);
            
            if(returnResults.size() > 0 && departResults.size() > 0)
            {
                display.displayReturningResultsTable(returnResults);
                display.displayDepartingResultsTable(departResults);
            }
            else
            {
                display.clearRoundSearchResults();
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }   
    }
    
    public void clearSelectedTrips()
    {
        selectedTrips.clear();
    }
    
    public void addSelectedTrip(String tripNum, String price)
    {
        selectedTrips.put(tripNum, price);
    }
    
    public String getTotalPayment()
    {
        Set<String> set = selectedTrips.keySet();
        int total = 0;
        for(String s: set)
        {
            total += Integer.parseInt(selectedTrips.get(s));
        }
        
        return String.valueOf(total);
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
