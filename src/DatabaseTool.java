/**
 * @author Stephen Austin Shiner
 *         Caleb Rollins
 *         Giampreso Vanzzini
 *         Alex Ross
 *         Quentin Wephre
 *         Axel Tovar
 *         Kyle Schmidt
 *          
 * Created at 4:52:50 PM on Nov 30, 2015 using UTF-8 encoding
 * File: DatabaseTool.java
 * License: GNU GPLv3 
 * Purpose: CSC 323 Group Project Prototype
 * Server class, which MUST be run before any clients can connect
 * Accepts requests for employee information, login attempts, clock in/out requests
 * Uses UDP for network communication
 * Uses SQLite database to house user/employee/manager data and to process and
 * respond to SQL queries
 * Database is saved to UserInfo.db in same folder as jar file
 */

import java.util.Scanner;
import java.sql.*;

public class DatabaseTool
{
    public static void main(String[] args)
    {
//        Scanner keyboardInput = new Scanner(System.in);
//        System.out.printf("Enter one of the following keywords:%n%s%n%s%n%s%n", "add", "delete", "update");
//        
//        String userInput = keyboardInput.next();
//        
//        if (userInput.equalsIgnoreCase("add"))
//        {
//            
//        }
        
        Connection c = null;
        Statement stmt = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:UserInfo.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE COMPANY " +
                   "(ID INT PRIMARY KEY     NOT NULL," +
                   " NAME           TEXT    NOT NULL, " + 
                   " AGE            INT     NOT NULL, " + 
                   " ADDRESS        CHAR(50), " + 
                   " SALARY         REAL" +
                   " PASSWORD       CHAR(25)," +
                   " USERTYPE       CHAR(25) NOT NULL)";
      
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    
    
}
