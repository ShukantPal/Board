package com.silcos.board;

import static com.silcos.board.BoardGame.ELIMINATION_EVENT;

public class EliminationEvent extends Event {
    public final int playerId;

    protected EliminationEvent(int playerId, int id, BoardGame origin) {
        super(id, origin);
        this.playerId = playerId;
    }

    @Override
    public int typeId() {
        return ELIMINATION_EVENT;
    }
}
