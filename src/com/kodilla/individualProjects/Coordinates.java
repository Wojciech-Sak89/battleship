package com.kodilla.individualProjects;

public class Coordinates {
    private final int x;
    private final int y;
    private final boolean vertical;

    public Coordinates(int x, int y, boolean vertical) {
        this.x = x;
        this.y = y;
        this.vertical = vertical;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVertical() {
        return vertical;
    }
}
