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
import java.util.Random;

public class DatabaseTool
{
    public static void main(String[] args)
    {
        
       
        Connection c = null;
        Statement stmt = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:UserInfo.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            
            Scanner keyboardInput = new Scanner(System.in);
            System.out.printf("Enter one of the following keywords:%n%s%n%s%n%s%nEnter keyword here: ",
                                "(l)ookup", "(a)dd", "(d)elete");
            String sql ="";

            String userInput = keyboardInput.nextLine();

            if (userInput.startsWith("l")) // Lookup
            {
                sql = "SELECT * FROM COMPANY";
                ResultSet rs = stmt.executeQuery(sql);
                
                ResultSetMetaData rsmd = rs.getMetaData();
                int numColumns = rsmd.getColumnCount();
                
                while (rs.next())
                {
                    for (int i = 1; i <= numColumns; i++)
                    {
                        String cellData = rs.getString(i);
                        System.out.print(cellData + " ");
                    }
                    System.out.println();
                }
            }
            else if (userInput.startsWith("a")) // Add
            {          
                int id = randInt(0, 9999);
                
                System.out.printf("Enter the %s: ", "Name");
                String name = keyboardInput.nextLine();
                
                System.out.printf("Enter the %s: ", "Age");
                int age = Integer.parseInt(keyboardInput.nextLine());

                System.out.printf("Enter the %s: ", "Address");
                String address = keyboardInput.nextLine();
                
                System.out.printf("Enter the %s: ", "Salary");
                double salary = Double.parseDouble(keyboardInput.nextLine());
                
                System.out.printf("Enter the %s: ", "Password");
                String pass = keyboardInput.nextLine();
                
                System.out.printf("Enter the %s: ", "Type");
                String type = keyboardInput.nextLine();
                
                sql = "INSERT INTO COMPANY VALUES(" +
                        id + ",'" + name + "'," + age + ",'" +
                        address + "'," + salary + ",'" +
                        pass + "','" + type + "')";
                
                stmt.executeUpdate(sql);
                System.out.println("Successfully added " + name);
            }
            else if (userInput.startsWith("d")) // delete
            {
                System.out.printf("Enter the %s: ", "id number");
                int targetID = keyboardInput.nextInt();
                
                sql = "DELETE FROM COMPANY WHERE ID=" + targetID;
                
                stmt.executeUpdate(sql);
                System.out.println("Successfully deleted id # " + targetID);
            }
            else
            {
                while (    !userInput.startsWith("l")
                        && !userInput.startsWith("a")
                        && !userInput.startsWith("d") )
                {
                    System.out.printf("Enter one of the following keywords:%n%s%n%s%n%s%nEnter keyword here: ",
                                "(l)ookup", "(a)dd", "(d)elete");

                    userInput = keyboardInput.nextLine();
                }
            }
            
            stmt.close();
            c.close();
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    //            String sql = "CREATE TABLE COMPANY " +
//                   "(ID INT PRIMARY KEY     NOT NULL," +
//                   " NAME           TEXT    NOT NULL, " + 
//                   " AGE            INT     NOT NULL, " + 
//                   " ADDRESS        CHAR(50), " + 
//                   " SALARY         REAL," +
//                   " PASSWORD       CHAR(25)," +
//                   " USERTYPE       CHAR(25) NOT NULL)";
    
    public static int randInt(int min, int max)
    {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        rand.setSeed(System.currentTimeMillis());
        
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
