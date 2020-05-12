package com.kodilla.individualProjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

class CellOnBoard extends Rectangle {
    Images img = new Images();
    Color strokeDefault = Color.rgb(57, 84, 135);

    private final Board board;
    public int x, y;
    public boolean wasAlreadyShot;

    public Ship ship;
    public Marker marker;

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
        wasAlreadyShot = true;
        setFill(new ImagePattern(img.shotCellWithNoShip));

        return this;
    }

    public boolean shootShip() {
        if (ship != null) {
            ship.hit();
            setFill(new ImagePattern(img.shipOnFire));

            if (!ship.isAlive()) {
                board.ships--;
                System.out.println("Ships left: " + board.ships + "on " +
                        (board.isEnemyBoard ? "enemy" : "player") + " board");
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
        wasAlreadyShot = true;
        setFill(new ImagePattern(img.shotCellWithNoShip));
    }

}