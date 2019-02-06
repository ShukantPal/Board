package com.silcos.board;

/**
 * Objects that implement {@code BoardEventAdapter} show the ability
 * to handle all sorts of events explicity, and can be used for
 * "injection" into board event-listeners, like for example
 * {@link InjectedBoardEventAdapter}.
 *
 * As new APIs are developed, additional methods will be added and
 * hence, you may be required to move code from [@code onOther}.
 */
public interface BoardEventAdapter {

    public void onPlayerWire(Event e);

    public void onMove(Event e);

    public void onFinish(Event e);

    public void onTimerTick(Event e);

    public void onElimination(Event e);

    public void onOther(Event e);
}
