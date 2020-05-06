package com.kodilla.individualProjects;

import javafx.scene.image.Image;

public class Images {

    // Patrol ships, alive or destroyed
    Image patrolShipVertical = new Image("file:resources/Battleships-by-size/1/ShipPatrolHull_OnBackground_Vertical.png");
    Image patrolShipHorizontal = new Image("file:resources/Battleships-by-size/1/ShipPatrolHull_OnBackground_Horizontal.png");

    Image patrolShipDestroyedVertical = new Image("file:resources/Battleships-by-size/1/ShipPatrolHull_Destroyed_OnBackground_Vertical.png");
    Image patrolShipDestroyedHorizontal = new Image("file:resources/Battleships-by-size/1/ShipPatrolHull_Destroyed_OnBackground_Horizontal.png");


    // Default ships fragments
    Image shipDefaultVerticalBow = new Image("file:resources/Battleships-by-size/Alive/Vertical/Ship_Alive_Bow_Vertical.png");
    Image shipDefaultVerticalCenter = new Image("file:resources/Battleships-by-size/Alive/Vertical/Ship_Alive_Center_Vertical.png");
    Image shipDefaultVerticalStern = new Image("file:resources/Battleships-by-size/Alive/Vertical/Ship_Alive_Stern_Vertical.png");

    Image shipDefaultHorizontalBow = new Image("file:resources/Battleships-by-size/Alive/Horizontal/Ship_Alive_Bow_Horizontal.png");
    Image shipDefaultHorizontalCenter = new Image("file:resources/Battleships-by-size/Alive/Horizontal/Ship_Alive_Center_Horizontal.png");
    Image shipDefaultHorizontalStern = new Image("file:resources/Battleships-by-size/Alive/Horizontal/Ship_Alive_Stern_Horizontal.png");

    // Default destroyed ships fragments
    Image shipDestroyedVerticalBow = new Image("file:resources/Battleships-by-size/Destroyed/Vertical/Ship_Destroyed_Bow_Vertical.png");
    Image shipDestroyedVerticalCenter = new Image("file:resources/Battleships-by-size/Destroyed/Vertical/Ship_Destroyed_Center_Vertical.png");
    Image shipDestroyedVerticalStern = new Image("file:resources/Battleships-by-size/Destroyed/Vertical/Ship_Destroyed_Stern_Vertical.png");

    Image shipDestroyedHorizontalBow = new Image("file:resources/Battleships-by-size/Destroyed/Horizontal/Ship_Destroyed_Bow_Horizontal.png");
    Image shipDestroyedHorizontalCenter = new Image("file:resources/Battleships-by-size/Destroyed/Horizontal/Ship_Destroyed_Center_Horizontal.png");
    Image shipDestroyedHorizontalStern = new Image("file:resources/Battleships-by-size/Destroyed/Horizontal/Ship_Destroyed_Stern_Horizontal.png");

    // Tiles
    Image defaultWater = new Image("file:resources/Tiles/smalldefaultWater-tile.png");
    Image shotCellWithNoShip = new Image("file:resources/Tiles/smallMissed-tile.png");
    Image shipOnFire = new Image("file:resources/Tiles/explosion.png");
}
