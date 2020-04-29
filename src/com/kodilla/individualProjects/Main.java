package com.kodilla.individualProjects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
   Content content = new Content();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(content.create());
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
