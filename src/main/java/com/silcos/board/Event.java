package com.silcos.board;

/**
 * Events track changes occuring during gameplay. Each event object
 * is created with an unique identifier, and also have a specific
 * type, e.g. piece-move event would have the BoardGame.PIECE_MOVE_EVENT
 * typeId().
 *
 * The id() function will always return a unique id for the event of
 * the given type, unless id() == 0, which means the event is special
 * and shouldn't be recorded (like PLAYER_WIRE_EVENT).
 */
public abstract class Event {

    public final int mId;
    public final BoardGame mOrigin;

    protected Event(final int id, final BoardGame origin) {
        mId = id;
        mOrigin = origin;
    }

    public Event(final BoardGame origin) {
        mId = 0;
        mOrigin = origin;
    }

    public final int id() {
        return mId;
    }

    public abstract int typeId();

    public BoardGame getOrigin() {
        return mOrigin;
    }

}
