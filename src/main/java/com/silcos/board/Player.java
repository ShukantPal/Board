package com.silcos.board;

import com.silcos.board.BoardGame.GameInputController;

/**
 * Players take turns while playing on the board and are notified
 * whenever their turns come. When they are ready to place their
 * move, they should call deliverMove() to signal that to the
 * board controller.
 */
public abstract class Player {

    protected final BoardGame mWork;
    protected final GameInputController mInputController;
    protected Piece[] myPieces = null;

    public Player(BoardGame work, GameInputController inputController) {
        mWork = work;
        mInputController = inputController;
    }

    protected Board boardController() {
       return mWork.board();
    }

    /**
     * Override this to return a specific input controller for
     * your board game, e.g. ChessGameInputController.
     *
     * @return
     */
    protected GameInputController inputController() {
        return mInputController;
    }

    protected boolean deliverMove(Move turn) {
        return inputController().placeMove(turn);
    }

    protected void allocatePieces(int count, int myId) {
        myPieces = new Piece[count];

        for(int i = 0; i < count; i++) {
            myPieces[i] = new Piece(i, myId);
        }
    }

    public void resign() {

    }

    public abstract void onTurn();

    public abstract void initAfterGame();

}
