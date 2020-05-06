package com.kodilla.individualProjects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Content {
    Background background = BackgroundForGame.myBackground();

    private Board enemyBoard, playerBoard;
    private final Enemy enemy = new Enemy();
    private final int enemyShipsToPlace = 10;
    private int playerShipsToPlace = 10;

    private boolean gamePrepared = false;
    private boolean enemyTurn = false;
    private static boolean gameFinished = false;

    static Text resultText = new Text();

    Color textResultBattleColor = Color.rgb(57, 84, 135);
    Color textResultWinColor = Color.rgb(217, 85, 250);

    public Parent create() {
        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
        root.setBackground(background);

        resultText.setText("Let the battle begin!!!");
        resultText.setFont(Font.loadFont("file:resources/Fonts/IsadoraCyrPro.ttf", 45));
        resultText.setFill(textResultBattleColor);
        resultText.setStroke(Color.BLACK);

        Rectangle rectangleLeft = new Rectangle(400, 200);
        Rectangle rectangleTop = new Rectangle(1600, 300);
        rectangleLeft.setOpacity(0);
        rectangleTop.setOpacity(0);

        root.setTop(rectangleTop);
        root.setLeft(rectangleLeft);


        enemyBoard = new Board(true, event -> {
            if (!gamePrepared || gameFinished)
                return;

            Cell cellOnEnemyBoardToHit = (Cell) event.getSource();
            if (cellOnEnemyBoardToHit.wasAlreadyShot)
                return;

            enemyTurn = !cellOnEnemyBoardToHit.wasThereAShip();

            if (enemyBoard.ships == 0) {
                resultText.setText("You have won!!!");
                resultText.setFill(textResultWinColor);
                gameFinished = true;
                return;
            }

            if (enemyTurn)
                enemy.enemyMove(playerBoard, true);
        });

        playerBoard = new Board(false, event -> {
            if (gamePrepared || gameFinished)
                return;

            Cell cellOnPlayerBoardToHit = (Cell) event.getSource();
            if (playerBoard.placeShip(
                    new Ship(Ship.getRussianShips()
                            .get(playerShipsToPlace - 1),
                            event.getButton() == MouseButton.PRIMARY),
                    cellOnPlayerBoardToHit.x,
                    cellOnPlayerBoardToHit.y)) {

                playerShipsToPlace--;

                if (playerShipsToPlace == 0) {
                    gamePrepared = enemy.prepareShips(enemyBoard, enemyShipsToPlace);
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15,15,15,15));
        gridPane.add(resultText, 0,0);

        HBox hBox = new HBox(50, playerBoard, enemyBoard);
        hBox.setAlignment(Pos.CENTER);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        flowPane.getChildren().add(hBox);
        flowPane.getChildren().add(gridPane);

        root.setCenter(flowPane);

        return root;
    }

    public static void setResultText(String text) {
        resultText.setText(text);
    }

    public static void setTextResultColor(Color textResultColor) {
        resultText.setFill(textResultColor);
    }

    public static void setGameFinished(boolean myGameFinished) {
        gameFinished = myGameFinished;
    }
}
