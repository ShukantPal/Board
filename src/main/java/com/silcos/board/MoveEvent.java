package com.silcos.board;

public class MoveEvent extends Event {

    private static int sPastMoveEvents = 0;

    public final int sourceRow;
    public final int sourceColumn;
    public final int destRow;
    public final int destColumn;
    public final boolean pebbleKilled;
    public final Object targetHolder;
    public final Object killedHolder;

    public MoveEvent(BoardGame origin, int srcRow, int srcCol, int dstRow, int dstCol,
                     Object targetHolder,
                     Object killedHolder) {
        super(BoardGame.PIECE_MOVE_EVENT + sPastMoveEvents++, origin);
        this.sourceRow = srcRow;
        this.sourceColumn = srcCol;
        this.destRow = dstRow;
        this.destColumn = dstCol;
        this.pebbleKilled = killedHolder != null;
        this.targetHolder = targetHolder;
        this.killedHolder = killedHolder;
    }

    @Override
    public int typeId() {
        return BoardGame.PIECE_MOVE_EVENT;
    }
}
