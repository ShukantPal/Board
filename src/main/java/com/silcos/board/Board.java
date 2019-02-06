package com.silcos.board;

/**
 * {@code Board} handles the spatial configuration of pieces that are
 * alive. Most board games have only one {@link Board} used; hence,
 * {@link BoardGame} is built around only one {@code Board} object.
 *
 * Note that {@code Board} is a 2-D rectangular object that stores
 * each piece in its own {@link Cell} container.
 *
 * Static utilities are also given for getting results related to
 * spatial functions on pieces.
 *
 * You must inherit from {@code Board} to use it in your app, and
 * override the {@code handle} method. Handle specifically implements
 * moves that are inputted into the game, and checks whether they are
 * valid, according to the rules, and returns whether it was passed
 * or not.
 */
public abstract class Board {

    public enum BoardChangeType {
        MOVE
    }

    public static class BoardChange {

        protected boolean mIsVisible;

        protected BoardChangeType mType;

        public boolean isVisible() {
            return mIsVisible;
        }

        public BoardChangeType getType() {
            return mType;
        }

    }

    /**
     * Height of the board, in terms of cells; that is also the no. of
     * rows present.
     */
    private int height;

    /**
     * Width of the board, in terms of cells; that is also the no. of
     * columns present.
     */
    private int width;

    /**
     * Cell data for this board; contains individual cells for each
     * spatial location at which a piece can be placed.
     */
    protected Cell cellData[][];

    /**
     * Override this and call in constructor
     */
    protected void allocateCellData() {
        cellData = new Cell[height][width];
    }

    /**
     * Creates a new {@code Board} with the given height and width.
     *
     * Contract: Subclasses must call {@code allocateCellData} so that
     * a NullPointerException isn't thrown on accessing the board's
     * data.
     *
     * @param cellBasedHeight
     * @param cellBasedWidth
     */
    public Board(int cellBasedHeight, int cellBasedWidth) {
        setHeight(cellBasedHeight);
        setWidth(cellBasedWidth);
        // call allocateCellData() in subclasse constructor
    }

    public int getHeight() {
        return height;
    }

    protected final void setHeight(int cellBasedHeight) {
        height = cellBasedHeight;
    }

    public int getWidth() {
        return width;
    }

    protected final void setWidth(int cellBasedWidth) {
        width = cellBasedWidth;
    }

    public Cell getCell(int rowOffset, int columnOffset) {
        return cellData[rowOffset][columnOffset];
    }

    public Cell getCell(int inMemoryOffset) {
        final int rowOffset = inMemoryOffset / width;
        final int columnOffset = inMemoryOffset % width;
        return getCell(rowOffset, columnOffset);
    }

    public Cell getCell(Loc2D loc) {
        return getCell(loc.row(), loc.column());
    }

    public void setCell(Cell cell, int rowOffset, int columnOffset) {
        cellData[rowOffset][columnOffset] = cell;
    }

    public void setCell(Cell cell, int inMemoryOffset) {
        final int rowOffset = inMemoryOffset / width;
        final int columnOffset = inMemoryOffset % width;
        setCell(cell, rowOffset, columnOffset);
    }

    /**
     * Handles the input given to the game by a player on its turn, and
     * relects it spatially, if it conforms to the rules applied on the
     * game for which this board is configured.
     *
     * @param directMove input given to game
     * @return whether the move was valid
     */
    public abstract boolean handle(Move directMove);

    // ----------------- Cell-based utility method ------------------

    /**
     * Calculates the net-distance value for the two rectangular coordinates
     * given - (r1, c1) and (r2, c2). This value is not the regular
     * pythagorean distance, but the distance of path when going only in the
     * horizontal or vertical direction.
     *
     * @param r1 row of first location
     * @param c1 column of first location
     * @param r2 row of second location
     * @param c2 columns of second location
     * @return rectangular distance b/w the two locations
     */
    public static int netDistance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

    /**
     * Tells whether the two cells at (r1, c1) and (r2, c2) are adjacent,
     * and share a side or corner (if treated as squares).
     *
     * @param r1 row of first cell
     * @param c1 columns of first cell
     * @param r2 row of second cell
     * @param c2 columns of second cell
     * @return whether the two cells are adjacent
     */
    public static boolean areAdjacent(int r1, int c1, int r2, int c2) {
        final int netDist = netDistance(r1, c1, r2, c2);

        if(netDist == 1) {
            return true;
        } else if(netDist == 2 && (r1 != r2 && c1 != c2)) {
            return true;
        }

        return false;
    }

    /**
     * Tells whether the cell at (row, col) is located at the very edge of
     * the rectangular board of size (width x height).
     *
     * @param row row of cell
     * @param col column of cell
     * @param width width of board
     * @param height height of board
     * @return whether the cell is at the edge
     */
    public static boolean isEdgeCell(int row, int col, int width, int height) {
        if(row == 0 || col == 0 || row == (height - 1)
                || col == (width - 1)) {
            return true;
        }
        return false;
    }

    /**
     * Tells whether the cell at (row, col) is located at the very corner
     * of the rectangular board of size (width x height).
     *
     * @param row row of cell
     * @param col column of cell
     * @param width width of board
     * @param height height of board
     * @return whether the cell is at the corner
     */
    public static boolean isCornerCell(int row, int col, int width, int height) {
        final boolean isRowCornerCompat = (row == 0) || (row == height - 1);
        final boolean isColCornerCompat = (col == 0) || (col == width - 1);
        return isRowCornerCompat & isColCornerCompat;
    }

    /**
     * Tells whether the cell at (row, col) is located at the very edge, but
     * not at a corner, of the board of size (width x height).
     *
     * @param row row of cell
     * @param col column of cell
     * @param width width of board
     * @param height height of board
     * @return whether the cell is at an edge but not at corner
     */
    public static boolean isNotCornerButEdgeCell(int row, int col, int width, int height) {
        final boolean isRowCornerCompat = (row == 0) || (row == height - 1);
        final boolean isColCornerCompat = (col == 0) || (col == width - 1);
        return (isRowCornerCompat ^ isColCornerCompat);
    }

}
