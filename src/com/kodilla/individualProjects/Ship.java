package com.kodilla.individualProjects;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ship extends Parent {

    Image shipSizeOneDestroyedVertical = new Image("file:resources/Battleships-by-size/1/ShipPatrolHull_Destroyed_OnBackground_Vertical.png");
    Image shipSizeOneDestroyedHorizontal = new Image("file:resources/Battleships-by-size/1/ShipPatrolHull_Destroyed_OnBackground_Horizontal.png");

    Image shipDestroyedVerticalBow = new Image("file:resources/Battleships-by-size/Destroyed/Vertical/Ship_Destroyed_Bow_Vertical.png");
    Image shipDestroyedVerticalCenter = new Image("file:resources/Battleships-by-size/Destroyed/Vertical/Ship_Destroyed_Center_Vertical.png");
    Image shipDestroyedVerticalStern = new Image("file:resources/Battleships-by-size/Destroyed/Vertical/Ship_Destroyed_Stern_Vertical.png");

    Image shipDestroyedHorizontalBow = new Image("file:resources/Battleships-by-size/Destroyed/Horizontal/Ship_Destroyed_Bow_Horizontal.png");
    Image shipDestroyedHorizontalCenter = new Image("file:resources/Battleships-by-size/Destroyed/Horizontal/Ship_Destroyed_Center_Horizontal.png");
    Image shipDestroyedHorizontalStern = new Image("file:resources/Battleships-by-size/Destroyed/Horizontal/Ship_Destroyed_Stern_Horizontal.png");


    public int type;
    public boolean isVertical;
    public List<Coordinates> coordinates = new ArrayList<>();

    private int health;


    public Ship(int type, boolean isVertical) {
        this.type = type;
        this.isVertical = isVertical;
        this.health = type;
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

    public static List<Integer> getShipsSizesForRussianGame() {
        ArrayList<Integer> classicShips = new ArrayList<>(10);
        classicShips.add(4);
        classicShips.add(3);
        classicShips.add(3);
        classicShips.add(2);
        classicShips.add(2);
        classicShips.add(2);
        classicShips.add(1);
        classicShips.add(1);
        classicShips.add(1);
        classicShips.add(1);

        Collections.reverse(classicShips);

        return classicShips;
    }

    public void markDestroyed(Board board) {
        if (isVertical && type == 1) {
            Cell cell = board.getCellOnBoard(coordinates.get(0).getX(), coordinates.get(0).getY());
            cell.setFill(new ImagePattern(shipSizeOneDestroyedVertical));

        } else if (!isVertical && type == 1) {
            Cell cell = board.getCellOnBoard(coordinates.get(0).getX(), coordinates.get(0).getY());
            cell.setFill(new ImagePattern(shipSizeOneDestroyedHorizontal));

        } else if (isVertical && type > 1) {
            for (int i = 0; i < type; i++) {
                Cell cell = board.getCellOnBoard(coordinates.get(i).getX(), coordinates.get(i).getY());
                if (i == 0)
                    cell.setFill(new ImagePattern(shipDestroyedVerticalBow));
                if (i > 0 && i < type - 1)
                    cell.setFill(new ImagePattern(shipDestroyedVerticalCenter));
                if (i == type - 1)
                    cell.setFill(new ImagePattern(shipDestroyedVerticalStern));
            }

        } else if (!isVertical && type > 1) {
            for (int i = 0; i < type; i++) {
                Cell cell = board.getCellOnBoard(coordinates.get(i).getX(), coordinates.get(i).getY());
                if (i == 0)
                    cell.setFill(new ImagePattern(shipDestroyedHorizontalStern));
                if (i > 0 && i < type - 1)
                    cell.setFill(new ImagePattern(shipDestroyedHorizontalCenter));
                if (i == type - 1)
                    cell.setFill(new ImagePattern(shipDestroyedHorizontalBow));
            }
        }
    }
}