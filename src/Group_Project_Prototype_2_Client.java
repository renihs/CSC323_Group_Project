/**
 * @author Stephen Austin Shiner
 *         Caleb Rollins
 *         Giampiero Vanzzini
 *         Alex Ross
 *         Quentin Wephre
 *         Axel Tovar
 *         Kyle Schmidt
 *
 * Created at 11:27:45 AM on Oct 28, 2015 using UTF-8 encoding
 * File: Group_Project_Prototype_2_Client.java
 * License: GNU GPLv3
 * Purpose: CSC 323 Group Project Prototype
 * Client GUI contains login panel, employee panel(s), and manager panel(s)
 * GUI utilizing the CardLayout in order to stack content panels
 * Uses TCP/IP to send and receive information to/from the server, including
 * SQL queries. Maintains connection until application run is terminated.
 *
 */

// Import declarations
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

public class Group_Project_Prototype_2_Client implements ItemListener
{
    JPanel cards; // Panel that uses CardLayout, will hold our panels ("cards")
    final static String BUTTONPANEL = "Card with JButtons";
    final static String TEXTPANEL = "Card with JTextField";
    final static String LOGINPANEL = "Card with Login";
    final static String MANAGERPANEL_1 = "Card - Manager Panel 1";
    final static String EMPLOYEEPANEL_1 = "Card - Employee Panel 1";
    final static String EMPLOYEEPANEL_2 = "Card - Employee Panel 2";

    public static void main(String[] args)
    {
        try
        {   // Loop iterates through system looks and feels and sets theme to Nimbus
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        catch (InstantiationException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (Exception ex) // Catch all
        {
            ex.printStackTrace();
        }

        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    static void createAndShowGUI()
    {
        // Create and set up the window.
        JFrame frame = new JFrame("J Jacks Tire Repair"); // Our window's overall frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close oepration to end program

        // Create and set up the content pane.
        Group_Project_Prototype_2_Client guiListener = new Group_Project_Prototype_2_Client();
        guiListener.addComponentToPane(frame.getContentPane());

        // Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void addComponentToPane(Container panel)
    {
        LoginPanel cardLogin = new LoginPanel(); // Create our login panel
        ManagerPanel1 cardManager1 = new ManagerPanel1(); // Create our manager's first panel
        EmployeePanel1 cardEmployee1 = new EmployeePanel1(); // Create our employee's first panel
        EmployeePanel2 cardEmployee2 = new EmployeePanel2(); // Create our second employee panel

        // Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL, LOGINPANEL, MANAGERPANEL_1, EMPLOYEEPANEL_1, EMPLOYEEPANEL_2 };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        // Create the "cards".
        JPanel card1 = new JPanel();
        card1.add(new JButton("Button 1"));
        card1.add(new JButton("Button 2"));
        card1.add(new JButton("Button 3"));

        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));

        // Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, BUTTONPANEL);
        cards.add(card2, TEXTPANEL);
        cards.add(cardLogin, LOGINPANEL);
        cards.add(cardManager1, MANAGERPANEL_1);
        cards.add(cardEmployee1, EMPLOYEEPANEL_1);
        cards.add(cardEmployee2, EMPLOYEEPANEL_2);

        panel.add(comboBoxPane, BorderLayout.PAGE_START);
        panel.add(cards, BorderLayout.CENTER);
    }

    /**
     * Part of the ItemListener implementation,
     * contains functionality to process event actions and show new panel
     *
     * @param evt Item Event which trigger this event handler
     */
    @Override
    public void itemStateChanged(ItemEvent evt)
    {
        CardLayout cl = (CardLayout)(cards.getLayout()); // Get and cast our CardLayout
        cl.show(cards, (String)evt.getItem()); // Show the panel associated with the event's item String
    }

    /**
     * Shows a new panel in the main GUI frame using the argument String
     *
     * @param panelIdentifier Panel identifier String
     */
    public void switchToPanel(String panelIdentifier)
    {
        CardLayout cl = (CardLayout)(cards.getLayout()); // Get and cast our CardLayout
        cl.show(cards, panelIdentifier); // Show the panel associated with the argument String
    }

    /* *
     * Adds 6.75% for tax.
     * $10 mounting fee if < 4 tires.
     * Rims are always mounted free
     * Give employee 10% commission (before tax) if >= 4 tires AND >= 4 rims
     */
    public void calculateSale()
    {
        float total = 0;
    }

    /**
     * Our event listener for all button events
     * Performs appropriate action based on origin event's command string
     *
     */
    class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String commandText = e.getActionCommand(); // Get action command text

            System.out.println("Command Text: " + commandText);

            if (commandText.equalsIgnoreCase("login")) // Check for login button
            {
                // TODO:
                // Add code to create instance of socket object,
                // attempt to connect with server, and if successful,
                // send login information to the server
                //
                // Display correct panel based on login results
                //

                switchToPanel(MANAGERPANEL_1); // Switch to first manager panel
            }
            else if (commandText.equalsIgnoreCase("quit")) // Check for quit button
            {
                System.exit(0); // End program run
            }
            else if (commandText.equalsIgnoreCase("saleScreen")) // Check for sales button in employee GUI
            {
                switchToPanel(EMPLOYEEPANEL_2); // Switch to second employee panel
            }
            else if (commandText.equalsIgnoreCase("backToClock")) // Check for back button in employee GUI
            {
                switchToPanel(EMPLOYEEPANEL_1); // Switch back to first employee panel
            }
            else if (commandText.equalsIgnoreCase("submit")) // Check for submit button in employee sales panel
            {
                calculateSale();
            }
        }
    }

