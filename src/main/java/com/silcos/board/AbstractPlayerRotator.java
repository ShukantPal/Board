package com.silcos.board;

/**
 * {@code AbstractPlayerRotator} can be inherited to make your own
 * custom rotator. It uses an array to store players and points to
 * the currently selected one using its index into that array.
 *
 * You must override getNextPlayer() and getPreviousPlayer() to
 * complete the rotator.
 */
public abstract class AbstractPlayerRotator implements PlayerRotator {

    protected Player[] mPlayers;
    protected int mCurrentId;

    protected AbstractPlayerRotator() {
    }

    protected AbstractPlayerRotator(Player[] players) {
        mPlayers = players;
    }

    @Override
    public final Player[] getPlayers() {
        return mPlayers;
    }

    @Override
    public void setPlayers(Player[] players) {
        mPlayers = players;
    }

    @Override
    public final int getCurrentId() {
        return mCurrentId;
    }
}
