package com.kodilla.individualProjects;

public enum ShipOrientation {

    VERTICAL(true), HORIZONTAL(false);

    boolean isVertical;

    private ShipOrientation(boolean isVertical) {
        this.isVertical = isVertical;
    }

    public boolean isVertical() {
        return isVertical;
    }
}

