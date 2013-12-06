/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */

import java.sql.*;

public class testConnect {
     public static void main (String args [])
       throws SQLException
  {
    // Load the Oracle JDBC driver
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

    // Connect to the database
    // You must put a database name after the @ sign in the connection URL.
    // You can use either the fully specified SQL*net syntax or a short cut
    // syntax as <host>:<port>:<sid>.  The example uses the short cut syntax.
    Connection conn =
      DriverManager.getConnection ("jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl",
                                   "s03022dt", "bUmb0r9s81");

    // Create a Statement
    Statement stmt = conn.createStatement ();

    // Select the ENAME column from the EMP table
    String uname = "t";
    String pswd = "t";
    ResultSet rset = stmt.executeQuery ("select username from USERS where username = '" + uname + "' and user_password = '" + pswd + "'");

    // Iterate through the result and print the employee names
    //while (rset.next ())
    //  System.out.println ("string index: " + rset.getString ("username"));
    if(!rset.next())
    {
        System.out.println("not there");
    }
    else
    {
        System.out.println("there");
    }
    
    System.out.println("inserting");
    String insertUser = "INSERT INTO USERS(USERNAME, USER_PASSWORD, USER_ACCESS) VALUES('t', 't', 't')";
    Statement insertStatement = conn.createStatement();
    
    try
    {
        insertStatement.executeUpdate(insertUser);
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    stmt.close();
    conn.close(); // ** IMPORTANT : Close connections when done **
  }
}
