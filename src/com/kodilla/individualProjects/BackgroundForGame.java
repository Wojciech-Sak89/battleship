package com.kodilla.individualProjects;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BackgroundForGame {
    private static final Image imageForBackground = new Image("file:resources/background.jpg");

    static BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    static BackgroundImage backgroundImage = new BackgroundImage
                                            (imageForBackground,
                                                    BackgroundRepeat.REPEAT,
                                                    BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.CENTER,
                                                    backgroundSize);
    static Background background = new Background(backgroundImage);

    public static Background myBackground() {
        return background;
    }
}
