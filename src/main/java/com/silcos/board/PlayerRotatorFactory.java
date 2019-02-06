package com.silcos.board;

public abstract class PlayerRotatorFactory {

    public abstract PlayerRotator newRotator();

    public PlayerRotator newRotator(Player[] players) {
        PlayerRotator rotater = newRotator();
        rotater.setPlayers(players);

        return rotater;
    }

}
