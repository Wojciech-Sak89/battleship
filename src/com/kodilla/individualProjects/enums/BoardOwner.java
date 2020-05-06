package com.kodilla.individualProjects.enums;

public enum BoardOwner {
    ENEMY(true), PLAYER(false);

    public boolean isOwner;

    BoardOwner(boolean enemyOwns) {
        this.isOwner = enemyOwns;
    }

    public boolean value () {
        return isOwner;
    }
}

