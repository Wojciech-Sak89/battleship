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
    private final Background background = BackgroundForGame.myBackground();
    private final Color textResultBattleColor = Color.rgb(57, 84, 135);
    private final Color textResultWinColor = Color.rgb(217, 85, 250);
    private final Enemy enemy = new Enemy();
    private static final Text resultText = new Text();

    private static int enemyShipsToPlace;
    private static int playerShipsToPlace;
    private static boolean gameFinished = false;
    private static boolean classicMode;

    private Marker enemyMarker;
    private Marker playerMarker;

    private boolean gamePrepared = false;
    private boolean enemyTurn = false;

    private Board enemyBoard, playerBoard;


    public Parent create() {
        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
        root.setBackground(background);

        initializeResultText();
        initializeBorderAlignmentSupport(root);

        runEnemyBoard();
        runPlayerBoard();

        newGame();

        enemyMarker = new Marker(enemyBoard);
        playerMarker = new Marker(playerBoard);

        Button newGameButton = new Button("New Game");
        System.out.println(newGameButton.isPressed());
        Button quitButton = new Button("Quit");

        ToggleGroup difficultyModes = new ToggleGroup();
        ToggleButton normalModeButton = new ToggleButton("Normal mode");
        ToggleButton hardModeButton = new ToggleButton("Hard mode");
        normalModeButton.setToggleGroup(difficultyModes);
        hardModeButton.setToggleGroup(difficultyModes);
        normalModeButton.setSelected(false);
        HBox difficultyBox = new HBox(20, normalModeButton, hardModeButton);

        ToggleGroup shipsModes = new ToggleGroup();
        ToggleButton classicMode = new ToggleButton("Classic");
        ToggleButton russianMode = new ToggleButton("Russian");
        classicMode.setToggleGroup(shipsModes);
        russianMode.setToggleGroup(shipsModes);
        russianMode.setSelected(false);
        HBox shipsModesBox = new HBox(20, classicMode, russianMode);

        newGameButton.setOnAction(actionEvent -> restartGame());
        normalModeButton.setOnAction(actionEvent -> enemy.setHardMode(false));
        hardModeButton.setOnAction(actionEvent -> enemy.setHardMode(true));
        runShipModeButton(classicMode, true, 5);
        runShipModeButton(russianMode, false, 10);
        quitButton.setOnAction(actionEvent -> System.exit(0));

        GridPane gridPaneLeft = new GridPane();
        initializeLeftGridPane(newGameButton, quitButton, difficultyBox, shipsModesBox, gridPaneLeft);

        GridPane gridPaneRight = new GridPane();
        initializeRightGridPane(gridPaneRight);

        HBox hBox = new HBox(50, playerBoard, enemyBoard);
        hBox.setAlignment(Pos.CENTER);

        FlowPane flowPane = new FlowPane();
        initializeFlowPane(gridPaneLeft, gridPaneRight, hBox, flowPane);

        root.setCenter(flowPane);

        return root;
    }

    private void initializeFlowPane(GridPane gridPaneLeft, GridPane gridPaneRight, HBox hBox, FlowPane flowPane) {
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        flowPane.getChildren().add(gridPaneLeft);
        flowPane.getChildren().add(hBox);
        flowPane.getChildren().add(gridPaneRight);
    }

    private void initializeRightGridPane(GridPane gridPaneRight) {
        gridPaneRight.setHgap(15);
        gridPaneRight.setVgap(15);
        gridPaneRight.setPadding(new Insets(15, 15, 15, 15));
        gridPaneRight.add(resultText, 0, 0);
    }

    private void initializeLeftGridPane(Button newGameButton, Button quitButton, HBox difficultyBox, HBox shipsModesBox, GridPane gridPaneLeft) {
        gridPaneLeft.setHgap(15);
        gridPaneLeft.setVgap(15);
        gridPaneLeft.setPadding(new Insets(15, 15, 15, 15));
        gridPaneLeft.add(newGameButton, 0, 0);

        gridPaneLeft.add(new Text("Difficulty"), 0, 1);
        gridPaneLeft.add(difficultyBox, 1, 1);

        gridPaneLeft.add(new Text("Game Mode"), 0, 2);
        gridPaneLeft.add(shipsModesBox, 1, 2);

        gridPaneLeft.add(quitButton, 0, 3);
    }

    private void initializeBorderAlignmentSupport(BorderPane root) {
        Rectangle rectangleLeft = new Rectangle(100, 200);
        Rectangle rectangleTop = new Rectangle(1600, 300);
        rectangleLeft.setOpacity(0);
        rectangleTop.setOpacity(0);

        root.setTop(rectangleTop);
        root.setLeft(rectangleLeft);
    }

    private void initializeResultText() {
        resultText.setText("Let the battle begin!!!");
        resultText.setFont(Font.loadFont("file:resources/Fonts/IsadoraCyrPro.ttf", 45));
        resultText.setFill(textResultBattleColor);
        resultText.setStroke(Color.BLACK);
    }

    private void runShipModeButton(ToggleButton classicMode, boolean b, int i) {
        classicMode.setOnAction(actionEvent -> {
            setClassicMode(b);
            enemyShipsToPlace = i;
            playerShipsToPlace = i;

            enemyBoard.setShips(i);
            playerBoard.setShips(i);
        });
    }

    private void runPlayerBoard() {
        playerBoard = new Board(false, event -> {
            if (gamePrepared || gameFinished)
                return;

            CellOnBoard cellOnPlayerBoardToHit = (CellOnBoard) event.getSource();
            if (playerBoard.placeShip(
                    new Ship(
                            Ship.getShips(classicMode).get(getPlayerShipsToPlace() - 1),
                            event.getButton() == MouseButton.PRIMARY),
                    cellOnPlayerBoardToHit.getXvar(),
                    cellOnPlayerBoardToHit.getYvar())) {

                playerShipsToPlace--;

                if (playerShipsToPlace == 0) {
                    gamePrepared = enemy.prepareShips(enemyBoard, getEnemyShipsToPlace());
                }
            }
        });
    }

    private void runEnemyBoard() {
        enemyBoard = new Board(true, event -> {
            if (!gamePrepared || gameFinished)
                return;

            CellOnBoard cellOnEnemyBoardToHit = (CellOnBoard) event.getSource();
            if (cellOnEnemyBoardToHit.isShot())
                return;

            enemyTurn = !cellOnEnemyBoardToHit.shootCell().shootShip();

            if (enemyBoard.getShips() == 0) {
                resultText.setText("You have won!!!");
                resultText.setFill(textResultWinColor);
                gameFinished = true;
                return;
            }

            if (enemyTurn) {
                enemy.enemyMove(playerBoard, true);
            }
        });
    }

    private void restartGame() {
        enemyMarker.makeNewBoard();
        playerMarker.makeNewBoard();

       newGame();
    }

    private void newGame() {

        setClassicMode(false);

        enemyShipsToPlace = 10;
        playerShipsToPlace = 10;

        enemyBoard.setShips(10);
        playerBoard.setShips(10);

        gamePrepared = false;
        gameFinished = false;

        enemyTurn = false;

        resultText.setText("Let the battle begin!!!");
        resultText.setFill(textResultBattleColor);
    }

    public static boolean isClassicMode() {
        return classicMode;
    }

    public static int getEnemyShipsToPlace() {
        return enemyShipsToPlace;
    }

    public static int getPlayerShipsToPlace() {
        return playerShipsToPlace;
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




}
