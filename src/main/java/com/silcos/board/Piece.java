package com.silcos.board;

public class Piece {

    public static final int DEFAULT_STATUS = 0;

    private int mId;
    private int mPlayerId;
    private int mStatus;

    public Piece(int id, int playerId) {
        mId = id;
        mPlayerId = playerId;
        mStatus = DEFAULT_STATUS;
    }

    public int id() {
        return mId;
    }

    public int playerId() {
        return mPlayerId;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }
}
