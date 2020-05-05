package com.kodilla.individualProjects;

public enum ShipType {
    PATROL(1), DESTROYER(2), CRUISER(3), BATTLESHIP(4);

    int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}