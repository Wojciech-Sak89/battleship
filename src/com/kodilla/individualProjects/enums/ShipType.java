package com.kodilla.individualProjects.enums;

public enum ShipType {
    PATROL(1), DESTROYER(2), CRUISER(3), BATTLESHIP(4), AIRCRAFT_CARRIER(5);

    public int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}