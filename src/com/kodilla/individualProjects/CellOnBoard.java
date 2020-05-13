package com.kodilla.individualProjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

class CellOnBoard extends Rectangle {
    Images img = new Images();
    Color strokeDefault = Color.rgb(57, 84, 135);

    private final Board board;
    private final int x;
    private final int y;
    private boolean shot;

    private Ship ship;
    private final Marker marker;

    public CellOnBoard(int x, int y, Board board) {
        super(50, 50);
        this.x = x;
        this.y = y;
        this.board = board;

        marker = new Marker(board);

        setFill(new ImagePattern(img.defaultWater));
        setStroke(strokeDefault);
        setStrokeWidth(1);
    }

    public CellOnBoard shootCell() {
        shot = true;
        setFill(new ImagePattern(img.shotCellWithNoShip));

        return this;
    }

    public boolean shootShip() {
        if (ship != null) {
            ship.hit();
            setFill(new ImagePattern(img.shipOnFire));

            if (!ship.isAlive()) {
                board.getOffOneShip();
                ship.markDestroyed(board);
            }

            for (CellOnBoard cellOnBoard : board.getDiagonalNeighboringCells(x, y)) {
                cellOnBoard.markMissed();
            }

            marker.markSurroundingsOfSunkenShips();

            return true;
        }
        return false;
    }

    public void markMissed() {
        shot = true;
        setFill(new ImagePattern(img.shotCellWithNoShip));
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getXvar() {
        return x;
    }

    public int getYvar() {
        return y;
    }
}