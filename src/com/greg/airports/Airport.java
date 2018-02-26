package com.greg.airports;

public class Airport {
    private String name;
    private int[] coordinates = {0,0};
    private int id;
    private int type;
    private int orientation;
    private boolean state;

    public Airport(String name,int x,int y,int id , int type,int orientation,boolean state){
        coordinates[0] = x;
        coordinates[1] = y;
        this.id = id;
        this.name = name;
        this.type = type;
        this.orientation = orientation;
        this.state = state;
    }
}
