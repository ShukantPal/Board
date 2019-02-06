package com.silcos.board;

/**
 * You can build a custom FinishEvent class to give more specific
 * results.
 */
public class FinishEvent extends Event {

    public final int winnerId;

    public FinishEvent(final BoardGame origin, int winnerId) {
        super(origin);
        this.winnerId = winnerId;
    }

    @Override
    public int typeId() {
        return BoardGame.GAME_FINISH_EVENT;
    }
}
