package com.kodilla.individualProjects;

import javafx.scene.paint.Color;

import java.util.Random;

public class Enemy {
    private boolean hardMode;
    private final Random random = new Random();
    private final Color textResultLoseColor = Color.rgb(144, 6, 37);

    public boolean prepareShips(Board enemyBoard, int enemyShipsToPlace) {
        while (enemyShipsToPlace > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new Ship(
                    Ship.getShips(Content.isClassicMode()).get(enemyShipsToPlace-1),
                    (Math.random() < 0.5)),
                    x, y)) {
                enemyShipsToPlace--;
            }
        }

        return true;
    }

    public void enemyMove(Board playerBoard, boolean enemyTurn) {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int shootWithAdvantage = random.nextInt(100 + 1);

            CellOnBoard cellOnBoard = playerBoard.getCellOnBoard(x, y);
            if (cellOnBoard.isShot())
                continue;

            if (hardMode) {
                if (cellOnBoard.getShip() == null && shootWithAdvantage > 35) {
                    continue;
                }
            }

            enemyTurn = cellOnBoard.shootCell().shootShip();

            if (playerBoard.getShips() == 0) {
                Content.setResultText("You have lost...");
                Content.setTextResultColor(textResultLoseColor);

                Content.setGameFinished(true);
                break;
            }
        }
    }

    public void setHardMode(boolean hardMode) {
        this.hardMode = hardMode;
    }
}
