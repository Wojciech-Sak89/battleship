package com.kodilla.individualProjects;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;

public class Board extends Parent {
    Images img = new Images();

    private final VBox rows = new VBox();

    public boolean isEnemyBoard;
    public int ships = 10;
    public ArrayList<ArrayList<Coordinates>> wholeShipsCoordinates = new ArrayList<>();

    public Board(boolean isEnemyBoard, EventHandler<? super MouseEvent> handler) {
        this.isEnemyBoard = isEnemyBoard;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell cell = new Cell(x, y, this);
                cell.setOnMouseClicked(handler);

                row.getChildren().add(cell);
            }

            rows.getChildren().add(row);
        }

        getChildren().add(rows);
    }

    // refactor
    public boolean placeShip(Ship ship, int x, int y) {
        ArrayList<Coordinates> partsOfTheShipCoordinates = new ArrayList<>();

        if (canPlaceShip(ship, x, y)) {
            int shipSize = ship.size;
            int tempX = x, tempY = y;

            if (!ship.isVertical) {
                x = tempY;
                y = tempX;
            }

            for (int i = y; i < y + shipSize; i++) {
                Cell cell = ship.isVertical ? getCellOnBoard(x, i) : getCellOnBoard(i, x);
                cell.ship = ship;

                int currX = ship.isVertical ? x : i;
                int currY = ship.isVertical ? i : x;

                partsOfTheShipCoordinates.add(new Coordinates(currX, currY, ship.isVertical));
                ship.getCoordinates().add(new Coordinates(currX, currY, ship.isVertical));

                if (!isEnemyBoard) {
                    setShipImages(ship, y, shipSize, i, cell);
                }
            }

            wholeShipsCoordinates.add(partsOfTheShipCoordinates);

            return true;
        }

        return false;
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.size;

        if (ship.isVertical) {
            for (int i = y; i < y + length; i++) {
                if (!pointIsInGridRange(x, i))
                    return false;

                Cell cell = getCellOnBoard(x, i);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getAllNeighboringCells(x, i)) {
                    if (neighbor.ship != null)
                        return false;
                }
            }
        } else {
            for (int i = x; i < x + length; i++) {
                if (!pointIsInGridRange(i, y))
                    return false;

                Cell cell = getCellOnBoard(i, y);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getAllNeighboringCells(i, y)) {
                    if (!pointIsInGridRange(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }

        return true;
    }

    public Cell[] getAllNeighboringCells(int x, int y) {
        Point2D[] points = new Point2D[]{
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1),
                new Point2D(x + 1, y - 1),
                new Point2D(x + 1, y + 1),
                new Point2D(x - 1, y + 1),
                new Point2D(x - 1, y - 1)
        };

        return getCells(points);
    }

    public Cell[] getDiagonalNeighboringCells(int x, int y) {
        Point2D[] points = new Point2D[]{
                new Point2D(x + 1, y - 1),
                new Point2D(x + 1, y + 1),
                new Point2D(x - 1, y + 1),
                new Point2D(x - 1, y - 1)
        };

        return getCells(points);
    }

    private Cell[] getCells(Point2D[] points) {
        List<Cell> neighbors = new ArrayList<>();

        for (Point2D p : points) {
            if (pointIsInGridRange(p)) {
                neighbors.add(getCellOnBoard((int) p.getX(), (int) p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

    public Cell getCellOnBoard(int x, int y) {
        return (Cell) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
    }

    public boolean pointIsInGridRange(Point2D point) {
        return pointIsInGridRange(point.getX(), point.getY());
    }

    private boolean pointIsInGridRange(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    private void setShipImages(Ship ship, int y, int shipSize, int i, Cell cell) {
        if (ship.isVertical && shipSize == ShipType.PATROL.size) {
            cell.setFill(new ImagePattern(img.patrolShipVertical));

        } else if (!ship.isVertical && shipSize == ShipType.PATROL.size) {
            cell.setFill(new ImagePattern(img.patrolShipHorizontal));

        } else if (ship.isVertical && shipSize > ShipType.PATROL.size) {
            setImageOfShipFragment(y, shipSize, i, cell, img.shipDefaultVerticalBow,
                                                         img.shipDefaultVerticalCenter,
                                                         img.shipDefaultVerticalStern);

        } else if (!ship.isVertical && shipSize > ShipType.PATROL.size) {
            setImageOfShipFragment(y, shipSize, i, cell, img.shipDefaultHorizontalStern,
                                                         img.shipDefaultHorizontalCenter,
                                                         img.shipDefaultHorizontalBow);
        }
    }

    private void setImageOfShipFragment(int y, int shipSize, int i, Cell cell, Image shipDefaultBow,
                                                                               Image shipDefaultCenter,
                                                                               Image shipDefaultStern) {
        if (i - y == 0)
            cell.setFill(new ImagePattern(shipDefaultBow));
        if (i - y > 0 && i < y + shipSize - 1)
            cell.setFill(new ImagePattern(shipDefaultCenter));
        if (i == y + shipSize - 1)
            cell.setFill(new ImagePattern(shipDefaultStern));
    }


}