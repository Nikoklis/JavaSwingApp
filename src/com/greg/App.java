package com.greg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        setSize(800, 400);
        setLayout(new BorderLayout());

        //center the window on the screen
        setLocationRelativeTo(null);

        //closes the window when you press the Close button of the title bar
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPopupMenu jPopupMenu = new JPopupMenu();


        createMenuBar(jPopupMenu);


//
//      JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JPanel jPanel = new JPanel();
        jPanel.setSize(getWidth()-300,getHeight());
        jPanel.setBackground(Color.blue);

        jPanel.setLayout(new GridBagLayout());

//        jPanel.setVisible(true);
//        this.add(jPanel);

        JPanel controls = new JPanel();
        controls.setSize(getWidth()-150, getHeight());
        controls.setBackground(Color.RED);
//        controls.setVisible(true);
//        this.add(controls);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jPanel,controls);
//        jSplitPane.setSize(getWidth(),getHeight());
        jSplitPane.setDividerSize(10);
        jSplitPane.setResizeWeight(0.90);

//        jSplitPane.setDividerLocation(getWidth()-300);
//        jSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        JPanel topControls = new JPanel();
//        topControls.setSize(10,10);
        topControls.setBackground(Color.orange);
//        topControls.setVisible(true);
//        this.add(topControls);


        JSplitPane topPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jSplitPane,topControls);
//        topPanel.setSize(10,10);
//        topPanel.setDividerSize(10);
        topPanel.setDividerLocation(10-getHeight());
        topPanel.setResizeWeight(0.03);
//        topPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
//        jSplitPane.setRightComponent(jPanel);
//        jSplitPane.setLeftComponent(controls);

//        this.add(jSplitPane);
        topPanel.setTopComponent(topControls);
        topPanel.setBottomComponent(jSplitPane);
        this.add(topPanel);
//        this.add(jSplitPane);



    }

    private void createMenuBar(JPopupMenu jPopupMenu) {
        JMenuBar menuBar = new JMenuBar();

        JMenu game = new JMenu("Game");
        JMenu simulation = new JMenu("Simulation");


        createMenuItem(game, "Start",null);
        createMenuItem(game, "Stop",null);
        createMenuItem(game, "Load",null);
        createMenuItem(game, "Exit",null);

        createMenuItem(simulation, "Airports",jPopupMenu);
        createMenuItem(simulation, "Aircrafts",null);
        createMenuItem(simulation, "Flights",null);


        menuBar.add(game);
        menuBar.add(simulation);


        setJMenuBar(menuBar);
    }

    private void createMenuItem(JMenu jMenu, String infoText,JPopupMenu jPopupMenu) {
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
                    if (mouseEvent.isPopupTrigger())
                    {
                        jPopupMenu.show(mouseEvent.getComponent(),mouseEvent.getX(),mouseEvent.getY());
                    }
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    if (mouseEvent.isPopupTrigger())
                    {
                        jPopupMenu.show(mouseEvent.getComponent(),mouseEvent.getX(),mouseEvent.getY());
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
        if (event.isPopupTrigger())
        {

        }
    }


}

