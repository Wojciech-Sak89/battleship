package com.kodilla.individualProjects;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ship extends Parent {
    Images img = new Images();

    public int size;
    public boolean isVertical;
    public List<Coordinates> coordinates = new ArrayList<>();

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

    public static List<Integer> getRussianShips() {
        ArrayList<Integer> russianShips = new ArrayList<>(10);
        russianShips.add(4);
        russianShips.add(3);
        russianShips.add(3);
        russianShips.add(2);
        russianShips.add(2);
        russianShips.add(2);
        russianShips.add(1);
        russianShips.add(1);
        russianShips.add(1);
        russianShips.add(1);

        Collections.reverse(russianShips);

        return russianShips;
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
        Cell cell = board.getCellOnBoard(coordinates.get(0).getX(), coordinates.get(0).getY());
        cell.setFill(new ImagePattern(destroyedPatrolShip));
    }

    private void destroyShipGreaterThanPatrol(Board board, Image shipDestroyedBow,
                                              Image shipDestroyedCenter,
                                              Image shipDestroyedStern) {
        for (int i = 0; i < size; i++) {
            Cell cell = board.getCellOnBoard(coordinates.get(i).getX(), coordinates.get(i).getY());
            if (i == 0)
                cell.setFill(new ImagePattern(shipDestroyedBow));
            if (i > 0 && i < size - 1)
                cell.setFill(new ImagePattern(shipDestroyedCenter));
            if (i == size - 1)
                cell.setFill(new ImagePattern(shipDestroyedStern));
        }
    }


}