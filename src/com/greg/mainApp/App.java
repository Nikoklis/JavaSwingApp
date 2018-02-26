package com.greg.mainApp;

import com.greg.airports.Airport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class App extends JFrame {
    private final String AppName = "MediaLab Flight Simulation";


    public App() {
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


        //creating the Menu
        JPopupMenu jPopupMenu = new JPopupMenu();
        createMenuBar(jPopupMenu);


        //the mid-right panel used for the output
        //of informative states to the user
        JPanel rightPanel = new JPanel();
        rightPanel.setSize(1200 - 960, 600 - 480);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setPreferredSize(new Dimension(rightPanel.getWidth(),getHeight()));
        jTextArea.setText("Initializing world...\nLoading world components...\nLoading airports...\n");

        rightPanel.add(jTextArea);


        //the mid-left panel used for the main simulation
        JPanel leftPanel = new JPanel();
        leftPanel.setSize(960, 480);


        //splitPane for the 2 middle panels
        JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        centerPane.setDividerSize(0);
        centerPane.setDividerLocation(940);
        centerPane.setResizeWeight(1);

        //the top panel containing info
        JPanel topPanel = new JPanel();

        //information at the top panel
        createTextInformation(topPanel);


        //new pane for all three panels
        //nested JspliPane to split the screen into 3 parts
        JSplitPane topPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPane, topPanel);
        topPane.setResizeWeight(0.03);

        topPane.setTopComponent(topPanel);
        topPane.setBottomComponent(centerPane);
        this.add(topPane);

        //initialize the Map from mapFile
        initMap(leftPanel);

        String temp = jTextArea.getText();
        temp += "Initialization complete!";
        jTextArea.setText(temp);
    }

    private void initMap(JPanel leftPanel) {
        File worldFile = new File("examples/world_default.txt");
        File airportsFile = new File("examples/airports_default.txt");
        BufferedReader bufferedReaderWorld = null;
        BufferedReader bufferedReaderAirport = null;
        try {
            bufferedReaderWorld = new BufferedReader(new FileReader(worldFile));
            bufferedReaderAirport = new BufferedReader(new FileReader(airportsFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<JPanel> components = new ArrayList<JPanel>();
        ArrayList<Airport> airports = new ArrayList<>();

        initWorld(bufferedReaderWorld, components, leftPanel);
        initAirports(bufferedReaderAirport, airports,components);

    }


    //helper functions called from the initMap
    private void initWorld(BufferedReader bufferedReaderWorld, ArrayList<JPanel> components, JPanel leftPanel) {
        leftPanel.setLayout(new GridLayout(30, 60));
        String line = "";
        String delimeter = ",";

        int[] rgbValues;
        try {
            while ((line = bufferedReaderWorld.readLine()) != null) {
                String[] height = line.split(delimeter);
                for (int i = 0; i < 60; i++) {
//                    Squares squares = new Squares(countLines,i,16,16,height[i]);
//                    leftPanel.add(squares);
//                    add(squares);
                    JPanel temp = new JPanel();
                    rgbValues = checkColour(height[i]);
                    temp.setBackground(new Color(rgbValues[0], rgbValues[1], rgbValues[2]));
                    temp.setSize(16, 16);
                    components.add(temp);
                    leftPanel.add(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //helperfunctions called from the initMap
    private void initAirports(BufferedReader bufferedReaderAirport, ArrayList<Airport> airports, ArrayList<JPanel> components) {
        String line = null;
        String delimeter = ",";

        int type;
        int id;
        int orientation;
        boolean state;
        int[] coordinates = {0, 0};
        int counter = 0;
        try {
            while ((line = bufferedReaderAirport.readLine()) != null) {
                String[] info = line.split(delimeter);
                id = Integer.parseInt(info[0]);
                coordinates[0] = Integer.parseInt(info[1]);
                coordinates[1] = Integer.parseInt(info[2]);
                orientation = Integer.parseInt(info[4]);
                type = Integer.parseInt(info[5]);
                state = Boolean.parseBoolean(info[6]);
                airports.add(new Airport(info[3], coordinates[0], coordinates[1], id, type, orientation, state));
                System.out.println("First coordinate : " + coordinates[0] + " Second coordinate :" + coordinates[1]);

                //we have a 1D matrix and we get 2 dimensions
                //so we have to cast the 2 coordinates to our 1D matrix
                JPanel box = components.get(coordinates[0] * 60 + coordinates[1]);
                Color tempColor = box.getBackground();

                Color oldColor = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue());

                box.setLayout(new OverlayLayout(box));

                JPanel image = new JPanel();
                ImageIcon imageIcon = new ImageIcon("SimulationIcons/airport.png");
                image.setBackground(oldColor);

                image.add(new JLabel(imageIcon));


                box.add(image);

                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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

    private void createTextInformation(JPanel topPanel) {

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

    private JTextField createTextLabel(String s) {
        JTextField jTextField = new JTextField(s);
        jTextField.setEditable(false);
        jTextField.setBorder(BorderFactory.createEmptyBorder());

        return jTextField;
    }

    private void createMenuBar(JPopupMenu jPopupMenu) {
        JMenuBar menuBar = new JMenuBar();

        JMenu game = new JMenu("Game");
        JMenu simulation = new JMenu("Simulation");


        createMenuItem(game, "Start", null);
        createMenuItem(game, "Stop", null);
        createMenuItem(game, "Load", null);
        createMenuItem(game, "Exit", null);

        createMenuItem(simulation, "Airports", jPopupMenu);
        createMenuItem(simulation, "Aircrafts", null);
        createMenuItem(simulation, "Flights", null);


        menuBar.add(game);
        menuBar.add(simulation);


        setJMenuBar(menuBar);
    }

    private void createMenuItem(JMenu jMenu, String infoText, JPopupMenu jPopupMenu) {
        JMenuItem menuItem = new JMenuItem(infoText);
//        menuItem.setMnemonic(KeyEvent.VK_E);
//        menuItem.setToolTipText("Exit application");
//        menuItem.addActionListener((ActionEvent event) -> {
//            System.exit(0);
//        });
        if (infoText.equals("Airports")) {
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                }
            });
            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    if (mouseEvent.isPopupTrigger()) {
                        jPopupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
                    }
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    if (mouseEvent.isPopupTrigger()) {
                        jPopupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
                    }
                }
            });
            jPopupMenu.add(menuItem);

        }// else if (infoText.equals("Aircrafts")) {
//            menuItem.addMouseListener((ActionEvent event) -> {
//
//            });
//        } else if (infoText.equals("Flights")) {
//            menuItem.addMouseListener((ActionEvent event) -> {
//
//            });
//        }
        jMenu.add(menuItem);
    }

    private void showPopUp(MouseEvent event) {
        if (event.isPopupTrigger()) {

        }
    }


}

