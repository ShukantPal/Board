package com.silcos.board;

/**
 * A cell is the spatial unit of the {@link Board}. Each cell can hold
 * pieces at any moment in time.
 *
 * NOTE: Currently Cell only supports one holder.
 */
public interface Cell {

    public Piece getHolder();

    public void setHolder(Piece o);

}
