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
    ResultSet rset = stmt.executeQuery ("select * from AIRPLANE");

    // Iterate through the result and print the employee names
    while (rset.next ())
      System.out.println (rset.getString (1));

    conn.close(); // ** IMPORTANT : Close connections when done **
  }
}
