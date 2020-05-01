package com.kodilla.individualProjects;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Content {
    Background background = BackgroundForGame.myBackground();

    private Board enemyBoard, playerBoard;
    private final Enemy enemy = new Enemy();
    private final int enemyShipsToPlace = 10;
    private int playerShipsToPlace = 10;

    private boolean gamePrepared = false;
    private boolean enemyTurn = false;

    public Parent create() {
        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
        root.setBackground(background);

        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));

        enemyBoard = new Board(true, event -> {
            if (!gamePrepared)
                return;

            Cell cellClickedOnEnemyBoard = (Cell) event.getSource();
            if (cellClickedOnEnemyBoard.wasAlreadyShot)
                return;

            enemyTurn = !cellClickedOnEnemyBoard.hittingOpponentsCell();

            if (enemyBoard.ships == 0) {
                System.out.println("YOU WIN");
                System.exit(0);
            }

            if (enemyTurn)
                enemy.enemyMove(playerBoard, true);
        });

        playerBoard = new Board(false, event -> {
            if (gamePrepared)
                return;

            Cell cellClickedOnPlayerBoard = (Cell) event.getSource();
            if (playerBoard.placeShip(new Ship(Ship.getShipsSizesForRussianGame().get(playerShipsToPlace-1),
                                       event.getButton() == MouseButton.PRIMARY),
                                      cellClickedOnPlayerBoard.x, cellClickedOnPlayerBoard.y)) {
                if (--playerShipsToPlace == 0) { //zdekrementować
                    gamePrepared = enemy.prepareShips(enemyBoard, enemyShipsToPlace);
                }
            }
        });

        HBox hBox = new HBox(50, playerBoard, enemyBoard);
        hBox.setAlignment(Pos.CENTER);

        root.setCenter(hBox);

        return root;
    }
}
