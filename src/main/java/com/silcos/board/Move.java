package com.silcos.board;

import com.silcos.board.Board.BoardChange;
import com.silcos.board.Board.BoardChangeType;

public class Move extends BoardChange {

    private static BoardChangeType MOVE_TYPE = BoardChangeType.MOVE;

    int mSrcRow;
    int mSrcColumn;
    int mDstRow;
    int mDstColumn;

    public int killedPieceId;

    public Move(boolean isVisible, int srcRow, int srcColumn, int dstRow, int dstColumn) {
        mIsVisible = isVisible;
        mSrcRow = srcRow;
        mSrcColumn = srcColumn;
        mDstRow = dstRow;
        mDstColumn = dstColumn;
    }

    public int sourceRow() {
        return mSrcRow;
    }

    public int sourceColumn() {
        return mSrcColumn;
    }

    public void resetSource(int row, int column) {
        mSrcRow = row;
        mSrcColumn = column;
    }

    public int destinationRow() {
        return mDstRow;
    }

    public int destinationColumn() {
        return mDstColumn;
    }

    public void resetDestination(int row, int column) {
        mDstRow = row;
        mDstColumn = column;
    }
}