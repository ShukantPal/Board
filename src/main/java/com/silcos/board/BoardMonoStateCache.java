package com.silcos.board;

/**
 * BoardMonoStateCache holds a state of the board (past or present) in
 * a compact form, allowing retrieval at a later point. It can be used
 * whenever the board has mono-cells (which hold only one piece at a
 * time, unlike Ludo).
 *
 * State of the board is stored by caching the playerId & id of each
 * piece on the board. Each board cell must store only one piece at a
 * time; hence, the name mono-state cache.
 */
public class BoardMonoStateCache {

    /**
     * Game for which this state existed.
     */
    protected final BoardGame mGame;

    /**
     * Cell states are encoded in 64-bit integers. Upper 32-bits store
     * the playerId and lower 32-bits store the piece id.
     */
    private final long mCellStateCaches[][];

    /**
     * Returns the playerId for the cell state value given.
     *
     * @param cellStateCache
     * @return playerId
     */
    protected int getPlayerId(long cellStateCache) {
        return (int)(cellStateCache >> 32);
    }

    /**
     * Returns the piece id for the cell state value given.
     *
     * @param cellStateCache
     * @return id
     */
    protected int getPieceIndex(long cellStateCache) {
        return (int)(cellStateCache);
    }

    /**
     * Encodes the playerId and piece id given into a 64-bit long. They
     * can later be retrieved using getPlayerId() & getPieceIndex().
     *
     * @param playerId id of the player holding this piece
     * @param pieceIndex index of the piece (or just id())
     * @return cache value
     */
    protected long getCacheValue(int playerId, int pieceIndex) {
        return ((long) playerId << 32) | (long) (pieceIndex);
    }

    /**
     * Returns the state of the cell at (row, col).
     *
     * @param row row of the cell
     * @param col column of the cell
     * @return stored state of the cell
     */
    protected long getCellStateCache(int row, int col) {
        return mCellStateCaches[row][col];
    }

    /**
     * Sets the value of the state cache for the cell at (row, col) to the
     * value given. This method shouldn't be called directly. Instead, see
     * {@code setCellStateCache(int, int, int, int)}
     *
     * @param newState state value to set
     * @param row row of the cell
     * @param col column of the cell
     */
    protected void setCellStateCache(long newState, int row, int col) {
        mCellStateCaches[row][col] = newState;
    }

    /**
     * Sets the state of the piece at (row, col) to the playerId & id.
     *
     * @param playerId
     * @param pieceIndex
     * @param row
     * @param col
     */
    protected void setCellStateCache(int playerId, int pieceIndex, int row, int col) {
        setCellStateCache(getCacheValue(playerId, pieceIndex), row, col);
    }

    protected BoardMonoStateCache(BoardGame game, int rows, int cols) {
        mGame = game;
        mCellStateCaches = new long[rows][cols];
    }

    protected BoardMonoStateCache(BoardMonoStateCache sourceCache, int newRows, int newCols) {
        mGame = sourceCache.mGame;
        mCellStateCaches = new long[newRows][newCols];

        for (int r = 0; r < newRows; r++) {
            for (int c = 0; c < newCols; c++) {
                mCellStateCaches[r][c] = sourceCache.mCellStateCaches[r][c];
            }
        }
    }

    /**
     * Returns the piece at the location at (row, col) for this state of
     * the board.
     *
     * @param row
     * @param col
     * @return
     */
    public Piece pieceAt(int row, int col) {
        final long cellState = getCellStateCache(row, col);

        if (cellState == -1)
            return null;

        final int playerId = getPlayerId(cellState);
        final int pieceIndex = getPieceIndex(cellState);

        return mGame.getPlayer(playerId).myPieces[pieceIndex];
    }

    public BoardMonoStateCache doMove(Move move, int newCacheRows, int newCacheCols) {
        long value = getCellStateCache(move.getSrcRow(), move.getSrcCol());
        BoardMonoStateCache copy = copyCache(this, newCacheRows, newCacheCols);
        copy.setCellStateCache(-1, move.getSrcRow(), move.getSrcCol());
        copy.setCellStateCache(value, move.getDstRow(), move.getDstCol());
        return copy;
    }

    /**
     * Builds the state-cache for the board in game.
     *
     * @param game
     * @return state-cache built for game
     */
    public static BoardMonoStateCache buildCache(BoardGame game) {
        final Board stateCtl = game.board();
        final int rowCount = stateCtl.getHeight();
        final int colCount = stateCtl.getWidth();
        final BoardMonoStateCache stateCache =
                new BoardMonoStateCache(game, rowCount, colCount);

        for (int rowIdx = 0; rowIdx < rowCount; rowIdx++) {
            for (int colIdx = 0; colIdx < colCount; colIdx++) {
                final Piece piece = game.pieceAt(rowIdx, colIdx);

                if (piece != null) {
                    stateCache.setCellStateCache(piece.playerId(),
                            piece.id(), rowIdx, colIdx);
                } else {
                    stateCache.setCellStateCache(-1, rowIdx, colIdx);
                }
            }
        }

        return stateCache;
    }

    public static BoardMonoStateCache copyCache(BoardMonoStateCache stateCache,
                                                int newRow, int newCol) {
        return new BoardMonoStateCache(stateCache, newRow, newCol);
    }

}
