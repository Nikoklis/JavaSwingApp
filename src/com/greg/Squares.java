package com.greg;

import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

public class Squares extends JPanel {
    private final int ROWS = 30;
    private final int COLUMNS = 60;
    private final int RectSizeX = 16;
    private final int RectSizeY = 16;

    private Rectangle square = null;
//    private final int PREF_W = 160;
//    private final int PREF_H = 160;

    private int[] rgbValues = new int[3];


    public Squares(int x, int y, int width, int height, String colour, GridBagConstraints gridBagConstraints) {
        rgbValues = checkColour(colour);
        square = new Rectangle(x, y, width, height);
        System.out.println("X is : " + x + " Y is : " + y + " width is : " +width + " height is : " + height);

        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.gridwidth = width;
        gridBagConstraints.gridheight = height;

        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(0,0,0,0);
    }

    public Squares(int x , int y, int width,int height , String colour)
    {
        rgbValues = checkColour(colour);
        square = new Rectangle(x, y, width, height);
    }
//    public void addSquare(int x, int y, int width, int height) {
//        Rectangle rectangle = new Rectangle(x, y, width, height);
//        square = rectangle;
////        System.out.println("X is : " + squares[x][y].getX() + " and Y is : " + squares[x][y].getY());
////        System.out.println("width is : " + width + " height is : " + height);
//    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(new Color(rgbValues[0],rgbValues[1],rgbValues[2]));
        g2.fillRect((int)square.getX(),(int)square.getY(),RectSizeX,RectSizeY);
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(PREF_W, PREF_H); // appropriate constants
//    }

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
            returnRGB = new int[]{34, 139, 135};
        } else if (colourValue <= 1500 && colourValue > 700) {
            returnRGB = new int[]{222, 184, 135};
        } else if (colourValue <= 3500 && colourValue > 1500) {
            returnRGB = new int[]{205, 133, 63};
        } else if (colourValue > 3500) {
            returnRGB = new int[]{145, 80, 20};
        }

        return returnRGB;
    }


}
