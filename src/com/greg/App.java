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
        JPopupMenu jPopupMenu = new JPopupMenu();

        createMenuBar(jPopupMenu);


        setTitle(AppName);
        setSize(800, 400);

        //center the window on the screen
        setLocationRelativeTo(null);

        //closes the window when you press the Close button of the title bar
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

