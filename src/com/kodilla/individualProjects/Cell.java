package com.kodilla.individualProjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

class Cell extends Rectangle {
    Images img = new Images();
    Color strokeDefault = Color.rgb(57, 84, 135);

    private final Board board;
    public int x, y;
    public boolean wasAlreadyShot = false;

    public Ship ship = null;
    public Marker marker;

    public Cell(int x, int y, Board board) {
        super(50, 50);
        this.x = x;
        this.y = y;
        this.board = board;
        marker = new Marker(board);
        setFill(new ImagePattern(img.defaultWater));
        setStroke(strokeDefault);
        setStrokeWidth(1);
    }

    public boolean hittingOpponentsCell() {
        wasAlreadyShot = true;
        setFill(new ImagePattern(img.shotCellWithNoShip));

        if (ship != null) {
            ship.hit();
            setFill(new ImagePattern(img.shipOnFire));
            if (!ship.isAlive()) {
                board.ships--;
                ship.markDestroyed(board);
            }

            for (Cell cell : board.getDiagonalNeighboringCells(x, y)) {
                cell.markDefaultMissed();
            }

            marker.shootSurroundingsOfSunkenShips();

            return true;
        }

        return false;
    }

    public void markDefaultMissed() {
        wasAlreadyShot = true;
        setFill(new ImagePattern(img.shotCellWithNoShip));
    }




}