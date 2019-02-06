package com.silcos.board;

/**
 * {@code CompatLoc2D} stores the two co-ordinates of the Loc2D
 * object as two-ints in one long (hence the name "compact"). It
 * offers both object-based encoding and static encoding (via the
 * {@code encode} method).
 */
public class CompactLoc2D implements Loc2D {

    private final long columnRow;

    public CompactLoc2D(int row, int column) {
        columnRow = ((long) column << 32) | (long) row;
    }

    public CompactLoc2D(long p) { columnRow = p; }

    @Override
    public int row() {
        return (int) columnRow;
    }

    @Override
    public int column() {
        return (int) (columnRow >> 32);
    }

    public long val() {return columnRow;}

    public static long encode(int row, int column) {
        return ((long) column << 32) | (long) row;
    }

    public static int decodeRow(long encodedCoordinates) {
        return (int) encodedCoordinates;
    }

    public static int decodeColumn(long encodedCoordinates) {
        return (int) (encodedCoordinates >> 32);
    }

}
