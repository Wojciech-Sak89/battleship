package com.kodilla.individualProjects;

import com.kodilla.individualProjects.enums.ShipType;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ship extends Parent {
    Images img = new Images();

    private final int size;
    private final boolean isVertical;
    private final List<Coordinates> coordinates = new ArrayList<>();

    private int health;

    public Ship(int size, boolean isVertical) {
        this.size = size;
        this.isVertical = isVertical;
        this.health = size;
    }

    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public static List<Integer> getShips(boolean classic) {
       if (classic) {
           return getClassicShips();
       } else {
           return getRussianShips();
       }
    }

    private static List<Integer> getRussianShips() {
        ArrayList<Integer> russianShips = new ArrayList<>(10);
        russianShips.add(ShipType.BATTLESHIP.size);
        russianShips.add(ShipType.CRUISER.size);
        russianShips.add(ShipType.CRUISER.size);
        russianShips.add(ShipType.DESTROYER.size);
        russianShips.add(ShipType.DESTROYER.size);
        russianShips.add(ShipType.DESTROYER.size);
        russianShips.add(ShipType.PATROL.size);
        russianShips.add(ShipType.PATROL.size);
        russianShips.add(ShipType.PATROL.size);
        russianShips.add(ShipType.PATROL.size);

        Collections.reverse(russianShips);

        return russianShips;
    }

    private static List<Integer> getClassicShips() {
        ArrayList<Integer> classicShips = new ArrayList<>(5);
        classicShips.add(ShipType.AIRCRAFT_CARRIER.size);
        classicShips.add(ShipType.BATTLESHIP.size);
        classicShips.add(ShipType.CRUISER.size);
        classicShips.add(ShipType.DESTROYER.size);
        classicShips.add(ShipType.PATROL.size);

        Collections.reverse(classicShips);

        return classicShips;
    }

    public void markDestroyed(Board board) {
        if (isVertical && size == ShipType.PATROL.size) {
            destroyPatrol(board, img.patrolShipDestroyedVertical);

        } else if (!isVertical && size == ShipType.PATROL.size) {
            destroyPatrol(board, img.patrolShipDestroyedHorizontal);

        } else if (isVertical && size > ShipType.PATROL.size) {
            destroyShipGreaterThanPatrol(board, img.shipDestroyedVerticalBow,
                                                    img.shipDestroyedVerticalCenter,
                                                    img.shipDestroyedVerticalStern);

        } else if (!isVertical && size > ShipType.PATROL.size) {
            destroyShipGreaterThanPatrol(board, img.shipDestroyedHorizontalStern,
                                                    img.shipDestroyedHorizontalCenter,
                                                    img.shipDestroyedHorizontalBow);
        }
    }

    private void destroyPatrol(Board board, Image destroyedPatrolShip) {
        CellOnBoard cellOnBoard = board.getCellOnBoard(coordinates.get(0).getX(), coordinates.get(0).getY());
        cellOnBoard.setFill(new ImagePattern(destroyedPatrolShip));
    }

    private void destroyShipGreaterThanPatrol(Board board, Image shipDestroyedBow,
                                                           Image shipDestroyedCenter,
                                                           Image shipDestroyedStern) {
        for (int i = 0; i < size; i++) {
            CellOnBoard cellOnBoard = board.getCellOnBoard(coordinates.get(i).getX(), coordinates.get(i).getY());
            if (i == 0)
                cellOnBoard.setFill(new ImagePattern(shipDestroyedBow));
            if (i > 0 && i < size - 1)
                cellOnBoard.setFill(new ImagePattern(shipDestroyedCenter));
            if (i == size - 1)
                cellOnBoard.setFill(new ImagePattern(shipDestroyedStern));
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isVertical() {
        return isVertical;
    }
}