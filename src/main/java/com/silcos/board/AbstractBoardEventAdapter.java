package com.silcos.board;

import static com.silcos.board.BoardGame.ELIMINATION_EVENT;
import static com.silcos.board.BoardGame.GAME_FINISH_EVENT;
import static com.silcos.board.BoardGame.PIECE_MOVE_EVENT;
import static com.silcos.board.BoardGame.PLAYER_WIRE_EVENT;
import static com.silcos.board.BoardGame.TIMER_TICK_EVENT;

/**
 * BoardEventAdapter that can be inherited so that it can be used
 * as an {@link BoardEventListener} and the relevant event-handling
 * methods are called, whenever an event is recieved.
 *
 * As new APIs are developed, the {@code AbstractBoardEventAdapter}
 * class will be updated to resolve more types of events.
 */
public class AbstractBoardEventAdapter implements BoardEventListener, BoardEventAdapter {

    /**
     * Handles all player-wire events that are received by this event
     * listener.
     *
     * @param e - PLAYER_WIRE_EVENT event object
     */
    public void onPlayerWire(Event e) {}

    /**
     * Handles all piece-moving events that are received by this event
     * listener.
     *
     * @param e - PIECE_MOVE_EVENT event object
     */
    public void onMove(Event e) {}

    /**
     * Handles the finish event, when received by this event listener.
     *
     * @param e - GAME_FINISH_EVENT event object
     */
    public void onFinish(Event e) {}

    /**
     * Handles the timer-tick events that are received by this event
     * listener.
     *
     * @param e - TIMER_TICK_EVENT event object
     */
    public void onTimerTick(Event e) {}

    /**
     * Handles all player-elimination events that received by this
     * event listener. Note that a finish event will not be issued if
     * all player but one are eliminated, even though the game does
     * terminate at that point.
     *
     * @param e - ELIMINATION_EVENT event object
     */
    public void onElimination(Event e) {}

    /**
     * Handles all other events that have not been specified in this
     * class. This is particularly useful if your application defines
     * its own type of event, that this listener will respond to.
     *
     * @param e - unknown event object
     */
    public void onOther(Event e) {}

    @Override
    public void handleEvent(Event e) {
        switch(e.typeId()) {
            case PLAYER_WIRE_EVENT:
                onPlayerWire(e);
                break;
            case PIECE_MOVE_EVENT:
                onMove(e);
                break;
            case GAME_FINISH_EVENT:
                onFinish(e);
                break;
            case TIMER_TICK_EVENT:
                onTimerTick(e);
                break;
            case ELIMINATION_EVENT:
                onElimination(e);
                break;
            default:
                onOther(e);
                break;
        }
    }
}
