package com.greg;

import javax.swing.*;
import javax.swing.border.Border;
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


        //the mid-left panel used for the main simulation
        JPanel leftPanel = new JPanel();
        leftPanel.setSize(960, 480);
//        leftPanel.setBackground(Color.blue);

        //initialize the Map from mapFile
        initMap(leftPanel);


        //the mid-right panel used for the output
        //of informative states to the user
        JPanel rightPanel = new JPanel();
        rightPanel.setSize(getWidth() - 150, getHeight());
        rightPanel.setBackground(Color.RED);


        //splitPane for the 2 middle panels
        JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        centerPane.setDividerSize(10);
        centerPane.setResizeWeight(0.90);

        //the top panel containing info
        JPanel topPanel = new JPanel();

        //information at the top panel
        createTextInformation(topPanel);


        //new pane for all three panels
        //nested JspliPane to split the screen into 3 parts
        JSplitPane topPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPane, topPanel);
//        topPane.setDividerLocation(5 - getHeight());
        topPane.setResizeWeight(0.03);

        topPane.setTopComponent(topPanel);
        topPane.setBottomComponent(centerPane);
        this.add(topPane);
    }

    private void initMap(JPanel leftPanel) {
        File file = new File("examples/world_default.txt");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        Squares squares = new Squares(bufferedReader);
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//        leftPanel.setLayout(new GridBagLayout());
//        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.X_AXIS));
        leftPanel.setLayout(new GridLayout(30,60));
        String line = "";
        String delimeter = ",";
//        leftPanel.add(new Squares(0,0,1,1,"0"));
//        leftPanel.add(new Squares(0,1,1,1,"110"));
//        leftPanel.add(new Squares(0,2,1,1,"222"));
//        leftPanel.add(new Squares(0,3,1,1,"222"));
//        leftPanel.add(new Squares(1,2,1,1,"222"));
//        leftPanel.add(new Squares( 0,0,13,13,"0",gridBagConstraints),gridBagConstraints);
//        leftPanel.add(new Squares(122,122,16,16,"0",gridBagConstraints),gridBagConstraints);
//        leftPanel.add(new Squares(2,1,16,16,"0",gridBagConstraints),gridBagConstraints);
//        leftPanel.add(new Squares(2,2,1,1,"0",gridBagConstraints),gridBagConstraints);
        ArrayList<JPanel> components = new ArrayList<JPanel>();
        int countLines = 0;
        int[] rgbValues;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] height = line.split(delimeter);
                for (int i = 0; i < 60; i++) {
//                    Squares squares = new Squares(countLines,i,16,16,height[i]);
//                    leftPanel.add(squares);
//                    add(squares);
                    JPanel temp = new JPanel();
                    rgbValues = checkColour(height[i]);
                    temp.setBackground(new Color(rgbValues[0],rgbValues[1],rgbValues[2]));
                    temp.setSize(16,16);
                    components.add(temp);
                    leftPanel.add(temp);
                }
                countLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        leftPanel.add(
//                leftPanel.add(squares);

//        leftPanel.setLayout(n);
//        leftPanel = squares;
    }
    public int[] checkColour(String colour) {
        int colourValue = Integer.parseInt(colour);
        int[] returnRGB = {0, 0, 0};
        if (colourValue == 0) {
            returnRGB = new int[]{0, 0, 255};
        } else if (colourValue <= 200 && colourValue > 0) {
            returnRGB = new int[]{60, 179, 113};
        } else if (colourValue <= 400 && colourValue > 200) {
            returnRGB = new int[]{46, 139, 87};
        } else if (colourValue <= 700 && colourValue > 400) {
            returnRGB = new int[]{34, 139, 34};
        } else if (colourValue <= 1500 && colourValue > 700) {
            returnRGB = new int[]{222, 184, 135};
        } else if (colourValue <= 3500 && colourValue > 1500) {
            returnRGB = new int[]{205, 133, 63};
        } else if (colourValue > 3500) {
            returnRGB = new int[]{145, 80, 20};
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

