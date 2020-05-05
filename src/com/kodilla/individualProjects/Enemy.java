package com.kodilla.individualProjects;

import java.util.Random;

public class Enemy {

    private final Random random = new Random();

    public boolean prepareShips(Board enemyBoard, int enemyShipsToPlace) {
        while (enemyShipsToPlace > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new Ship(Ship.getRussianShips().get(enemyShipsToPlace-1), Math.random() < 0.5), x, y)) {
                enemyShipsToPlace--;
            }
        }

        return true;
    }

    public void enemyMove(Board playerBoard, boolean enemyTurn) {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCellOnBoard(x, y);
            if (cell.wasAlreadyShot)
                continue;

            enemyTurn = cell.hittingOpponentsCell();

            if (playerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                System.exit(0);
            }
        }
    }
}
