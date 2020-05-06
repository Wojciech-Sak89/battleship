package com.kodilla.individualProjects.enums;

public enum ShipSituation {

    VERTICAL(true), HORIZONTAL(false);

    public boolean orientation;

    ShipSituation(boolean isVertical) {
        this.orientation = isVertical;
    }

    public boolean orientation() {
        return orientation;
    }
}

