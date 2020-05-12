package com.kodilla.individualProjects;

import com.kodilla.individualProjects.enums.ShipType;
import javafx.geometry.Point2D;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class Marker {
    Board board; // zmienić na private!!!
    Images img = new Images();

    public Marker(Board board) {
        this.board = board;
    }

    public void markSurroundingsOfSunkenShips() {
        ArrayList<ArrayList<Coordinates>> wholeShipsCoordinates = board.wholeShipsCoordinates;

        for (ArrayList<Coordinates> shipCoordinates : wholeShipsCoordinates) {
            int shotShipFragmentsNum = 0;

            shotShipFragmentsNum = getShotShipFragmentsNum(shipCoordinates, shotShipFragmentsNum);

            markSurroundingsOfOneSunkenShip(shipCoordinates, shotShipFragmentsNum);
        }
    }

    private int getShotShipFragmentsNum(ArrayList<Coordinates> shipCoordinates, int shotShipFragmentsNum) {
        for (Coordinates shipFragmentCoordinates : shipCoordinates) {
            CellOnBoard cellOnBoard = board.getCellOnBoard(shipFragmentCoordinates.getX(), shipFragmentCoordinates.getY());

            if (cellOnBoard.wasAlreadyShot && cellOnBoard.ship != null) {
                shotShipFragmentsNum++;
            }
        }
        return shotShipFragmentsNum;
    }

    private void markSurroundingsOfOneSunkenShip(ArrayList<Coordinates> shipCoordinates, int shotShipFragmentsNum) {
        if (shotShipFragmentsNum == shipCoordinates.size()) {

            if (shotShipFragmentsNum == ShipType.PATROL.size) {
                markSurroundingsOfPatrolShip(shipCoordinates);

            } else {
                markSurroundingsOfShipGreaterThanPatrol(shipCoordinates);

            }
        }
    }

    private void markSurroundingsOfPatrolShip(ArrayList<Coordinates> shipCoordinates) {
        for (CellOnBoard cellOnBoard : board.getAllNeighboringCells(shipCoordinates.get(0).getX(),
                shipCoordinates.get(0).getY())) {
            cellOnBoard.markMissed();
        }
    }

    private void markSurroundingsOfShipGreaterThanPatrol(ArrayList<Coordinates> shipCoordinates) {
        Coordinates bow = shipCoordinates.get(0);
        Coordinates stern = shipCoordinates.get(shipCoordinates.size() - 1);

        int adjustmentX = bow.vertical ? 0 : 1;
        int adjustmentY = stern.vertical ? 1 : 0;

        Point2D pointBeforeSunkenShipBow =
                new Point2D(bow.getX() - adjustmentX, bow.getY() - adjustmentY);
        Point2D pointAfterSunkenShipStern =
                new Point2D(stern.getX() + adjustmentX, stern.getY() + adjustmentY);

        if (board.pointIsInGridRange(pointBeforeSunkenShipBow)) {
            markPointAfterShipsVerge(pointBeforeSunkenShipBow);
        }

        if (board.pointIsInGridRange(pointAfterSunkenShipStern)) {
            markPointAfterShipsVerge(pointAfterSunkenShipStern);
        }
    }

    private void markPointAfterShipsVerge(Point2D pointAfterVerge) {
        CellOnBoard afterShipVerge = board.getCellOnBoard((int) pointAfterVerge.getX(),
                (int) pointAfterVerge.getY());
        afterShipVerge.markMissed();
    }


    public void makeNewBoard() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                board.getCellOnBoard(x, y).ship = null;
                board.getCellOnBoard(x, y).wasAlreadyShot = false;
                board.getCellOnBoard(x, y).setFill(new ImagePattern(img.defaultWater));
            }
        }

        board.wholeShipsCoordinates.clear();
    }


    private void takeOffShipFragment(ArrayList<Coordinates> shipCoordinates) {
        for (Coordinates shipFragment : shipCoordinates) {
            board.getCellOnBoard(shipFragment.getX(), shipFragment.getY()).ship = null;
            makeCellUnshot(shipFragment);
        }
    }

    private void makeCellUnshot(Coordinates shipFragment) {
        board.getCellOnBoard(shipFragment.getX(), shipFragment.getY()).wasAlreadyShot = false;
        board.getCellOnBoard(shipFragment.getX(), shipFragment.getY()).setFill(new ImagePattern(img.defaultWater));
    }

}
