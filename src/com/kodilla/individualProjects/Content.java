package com.kodilla.individualProjects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
    private static int enemyShipsToPlace;
    private static int playerShipsToPlace;

    Marker enemyMarker;
    Marker playerMarker;

    private boolean gamePrepared = false;
    private boolean enemyTurn = false;
    private static boolean gameFinished = false;

    static boolean classicMode;
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

        Rectangle rectangleLeft = new Rectangle(100, 200);
        Rectangle rectangleTop = new Rectangle(1600, 300);
        rectangleLeft.setOpacity(0);
        rectangleTop.setOpacity(0);

        root.setTop(rectangleTop);
        root.setLeft(rectangleLeft);


        enemyBoard = new Board(true, event -> {
            if (!gamePrepared || gameFinished)
                return;

            CellOnBoard cellOnEnemyBoardToHit = (CellOnBoard) event.getSource();
            if (cellOnEnemyBoardToHit.wasAlreadyShot)
                return;

            enemyTurn = !cellOnEnemyBoardToHit.shootCell().shootShip();

            if (enemyBoard.ships == 0) {
                resultText.setText("You have won!!!");
                resultText.setFill(textResultWinColor);
                gameFinished = true;
                return;
            }

            if (enemyTurn) {
                enemy.enemyMove(playerBoard, true);
            }
        });

        playerBoard = new Board(false, event -> {
            if (gamePrepared || gameFinished)
                return;

            CellOnBoard cellOnBoardOnPlayerBoardToHit = (CellOnBoard) event.getSource();
            if (playerBoard.placeShip(
                    new Ship(
                            Ship.getShips(classicMode).get(getPlayerShipsToPlace() - 1),
                            event.getButton() == MouseButton.PRIMARY),
                    cellOnBoardOnPlayerBoardToHit.x,
                    cellOnBoardOnPlayerBoardToHit.y)) {

                playerShipsToPlace--;

                if (playerShipsToPlace == 0) {
                    gamePrepared = enemy.prepareShips(enemyBoard, getEnemyShipsToPlace());
                }
            }
        });

        enemyMarker = new Marker(enemyBoard);
        playerMarker = new Marker(playerBoard);

        // Buttons for menu: New Game, Difficulty, Classic/Russian Game,  Quit
        //new game
        Button newGameButton = new Button("New Game");
        Button quitButton = new Button("Quit");

        ToggleGroup difficultyModes = new ToggleGroup();
        ToggleButton normalMode = new ToggleButton("Normal mode");
        ToggleButton hardMode = new ToggleButton("Hard mode");
        normalMode.setToggleGroup(difficultyModes);
        hardMode.setToggleGroup(difficultyModes);
        normalMode.setSelected(true);
        HBox difficultyBox = new HBox(20, normalMode, hardMode);

        ToggleGroup shipsModes = new ToggleGroup();
        ToggleButton classicMode = new ToggleButton("Classic");
        ToggleButton russianMode = new ToggleButton("Russian");
        classicMode.setToggleGroup(shipsModes);
        russianMode.setToggleGroup(shipsModes);
        normalMode.setSelected(true);
        HBox shipsModesBox = new HBox(20, classicMode, russianMode);

        newGameButton.setOnAction(actionEvent -> restartGame());

        normalMode.setOnAction(actionEvent -> enemy.setHardMode(false));
        hardMode.setOnAction(actionEvent -> enemy.setHardMode(true));

        classicMode.setOnAction(actionEvent -> {
            setClassicMode(true);
            enemyShipsToPlace = 5;
            playerShipsToPlace = 5;

            enemyBoard.ships = 5;
            playerBoard.ships = 5;
        });

        russianMode.setOnAction(actionEvent -> {
            setClassicMode(false);
            enemyShipsToPlace = 10;
            playerShipsToPlace = 10;

            enemyBoard.ships = 10;
            playerBoard.ships = 10;
        });

        quitButton.setOnAction(actionEvent -> System.exit(0));


        // GridPane left
        GridPane gridPaneLeft = new GridPane();
        gridPaneLeft.setHgap(15);
        gridPaneLeft.setVgap(15);
        gridPaneLeft.setPadding(new Insets(15, 15, 15, 15));
        gridPaneLeft.add(newGameButton, 0, 0);

        gridPaneLeft.add(new Text("Difficulty"), 0, 1);
        gridPaneLeft.add(difficultyBox, 1, 1);

        gridPaneLeft.add(new Text("Game Mode"), 0, 2);
        gridPaneLeft.add(shipsModesBox, 1, 2);

        gridPaneLeft.add(quitButton, 0, 3);

        // GridPane right
        GridPane gridPaneRight = new GridPane();
        gridPaneRight.setHgap(15);
        gridPaneRight.setVgap(15);
        gridPaneRight.setPadding(new Insets(15, 15, 15, 15));
        gridPaneRight.add(resultText, 0, 0);

        // Board hBox
        HBox hBox = new HBox(50, playerBoard, enemyBoard);
        hBox.setAlignment(Pos.CENTER);

        // All in FlowPane
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        flowPane.getChildren().add(gridPaneLeft);
        flowPane.getChildren().add(hBox);
        flowPane.getChildren().add(gridPaneRight);


        // setting root
        root.setCenter(flowPane);

        return root;

    }


    private void restartGame() {
        enemyMarker.makeNewBoard();
        playerMarker.makeNewBoard();

        playerShipsToPlace = 10;

        enemyBoard.ships = 10;
        playerBoard.ships = 10;

        gamePrepared = false;
        gameFinished = false;

        enemyTurn = false;

        resultText.setText("Let the battle begin!!!");
        resultText.setFill(textResultBattleColor);
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

    public static void setClassicMode(boolean classicMode) {
        Content.classicMode = classicMode;
    }

    public static int getEnemyShipsToPlace() {
        return enemyShipsToPlace;
    }

    public static int getPlayerShipsToPlace() {
        return playerShipsToPlace;
    }
}
