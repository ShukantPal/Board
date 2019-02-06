package com.silcos.board;

/**
 * {@code PlayerRotator} objects can be used to handle the flow of control
 * between different players. Each player is given the chance (a turn)
 * to make a move, in the order decided by a PlayerRotator.
 */
public interface PlayerRotator {

    /**
     * Returns the array of players that this object works on.
     *
     * @return array of players
     */
    public Player[] getPlayers();

    /**
     * Set the players
     *
     * @param players
     */
    public void setPlayers(Player[] players);

    public int getCurrentId();

    public Player nextPlayer();

    public Player previousPlayer();

}