    class ClickListener implements MouseListener
    {

        // <editor-fold defaultstate="collapsed" desc="Unimplemented Mouse Event Handlers">
        @Override
        public void mousePressed(MouseEvent me) { }

        @Override
        public void mouseReleased(MouseEvent me) { }

        @Override
        public void mouseEntered(MouseEvent me) { }

        @Override
        public void mouseExited(MouseEvent me) { } // </editor-fold>

        @Override
        public void mouseClicked(MouseEvent me)
        {
            if (me.getButton() == MouseEvent.BUTTON3) // Check if mouse was right-clicked
            {
                String componentName = me.getComponent().getName(); // Get clicked component's name
                System.out.println("Component right-clicked was: " + componentName);
                // Now we need to determine which component was right-clicked
                if (componentName.equalsIgnoreCase("tblEmployeeInfo"))
                {
                    PopUpTableMenu menu = new PopUpTableMenu(); // Create popup menu object
                    menu.show(me.getComponent(), me.getX(), me.getY()); // Display popup menu
                }
            }
        }
    }


    /**
     * This class acts as the menu when the employee table is right-clicked
     */
    class PopUpTableMenu extends JPopupMenu
    {
        private JMenuItem itmAddEmployee;
        private JMenuItem itmRemoveEmployee;

        public PopUpTableMenu()
        {
            // Create our menu component objects
            itmAddEmployee = new JMenuItem("Add New Employee");
            itmRemoveEmployee = new JMenuItem("Remove Employee");

            // Add our component objects to the menu
            this.add(itmAddEmployee);
            this.add(itmRemoveEmployee);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Login Panel">
    class LoginPanel extends JPanel
    {
        // Declare our component objects which will be on this panel
        private JLabel lblUsername;
        private JLabel lblPassword;

        private JTextField txtUsername;
        private JPasswordField pwdPassword;

        private JButton btnLogin;
        private JButton btnQuit;

        public LoginPanel()
        {
            // Set login panel component layout
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            // Create and customize our components for this panel
            lblUsername = new JLabel("Username: ");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            this.add(lblUsername, c); // Add our component to the panel

            txtUsername = new JTextField();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridwidth = 2;
            c.gridx = 1;
            c.gridy = 0;
            this.add(txtUsername, c); // Add our component to the panel

            lblPassword = new JLabel("Password: ");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 1;
            this.add(lblPassword, c); // Add our component to the panel

            pwdPassword = new JPasswordField();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridwidth = 2;
            c.gridx = 1;
            c.gridy = 1;
            this.add(pwdPassword, c); // Add our component to the panel

            btnLogin = new JButton("Login");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 2;
            this.add(btnLogin, c); // Add our component to the panel

            btnQuit = new JButton("Quit");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = 2;
            this.add(btnLogin, c); // Add our component to the panel

            // Create event listener/handler objects
            ButtonListener btnListener = new ButtonListener();
            // Register event listeneres/handlers
            btnLogin.addActionListener(btnListener);
            btnQuit.addActionListener(btnListener);
        }
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Manager Panel 1">
    class ManagerPanel1 extends JPanel
    {
        // Declare our component objects which will be on this panel
        private JTable tblEmployeeInfo;

        private JButton btnQuit;

        public ManagerPanel1()
        {
            // Create our click listener object for our table
            ClickListener clickListener = new ClickListener();

            // Create and customize our components for this panel
            tblEmployeeInfo = new JTable();
            tblEmployeeInfo.setName("tblEmployeeInfo");

            btnQuit = new JButton("Quit");

            // Create event listener/handler objects
            ButtonListener btnListener = new ButtonListener();
            // Register event listeneres/handlers
            btnQuit.addActionListener(btnListener);

            tblEmployeeInfo.addMouseListener(clickListener);

            // Set manager panel #1 component layout
            this.setLayout(new GridLayout(3, 3));

            // Add our components to the panel
            this.add(tblEmployeeInfo);
            this.add(btnQuit);
        }
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Employee Panel 1">
    class EmployeePanel1 extends JPanel
    {
        // Declare our component objects which will be on this panel
        private JLabel lblClockStatus;

        private JButton btnClockIn;
        private JButton btnClockOut;
        private JButton btnSale;
        private JButton btnLogOut;

        private String clockStatus;

        public EmployeePanel1()
        {
            // Set first employee panel component layout
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            clockStatus = "Clocked Out"; // Set initial clock status

            // Create and customize our components for this panel
            lblClockStatus = new JLabel("Current clock status is " + clockStatus);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 0;
            this.add(lblClockStatus, c); // Add our component to the panel

            btnClockIn = new JButton("Clock In");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 1;
            this.add(btnClockIn, c); // Add our component to the panel

            btnClockOut = new JButton("Clock Out");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = 1;
            this.add(btnClockOut, c); // Add our component to the panel

            btnSale = new JButton("Sale");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 2;
            btnSale.setActionCommand("saleScreen");
            this.add(btnSale, c); // Add our component to the panel

            btnLogOut = new JButton("Log Out");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = 2;
            this.add(btnLogOut, c); // Add our component to the panel

            // Create event listener/handler objects
            ButtonListener btnListener = new ButtonListener();
            // Register event listeneres/handlers
            btnClockIn.addActionListener(btnListener);
            btnClockOut.addActionListener(btnListener);
            btnLogOut.addActionListener(btnListener);
            btnSale.addActionListener(btnListener);
        }
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Employee Panel 2">
    class EmployeePanel2 extends JPanel
    {
        // Declare our component objects which will be on this panel
        private JLabel lblNameOfTire;
        private JLabel lblTireModelNumber;
        private JLabel lblNumberTiresSold;
        private JLabel lblTireUnitPrice;
        private JLabel lblRimName;
        private JLabel lblRimModelNumber;
        private JLabel lblNumberRimsSold;
        private JLabel lblRimUnitPrice;

        //private JTextField txtNameOfTire;
        private JComboBox cbxNameOfTire;
        private JComboBox cbxNameOfRim;

        private JTextField txtTireModelNumber;
        private JTextField txtNumberTiresSold;
        private JTextField txtTireUnitPrice;
        //private JTextField txtRimName;
        private JTextField txtRimModelNumber;
        private JTextField txtNumberRimsSold;
        private JTextField txtRimUnitPrice;

        private JButton btnSubmit;
        private JButton btnBack;

        public EmployeePanel2()
        {
            // Create and customize our components for this panel
            lblNameOfTire = new JLabel("Name of Tire: ");
            lblTireModelNumber = new JLabel("Tire Model Number: ");
            lblNumberTiresSold = new JLabel("Number of Tires Sold: ");
            lblTireUnitPrice = new JLabel("Tire Unit Price: ");
            lblRimName = new JLabel("Name of Rim: ");
            lblRimModelNumber = new JLabel("Rim Model Number: ");
            lblNumberRimsSold = new JLabel("Number of Rims Sold: ");
            lblRimUnitPrice = new JLabel("Rim Unit Price: ");

            String[] namesOfTires = { "Standard", "Racing", "Off-Road" };
            cbxNameOfTire = new JComboBox(namesOfTires);
            String[] namesOfRims = { "Standard", "Spinning", "Metal" };
            cbxNameOfRim = new JComboBox(namesOfRims);

            txtTireModelNumber = new JTextField();
            txtNumberTiresSold = new JTextField();
            txtTireUnitPrice = new JTextField();
            txtRimModelNumber = new JTextField();
            txtNumberRimsSold = new JTextField();
            txtRimUnitPrice = new JTextField();

            btnSubmit = new JButton("Submit");
            btnSubmit.setActionCommand("submit");
            btnBack = new JButton("Back To Clock");
            btnBack.setActionCommand("backToClock");

            // Create event listener/handler objects
            ButtonListener btnListener = new ButtonListener();
            // Register event listeneres/handlers
            btnSubmit.addActionListener(btnListener);
            btnBack.addActionListener(btnListener);

            // Set employee panel #1 component layout
            this.setLayout(new GridLayout(9, 2));

            // Add our components to the panel
            this.add(lblNameOfTire);
            this.add(cbxNameOfTire);
            this.add(lblTireModelNumber);
            this.add(txtTireModelNumber);
            this.add(lblNumberTiresSold);
            this.add(txtNumberTiresSold);
            this.add(lblTireUnitPrice);
            this.add(txtTireUnitPrice);
            this.add(lblRimName);
            this.add(cbxNameOfRim);
            this.add(lblRimModelNumber);
            this.add(txtRimModelNumber);
            this.add(lblNumberRimsSold);
            this.add(txtNumberRimsSold);
            this.add(lblRimUnitPrice);
            this.add(txtRimUnitPrice);

            this.add(btnBack);
            this.add(btnSubmit);

        }


    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Client Network Class">
    class ClientNetworkProgram
    {
        private String state;
        private String host;
        private int port;
        private String command;
        //private Socket cSocket;

        public ClientNetworkProgram(String hostName, int portNumber)
        {
            this.state = "WAITING"; // Set initial client state to waiting
            this.host = hostName; // Set the target server's host name
            this.port = portNumber; // Set our client's port number
            this.command = null; // Set internal command to null
            //this.cSocket = null; // Set our socket object attribute to null
        }

        public void connect()
        {
//            try
//            (
//                Socket cSocket = new Socket(this.host, this.port);
//                PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
//            )
            try
            (
                Socket cSocket = new Socket(this.host, this.port);
                PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            )
            {
                String fromServer;

                // While still receiving/processing network communications, loop runs
                while ((fromServer = in.readLine()) != null)
                {
                    System.out.println("Server: " + fromServer);

                    // If client receives END message, perform cleanup
                    if (fromServer.equalsIgnoreCase("END"))
                    {
                        System.out.println("TCP/IP Session is ending.");
                        break;
                    }

                    if (command != null)
                    {
                        System.out.println("Client: " + command);
                        out.println(command); // Send client data to server
                    }
                }
            }
            catch (UnknownHostException e)
            {
                System.err.println("Don't know about host " + this.host);
                System.exit(1);
            }
            catch (IOException e)
            {
                System.err.println("Couldn't get I/O for the connection to " + this.host);
                System.exit(1);
            }
        }

        public void sendMessage(String msg)
        {
            this.command = msg;
        }

//        public String sendMessage(String msg)
//        {
//            String response = ""; // Will hold the response from the server
//            return response; // Return the server's response
//        }

    } // </editor-fold>
}


