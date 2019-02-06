package com.silcos.board;

public final class MoveFactory {

    public static Move newMove(Board target, int srcRow, int srcColumn,
                               int dstRow, int dstColumn) {
        boolean isVisible = true;

        if(target.getCell(srcRow, srcColumn).getHolder() == null &&
                target.getCell(dstRow, dstColumn).getHolder() == null) {
            isVisible = false;
        }

        return new Move(isVisible, srcRow, srcColumn, dstRow, dstColumn);
    }

    private MoveFactory() {
    }

}
