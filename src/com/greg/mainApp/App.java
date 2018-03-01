package com.greg.mainApp;

import com.greg.airports.Airport;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class App extends JFrame {
    private final String AppName = "MediaLab Flight Simulation";
    private App app;

    //files for airports,aircrafts,flights
    private File airportFile;
    private File worldFile;
    private File flightFile;

    //the mapid the user gives
    //default -1
    private int MAPID = -1;

    //components for the map
    ArrayList<JPanel> components = new ArrayList<JPanel>();
    ArrayList<Airport> airports = new ArrayList<Airport>();

    //textArea for the right panel
    //usefull information presented to the user
    JTextArea information = new JTextArea();


    public App() {
        app = this;
        initUI();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);

        });
    }

    private void initUI() {
        setTitle(AppName);
        setSize(1200, 600);

        //center the window on the screen
        setLocationRelativeTo(null);

        //closes the window when you press the Close button of the title bar
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //the mid-right panel used for the output
        //of informative states to the user
        JPanel rightPanel = new JPanel();
        rightPanel.setSize(1200 - 960, 600 - 480);

        information.setEditable(false);
        information.setPreferredSize(new Dimension(rightPanel.getWidth(), getHeight()));

        rightPanel.add(information);


        //the mid-left panel used for the main simulation
        JPanel leftPanel = new JPanel();
        leftPanel.setSize(960, 480);

        //creating the Menu
        createMenuBar(leftPanel);


        //we need a split pane for the 2 middle panels
        JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        centerPane.setDividerSize(0);
        centerPane.setDividerLocation(940);
        centerPane.setResizeWeight(1);

        //the top panel containing info
        JPanel topPanel = new JPanel();

        //information at the top panel
        createTextInformation(topPanel);


        //new pane for all three panels
        //nested JsplitPane to split the screen into 3 parts
        //we provide our first "middle" spliPane and the new Panel of the top
        JSplitPane topPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPane, topPanel);
        topPane.setResizeWeight(0.03);

        //set the top and bottom panels -- bottom via the centerPane we have
        //that splits the rightPanel and the leftPanel panels
        topPane.setTopComponent(topPanel);
        topPane.setBottomComponent(centerPane);
        this.add(topPane);

        //initialize the Map from mapFile
        initMap(leftPanel);


    }

    private void initMap(JPanel leftPanel) {

        //string manipulation for world and airportFile
        //names ++ MAPID --default is -1 if user has not given
        //something yet
        String worldFileName = "world_";
        String airportFileName = "airports_";
        if (MAPID == -1) {
            worldFileName = "world_default.txt";
            airportFileName = "airports_default.txt";
        } else {
            worldFileName += MAPID;
            worldFileName += ".txt";

            airportFileName += MAPID;
            airportFileName += ".txt";
        }

        worldFile = new File(worldFileName);
        airportFile = new File(airportFileName);
        //check if worldFile and airportFile can be opened to begin
        //loading information from them
        if (!worldFile.exists() || worldFile.isDirectory() ||
                !airportFile.exists() || airportFile.isDirectory()) {
            JOptionPane.showMessageDialog(null, "Error finding files with the given MAPID", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            loadInfo(leftPanel);
        }

    }

    //actual function to load information from files airportFile
    //and worldFile
    private void loadInfo(JPanel leftPanel) {
        BufferedReader bufferedReaderWorld = null;
        BufferedReader bufferedReaderAirport = null;
        try {
            bufferedReaderWorld = new BufferedReader(new FileReader(worldFile));
            bufferedReaderAirport = new BufferedReader(new FileReader(airportFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //reset the jPanel components to 0
        //usefull when we reload information
        //from different file
        for (JPanel jPanel : components) {
            leftPanel.remove(jPanel);
        }
        components.removeAll(components);
        airports.removeAll(components);
        ////////


        //initialize the world and airports
        initWorld(bufferedReaderWorld, components, leftPanel);
        initAirports(bufferedReaderAirport, airports, components);

        //updates the right panel information for the user
        information.append("Intialization complete!\n");
    }


    //helper functions called from the initMap
    private void initWorld(BufferedReader bufferedReaderWorld, ArrayList<JPanel> components, JPanel leftPanel) {
        //update right panel information
        information.append("Initializing world\nLoading world components...\n");
        leftPanel.setLayout(new GridLayout(30, 60));
        String line = "";
        String delimeter = ",";

        int[] rgbValues;
        try {
            //start reading the world file to setup the world
            while ((line = bufferedReaderWorld.readLine()) != null) {
                String[] height = line.split(delimeter);
                for (int i = 0; i < 60; i++) {
                    //we create a JPANEL for each rectangle
                    //in order to draw our world
                    JPanel temp = new JPanel();
                    //helper function for checking the colour from the input
                    //we got from the file
                    rgbValues = checkColour(height[i]);

                    //give the rectangles color and size
                    temp.setBackground(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                    temp.setSize(16, 16);

                    //update our Arraylist of Jpanel-Rectangles
                    components.add(temp);
                    //update the leftPanel with the new component
                    leftPanel.add(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //helperfunctions called from the initMap
    private void initAirports(BufferedReader bufferedReaderAirport, ArrayList<Airport> airports, ArrayList<JPanel> components) {
        //update the right panel information
        information.append("Loading airports...\n");
        String line = null;
        String delimeter = ",";

        int type;
        int id;
        int orientation;
        int tempState;
        boolean state;
        int[] coordinates = {0, 0};

        try {
            //start reading the airport file
            while ((line = bufferedReaderAirport.readLine()) != null) {
                String[] info = line.split(delimeter);

                id = Integer.parseInt(info[0]);
                coordinates[0] = Integer.parseInt(info[1]);
                coordinates[1] = Integer.parseInt(info[2]);
                orientation = Integer.parseInt(info[4]);
                type = Integer.parseInt(info[5]);

                //value is an int of 1 or 0 in the file
                //so we have to check with integer value
                tempState = Integer.parseInt(info[6]);
                if (tempState == 1)
                    state = true;
                else
                    state = false;

                //update our Airport ArrrayList with the values we got from the user
                airports.add(new Airport(info[3], coordinates[0], coordinates[1], id, type, orientation, state));

                //we have a 1D matrix and we get 2 dimensions
                //so we have to cast the 2 coordinates to our 1D matrix
                JPanel box = components.get(coordinates[0] * 60 + coordinates[1]);

                //we need to set an image on top of our background
                //so we store the oldvalue and paint an ImageIcon on top
                //then we reset the background to the old value
                Color tempColor = box.getBackground();
                Color oldColor = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue());

                box.setLayout(new OverlayLayout(box));

                JPanel image = new JPanel();
                ImageIcon imageIcon = new ImageIcon("SimulationIcons/airport.png");
                image.setBackground(oldColor);

                image.add(new JLabel(imageIcon));
                box.add(image);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //helper function for checking the
    //colour to be given to a rectangle
    private int[] checkColour(String colour) {
        int colourValue = Integer.parseInt(colour);
        int[] returnRGB = {0, 0, 0};
        if (colourValue == 0) {
            returnRGB[0] = 0;
            returnRGB[1] = 0;
            returnRGB[2] = 255;
        } else if (colourValue <= 200 && colourValue > 0) {
            returnRGB[0] = 60;
            returnRGB[1] = 179;
            returnRGB[2] = 113;
        } else if (colourValue <= 400 && colourValue > 200) {
            returnRGB[0] = 46;
            returnRGB[1] = 139;
            returnRGB[2] = 87;
        } else if (colourValue <= 700 && colourValue > 400) {
            returnRGB[0] = 34;
            returnRGB[1] = 139;
            returnRGB[2] = 34;
        } else if (colourValue <= 1500 && colourValue > 700) {
            returnRGB[0] = 222;
            returnRGB[1] = 184;
            returnRGB[2] = 135;
        } else if (colourValue <= 3500 && colourValue > 1500) {
            returnRGB[0] = 205;
            returnRGB[1] = 133;
            returnRGB[2] = 63;
        } else if (colourValue > 3500) {
            returnRGB[0] = 145;
            returnRGB[1] = 80;
            returnRGB[2] = 20;
        }

        return returnRGB;
    }


    //helper function to initalize the top panel
    private void createTextInformation(JPanel topPanel) {
        //create 4 text fields to store the information on
        //the top panel
        JTextField simulatedTime = createTextLabel("Simulated Time:");
        JTextField totalAircrafts = createTextLabel("Total Aircrafts:");
        JTextField collisions = createTextLabel("Collisions:");
        JTextField landings = createTextLabel("Landings:");

        //set the layout as a box --the text fields have no borders
        BoxLayout boxLayout = new BoxLayout(topPanel, BoxLayout.X_AXIS);
        topPanel.setLayout(boxLayout);

        topPanel.add(simulatedTime);
        topPanel.add(totalAircrafts);
        topPanel.add(collisions);
        topPanel.add(landings);
    }

    //helper function for creating the Label
    //and setting specific values to them
    private JTextField createTextLabel(String s) {
        JTextField jTextField = new JTextField(s);
        jTextField.setEditable(false);
        jTextField.setBorder(BorderFactory.createEmptyBorder());

        return jTextField;
    }


    
    private void createMenuBar(JPanel leftPanel) {
        JMenuBar menuBar = new JMenuBar();

        JMenu game = new JMenu("Game");
        JMenu simulation = new JMenu("Simulation");


        //creating the menu items
        createMenuItem(game, "Start", leftPanel);
        createMenuItem(game, "Stop", leftPanel);
        createMenuItem(game, "Load", leftPanel);
        createMenuItem(game, "Exit", leftPanel);

        createMenuItem(simulation, "Airports", leftPanel);
        createMenuItem(simulation, "Aircrafts", leftPanel);
        createMenuItem(simulation, "Flights", leftPanel);
        ////

        menuBar.add(game);
        menuBar.add(simulation);


        setJMenuBar(menuBar);
    }


    private void createMenuItem(JMenu jMenu, String infoText, JPanel leftPanel) {
        JMenuItem menuItem = new JMenuItem(infoText);

        if (infoText.equals("Load")) {
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String mapID = JOptionPane.showInputDialog(app,
                            "Please provide with your MapID", null);
                    MAPID = Integer.parseInt(mapID);

                    information.setText("");
                    information.setText("Reloading...\n");
                    initMap(leftPanel);
                    revalidate();
//                    repaint();

                }
            });
        } else if (infoText.equals("Exit")) {
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            });
        } else if (infoText.equals("Airports")) {
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String[] columnNames = {
                            "ID", "Name", "Category", "State", "Orientation"
                    };


                    JDialog dialog = new JDialog();
                    Object[][] data = new Object[airports.size()][5];
                    for (int i = 0; i < airports.size(); i++) {
                        data[i][0] = airports.get(i).getId();
                        data[i][1] = airports.get(i).getName();
                        data[i][2] = airports.get(i).getType();
                        data[i][3] = airports.get(i).getState();
                        data[i][4] = airports.get(i).getOrientation();
                    }

                    JTable jTable = new JTable(data, columnNames);
                    JScrollPane jScrollPane = new JScrollPane(jTable);
                    dialog.add(jScrollPane);

                    dialog.setSize(400, 200);
                    dialog.setTitle("Airports");
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            });
        } else if (infoText.equals("Aircrafts")) {
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                }
            });
        } else if (infoText.equals("Flights")) {
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                }
            });
        }
        jMenu.add(menuItem);
    }
}

