/**
 * @author Stephen Austin Shiner
 *         Caleb Rollins
 *         Giampreso Vanzzini
 *         Alex Ross
 *         Quentin Wephre
 *         Axel Tovar
 *         Kyle Schmidt
 *          
 * Created at 11:27:45 AM on Oct 28, 2015 using UTF-8 encoding
 * File: Group_Project_Prototype_2_Server.java
 * License: GNU GPLv3 
 * Purpose: CSC 323 Group Project Prototype
 * Server class, which MUST be run before any clients can connect
 * Accepts requests for employee information, login attempts, clock in/out requests
 * Uses UDP for network communication
 * Uses SQLite database to house user/employee/manager data and to process and
 * respond to SQL queries
 * Database is saved to UserInfo.db in same folder as jar file
 */

import java.net.*;
import java.io.*;
import java.sql.*;

public class Group_Project_Prototype_2_Server
{
    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.err.println("Usage: java Group_Project_Prototype_Server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber))
        {
            while (listening)
            {
                new Group_Project_Prototype_Server_Thread(serverSocket.accept()).start();
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}

class Group_Project_Prototype_Server_Thread extends Thread
{
    private Socket socket = null;

    public Group_Project_Prototype_Server_Thread(Socket socket)
    {
        super("Group_Project_Prototype_Server_Thread");
        this.socket = socket;
    }
    
    public void run()
    {
        try
        (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        )
        {
            String inputLine, outputLine;
            JavaJackProtocol jjp = new JavaJackProtocol();
            //outputLine = jjp.processInput(null);
            //System.out.println(outputLine);

            while ((inputLine = in.readLine()) != null)
            {
                outputLine = jjp.processInput(inputLine);
                out.println(outputLine);
                
                // Check for exit conditions for this Server Thread
                if (outputLine.equalsIgnoreCase("END"))
                    break;
                if (jjp.state == JavaJackProtocol.SENT_END_ACK)
                    break;
            }
            
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}


class JavaJackProtocol
{
    static final int WAITING = 0;
    static final int RCV_SQL = 1;
    static final int SENT_SQL = 2;
    static final int RCV_END = 3;
    static final int SENT_END_ACK = 4; // End connection ack signal has been sent
    static final int RCV_QUERY = 5;

    int state = WAITING; // Initial state is waiting



    public String processInput(String input)
    {
        String output = null;
        
        if (input.startsWith("SQL:"))
        {
            Connection c = null;
            Statement stmt = null;
            
            // Check if our input SQL string will return a resultset
            if (input.contains("SELECT"))
            {
                try
                {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:UserInfo.db");
                    System.out.println("Opened database successfully");

                    stmt = c.createStatement();

                    String sql = input.trim(); // Trim the whitespace off the left and right

                    ResultSet rs = stmt.executeQuery(sql);

                    output = makeStringFromResultSet(rs);

                    stmt.close();
                    c.close();
                }
                catch (Exception e)
                {
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    System.exit(0);
                }
            }
            else if (input.contains("INSERT"))
            {
                try
                {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:UserInfo.db");
                    System.out.println("Opened database successfully");

                    stmt = c.createStatement();

                    String sql = input.trim(); // Trim the whitespace off the left and right
                    stmt.executeUpdate(sql);

                    output = "Successfully added new employee";
                    System.out.println(output);

                    stmt.close();
                    c.close();
                }
                catch (Exception e)
                {
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    System.exit(0);
                }
            }
            else if (input.contains("DELETE"))
            {
                try
                {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:UserInfo.db");
                    System.out.println("Opened database successfully");

                    stmt = c.createStatement();

                    String sql = input.trim(); // Trim the whitespace off the left and right
                    stmt.executeUpdate(sql);

                    output = "Successfully deleted employee";
                    System.out.println(output);

                    stmt.close();
                    c.close();
                }
                catch (Exception e)
                {
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    System.exit(0);
                }
            }
            else
            {
                System.out.println(input + " is not a recognized SQL statement");
            }
            
            state = RCV_SQL; // Switch to received sql information state
        }
        else if (input.startsWith("END"))
        {
            // Time to clean up this thread and connection, mark state
            state = SENT_END_ACK;
        }
        
        
        return output;
    }

    
    /**
     * Token delimiters will be spaces for cells and newline character (\n)
     * delimiting the records
     * 
     * @param rs ResultSet we will make into a String representation
     * @return String representation of the argument ResultSet
     */
    public static String makeStringFromResultSet(ResultSet rs)
    {
        String result = ""; // Will hold the String representation of our result set
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            while (rs.next())
            {
                for (int i = 1; i <= numColumns; i++)
                {
                    String cellData = rs.getString(i);
                    if (i < numColumns)
                    {
                        result += cellData + " "; // Add cell string and space to our result
                    }
                    else
                    {
                        result += cellData + "\n"; // Add cell string and newline to our result
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.exit(0);
        }
        
        return result;
    }
}

