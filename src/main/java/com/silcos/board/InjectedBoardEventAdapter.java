package com.silcos.board;

/**
 * {@code InjectedBoardEventAdapter} takes the {@link AbstractBoardEventAdapter}
 * concept one step further by allowing an external {@link BoardEventAdapter}
 * to be injected to decide the behaviour of this adapter. This is useful
 * if you reuse an existing application component as a BoardEventAdapter but
 * cannot inherit from AbstractBoardEventAdapter.
 *
 * InjectedBoardEventAdapter is a non-mutable object and can be reused
 * anywhere for the same BoardEventAdapter.
 */
public class InjectedBoardEventAdapter extends AbstractBoardEventAdapter {

    private final BoardEventAdapter injectedAdapter;

    public InjectedBoardEventAdapter(BoardEventAdapter injectedAdapter) {
        this.injectedAdapter = injectedAdapter;
    }

    @Override
    public void onPlayerWire(Event e) {
       injectedAdapter.onPlayerWire(e);
    }

    @Override
    public void onMove(Event e) {
       injectedAdapter.onMove(e);
    }

    @Override
    public void onFinish(Event e) {
        injectedAdapter.onFinish(e);
    }

    @Override
    public void onTimerTick(Event e) {
        injectedAdapter.onTimerTick(e);
    }

    public void onElimination(Event e) {
        injectedAdapter.onElimination(e);
    }

    @Override
    public void onOther(Event e) {
        injectedAdapter.onOther(e);
    }
}
