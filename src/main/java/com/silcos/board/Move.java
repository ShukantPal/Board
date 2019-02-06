package com.silcos.board;

import com.silcos.board.Board.BoardChange;
import com.silcos.board.Board.BoardChangeType;

/**
 * {@code Move} objects holds information required to spatially move
 * pieces on the board from one cell to another.
 *
 * If the rules allow, the dest-cell can already hold another piece
 * that will be killed in the process.
 *
 * Moves are deferred immutable, which means that they can be locked
 * after which they cannot be mutated. (NOT IMPLEMENTED YET)
 */
public class Move extends BoardChange {

    private static BoardChangeType MOVE_TYPE = BoardChangeType.MOVE;

    int srcRow;
    int srcCol;
    int dstRow;
    int dstCol;

    public int killedPieceId;

    public Move(boolean isVisible, int srcRow, int srcColumn, int dstRow, int dstColumn) {
        mIsVisible = isVisible;
        this.srcRow = srcRow;
        srcCol = srcColumn;
        this.dstRow = dstRow;
        dstCol = dstColumn;
    }

    public int getSrcRow() {
        return srcRow;
    }

    public int getSrcCol() {
        return srcCol;
    }

    public void resetSrc(int row, int column) {
        srcRow = row;
        srcCol = column;
    }

    public int getDstRow() {
        return dstRow;
    }

    public int getDstCol() {
        return dstCol;
    }

    public void resetDst(int row, int column) {
        dstRow = row;
        dstCol = column;
    }
}