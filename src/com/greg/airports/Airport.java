package com.greg.airports;

public class Airport {
    private String name;
    private int[] coordinates = {0, 0};
    private int id;
    private int type;
    private int orientation;
    private boolean state;

    public Airport(String name, int x, int y, int id, int type, int orientation, boolean state) {
        coordinates[0] = x;
        coordinates[1] = y;
        this.id = id;
        this.name = name;
        this.type = type;
        this.orientation = orientation;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        if (state == true)
            return 1;
        else
            return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
