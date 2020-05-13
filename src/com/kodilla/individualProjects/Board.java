package com.kodilla.individualProjects;

import java.util.ArrayList;
import java.util.List;

import com.kodilla.individualProjects.enums.ShipType;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Board extends Parent {
    private final Images img = new Images();
    private final Text text = new Text();
    private final VBox rows = new VBox();

    private final boolean isEnemyBoard;
    private final ArrayList<ArrayList<Coordinates>> wholeShipsCoordinates = new ArrayList<>();

    private int ships;

    public Board(boolean isEnemyBoard, EventHandler<? super MouseEvent> handler) {
        this.isEnemyBoard = isEnemyBoard;
        makeNewBoard(handler);
    }

    public void makeNewBoard(EventHandler<? super MouseEvent> handler) {
        rows.getChildren().removeAll(rows.getChildren());

        captionBoard();

        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                CellOnBoard cellOnBoard = new CellOnBoard(x, y, this);
                cellOnBoard.setOnMouseClicked(handler);

                row.getChildren().add(cellOnBoard);
            }

            rows.getChildren().add(row);
        }

        rows.getChildren().add(text);
        getChildren().add(rows);
    }

    private void captionBoard() {
        String enemyBoardText =  "                     Enemy";
        String playerBoardText = "                     Player";
        Color enemyColor = Color.rgb(240,245,255);
        Color playerColor = Color.rgb(112,96,57);

        text.setText(isEnemyBoard ? enemyBoardText : playerBoardText);
        text.setFont(Font.loadFont("file:resources/Fonts/IsadoraCyrPro.ttf", 35));
        text.setFill(isEnemyBoard ? enemyColor : playerColor);
        text.setStroke(isEnemyBoard ? enemyColor : playerColor);
        text.setStrokeWidth(1);
    }

    public boolean placeShip(Ship ship, int x, int y) {
        ArrayList<Coordinates> partsOfTheShipCoordinates = new ArrayList<>();

        if (canPlaceShip(ship, x, y)) {
            int shipSize = ship.getSize();
            int tempX = x, tempY = y;

            if (!ship.isVertical()) {
                x = tempY;
                y = tempX;
            }

            for (int i = y; i < y + shipSize; i++) {
                CellOnBoard cellOnBoard = ship.isVertical() ? getCellOnBoard(x, i) : getCellOnBoard(i, x);
                cellOnBoard.setShip(ship);

                int currX = ship.isVertical() ? x : i;
                int currY = ship.isVertical() ? i : x;

                partsOfTheShipCoordinates.add(new Coordinates(currX, currY, ship.isVertical()));
                ship.getCoordinates().add(new Coordinates(currX, currY, ship.isVertical()));

                if (!isEnemyBoard) {
                    setShipImages(ship, y, shipSize, i, cellOnBoard);
                }
            }

            wholeShipsCoordinates.add(partsOfTheShipCoordinates);

            return true;
        }

        return false;
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.getSize();

        if (ship.isVertical()) {
            return validateIfVerticalCellsAreFree(x, y, length);
        } else {
            return validateIfHorizontalCellsAreFree(x, y, length);
        }

    }

    private boolean validateIfHorizontalCellsAreFree(int x, int y, int length) {
        for (int i = x; i < x + length; i++) {
            if (!pointIsInGridRange(i, y))
                return false;

            CellOnBoard cellOnBoard = getCellOnBoard(i, y);
            if (cellOnBoard.getShip() != null)
                return false;

            for (CellOnBoard neighbor : getAllNeighboringCells(i, y)) {
                if (!pointIsInGridRange(i, y))
                    return false;

                if (neighbor.getShip() != null)
                    return false;
            }
        }
        return true;
    }

    private boolean validateIfVerticalCellsAreFree(int x, int y, int length) {
        for (int i = y; i < y + length; i++) {
            if (!pointIsInGridRange(x, i))
                return false;

            CellOnBoard cellOnBoard = getCellOnBoard(x, i);
            if (cellOnBoard.getShip() != null)
                return false;

            for (CellOnBoard neighbor : getAllNeighboringCells(x, i)) {
                if (neighbor.getShip() != null)
                    return false;
            }
        }
        return true;
    }

    public CellOnBoard[] getAllNeighboringCells(int x, int y) {
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

    public CellOnBoard[] getDiagonalNeighboringCells(int x, int y) {
        Point2D[] points = new Point2D[]{
                new Point2D(x + 1, y - 1),
                new Point2D(x + 1, y + 1),
                new Point2D(x - 1, y + 1),
                new Point2D(x - 1, y - 1)
        };

        return getCells(points);
    }

    private CellOnBoard[] getCells(Point2D[] points) {
        List<CellOnBoard> neighbors = new ArrayList<>();

        for (Point2D p : points) {
            if (pointIsInGridRange(p)) {
                neighbors.add(getCellOnBoard((int) p.getX(), (int) p.getY()));
            }
        }

        return neighbors.toArray(new CellOnBoard[0]);
    }

    public CellOnBoard getCellOnBoard(int x, int y) {
        return (CellOnBoard) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
    }

    public boolean pointIsInGridRange(Point2D point) {
        return pointIsInGridRange(point.getX(), point.getY());
    }

    private boolean pointIsInGridRange(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    private void setShipImages(Ship ship, int y, int shipSize, int i, CellOnBoard cellOnBoard) {
        if (ship.isVertical() && shipSize == ShipType.PATROL.size) {
            cellOnBoard.setFill(new ImagePattern(img.patrolShipVertical));

        } else if (!ship.isVertical() && shipSize == ShipType.PATROL.size) {
            cellOnBoard.setFill(new ImagePattern(img.patrolShipHorizontal));

        } else if (ship.isVertical() && shipSize > ShipType.PATROL.size) {
            setImageOfShipFragment(y, shipSize, i, cellOnBoard, img.shipDefaultVerticalBow,
                    img.shipDefaultVerticalCenter,
                    img.shipDefaultVerticalStern);

        } else if (!ship.isVertical() && shipSize > ShipType.PATROL.size) {
            setImageOfShipFragment(y, shipSize, i, cellOnBoard, img.shipDefaultHorizontalStern,
                    img.shipDefaultHorizontalCenter,
                    img.shipDefaultHorizontalBow);
        }
    }

    private void setImageOfShipFragment(int y, int shipSize, int i, CellOnBoard cellOnBoard, Image shipDefaultBow,
                                        Image shipDefaultCenter,
                                        Image shipDefaultStern) {
        if (i - y == 0)
            cellOnBoard.setFill(new ImagePattern(shipDefaultBow));
        if (i - y > 0 && i < y + shipSize - 1)
            cellOnBoard.setFill(new ImagePattern(shipDefaultCenter));
        if (i == y + shipSize - 1)
            cellOnBoard.setFill(new ImagePattern(shipDefaultStern));
    }

    public boolean isEnemyBoard() {
        return isEnemyBoard;
    }

    public void getOffOneShip() {
        ships--;
    }

    public int getShips() {
        return ships;
    }

    public void setShips(int ships) {
        this.ships = ships;
    }

    public ArrayList<ArrayList<Coordinates>> getWholeShipsCoordinates() {
        return wholeShipsCoordinates;
    }


}