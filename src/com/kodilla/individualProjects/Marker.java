package com.kodilla.individualProjects;

import com.kodilla.individualProjects.enums.ShipType;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Marker {
    Board board;

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
            Cell cell = board.getCellOnBoard(shipFragmentCoordinates.getX(), shipFragmentCoordinates.getY());

            if (cell.wasAlreadyShot && cell.ship != null) {
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
        for (Cell cell : board.getAllNeighboringCells(shipCoordinates.get(0).getX(),
                shipCoordinates.get(0).getY())) {
            cell.markMissed();
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
        Cell afterShipVerge = board.getCellOnBoard((int) pointAfterVerge.getX(),
                (int) pointAfterVerge.getY());
        afterShipVerge.markMissed();
    }

}
