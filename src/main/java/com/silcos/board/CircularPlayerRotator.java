package com.silcos.board;

/**
 * {@code CircularPlayerRotator} delivers the flow of control between all
 * registered players in a circular fashion - from the first to the last
 * and then again first.
 *
 * {@link CircularPlayerRotator.Factory} can be used as a factory for this
 * type of rotator.
 */
public class CircularPlayerRotator extends AbstractPlayerRotator {

    public static class Factory extends PlayerRotatorFactory {

        @Override
        public PlayerRotator newRotator() {
            return new CircularPlayerRotator();
        }
    }

    public CircularPlayerRotator() {
        super();
    }

    public CircularPlayerRotator(Player[] players) {
        super(players);
    }

    @Override
    public Player nextPlayer() {
        mCurrentId += 1;

        if(mCurrentId == mPlayers.length) {
            mCurrentId = 0;
        }

        return mPlayers[mCurrentId];
    }

    @Override
    public Player previousPlayer() {
        return mPlayers[mCurrentId];
    }
}
