package com.kodilla.individualProjects;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Cell extends Rectangle {
    Image defaultWater = new Image("file:resources/Tiles/smalldefaultWater-tile.png");
    Image shotCellWithNoShip = new Image("file:resources/Tiles/smallMissed-tile.png");
    Image shipOnFire = new Image("file:resources/Tiles/explosion.png");

    Color strokeDefault = Color.rgb(57, 84, 135);

    private final Board board;
    public int x, y;
    public boolean wasAlreadyShot = false;

    public Ship ship = null;

    public Cell(int x, int y, Board board) {
        super(50, 50); // size of cell, inherited from rectangle
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(new ImagePattern(defaultWater));
        setStroke(strokeDefault);
        setStrokeWidth(1);
    }

    public boolean hittingOpponentsCell() {
        wasAlreadyShot = true;
        setFill(new ImagePattern(shotCellWithNoShip));

        if (ship != null) {
            ship.hit();
            setFill(new ImagePattern(shipOnFire));
            if (!ship.isAlive()) {
                board.ships--;
                ship.markDestroyed(board);
            }

            for (Cell cell : board.getDiagonalNeighboringCells(x, y)) {
                cell.markDefaultMissed();
            }

            shootSurroundingsOfSunkenShips();

            return true;
        }

        return false;
    }

    // change to stream with flatmap()? make more methods, refactor - simplify using list ship.coordinates, koniecznie stream!!!
    public void shootSurroundingsOfSunkenShips() {
        ArrayList<ArrayList<Coordinates>> wholeShipsCoordinates = board.wholeShipsCoordinates;

        for (ArrayList<Coordinates> shipCoordinates : wholeShipsCoordinates) {
            int shotShipFragmentsNum = 0;

            for (Coordinates shipFragmentCoordinates : shipCoordinates) {
                Cell cell = board.getCellOnBoard(shipFragmentCoordinates.getX(), shipFragmentCoordinates.getY());

                if (cell.wasAlreadyShot && cell.ship != null) {
                    shotShipFragmentsNum++;
                }
            }

            if (shotShipFragmentsNum == shipCoordinates.size()) {

                if (shotShipFragmentsNum == 1) { // osobna metoda markifOne()
                    for (Cell cell : board.getAllNeighboringCells(shipCoordinates.get(0).getX(),
                                                                  shipCoordinates.get(0).getY())) {
                        cell.markDefaultMissed();
                    }

                } else {
                    Coordinates bow = shipCoordinates.get(0);
                    Coordinates stern = shipCoordinates.get(shipCoordinates.size() - 1);

                    int adjustmentX = bow.vertical ? 0 : 1;
                    int adjustmentY = stern.vertical ? 1 : 0;

                    Point2D pointBeforeSunkenShipBow =
                            new Point2D(bow.getX() - adjustmentX, bow.getY() - adjustmentY);
                    Point2D pointAfterSunkenShipStern =
                            new Point2D(stern.getX() + adjustmentX, stern.getY() + adjustmentY);

                    if (board.pointIsInGridRange(pointBeforeSunkenShipBow)) {
                        Cell beforeSunkenShipBow = board.getCellOnBoard((int) pointBeforeSunkenShipBow.getX(),
                                                                        (int) pointBeforeSunkenShipBow.getY());
                        beforeSunkenShipBow.markDefaultMissed();
                    }

                    if (board.pointIsInGridRange(pointAfterSunkenShipStern)) {
                        Cell afterSunkenShipStern = board.getCellOnBoard((int) pointAfterSunkenShipStern.getX(),
                                                                         (int) pointAfterSunkenShipStern.getY());
                        afterSunkenShipStern.markDefaultMissed();
                    }

                }

            }

        }

    }

    public void markDefaultMissed() {
        wasAlreadyShot = true;
        setFill(new ImagePattern(shotCellWithNoShip));
    }
}