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
            outputLine = jjp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null)
            {
                outputLine = jjp.processInput(inputLine);
                out.println(outputLine);
                
                if (outputLine.equalsIgnoreCase("END"))
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
    private static final int WAITING = 0;
    private static final int RCV_SQL = 1;
    private static final int SENT_SQL = 2;
    private static final int RCV_END = 3;
    private static final int SENT_END_ACK = 4; // End connection ack signal has been sent
    private static final int RCV_QUERY = 5;

    private int state = WAITING; // Initial state is waiting



    public String processInput(String input)
    {
        String output = null;
        
        if (input.startsWith("SQL:"))
        {
            
            state = RCV_SQL; // Switch to received sql information state
        }
        else if (input.startsWith("RCV_ACK"))
        {
            
        }
        else if (input.startsWith("SENT_END"))
        {
            
        }
        
        return output;
    }
    
    public static String sendQuery(String query)
    {
        String result = ""; // Will hold our query result

        try
        {
            
        }
        catch (Exception ex)
        {

        }

        return result; // Return our query result
    }
}

