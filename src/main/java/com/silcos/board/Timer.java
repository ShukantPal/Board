package com.silcos.board;

import java.util.Locale;

import static com.silcos.board.BoardPlatformProvider.GAME_EVENT_TASK;

/**
 * Maintains the amt. of time available for each player to decide on
 * all future turns. Uses the platform-provider to send itself timer
 * alerts.
 */
public class Timer {

    private int dispatchId = 0;
    protected int currentPlayerId;
    protected int frequency;
    protected int playerTimes[];
    protected BoardGame game;
    protected BoardPlatformProvider platformProvider;

    /**
     * Any object can invoke its runnable at a set time, for any given
     * dispatch id.
     */
    private Runnable controllerTask;
    private int timerSet;

    public Timer(int playerCount, int frequency, BoardGame game) {
        this.currentPlayerId = -1;
        this.playerTimes = new int[playerCount];
        this.frequency = frequency;
        this.game = game;
        this.platformProvider = game.getPlatformProvider();
    }

    public Timer resetAll(int newValue) {
        for (int i = 0; i < playerTimes.length; i++) {
            playerTimes[i] = newValue;
        }
        return this;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getPlayerTime(int playerId) {
        return playerTimes[playerId];
    }

    public void switchTo(int playerId) {
        if (playerId != currentPlayerId) {
            ++dispatchId;
            controllerTask = null;
        }
        currentPlayerId = playerId;

        platformProvider.runComputeIntensiveTaskAfter(getFrequency(), GAME_EVENT_TASK,
                new TimerDispatch(dispatchId));
    }

    public void invokeAt(int delay, Runnable action) {
        timerSet = playerTimes[currentPlayerId] - delay;
        controllerTask = action;
    }

    public void pause() {
        ++dispatchId;// all dispatches are cancelled now!
    }

    public void restart() {
        switchTo(currentPlayerId);
    }

    public void start(int firstPlayerId) {
        switchTo(firstPlayerId);
    }

    public static String toHMS(int timeLeftInSecs) {
        final int hr = timeLeftInSecs / 3600;
        final int mi = (timeLeftInSecs % 3600) / 60;
        final int s = (timeLeftInSecs % 60);

        return ((hr == 0) ? "" : String.valueOf(hr) + ":") +
                String.valueOf(mi) + ":" +
                String.format(Locale.US, "%02d", s);
    }

    protected class TimerDispatch implements Runnable {

        private final int id;

        public TimerDispatch(final int id) {
            this.id = id;
        }

        public int id() {
            return id;
        }

        @Override
        public void run() {
            if (dispatchId != this.id) {
                return;
            }

            --playerTimes[currentPlayerId];

            game.dispatchEvent(new TimerTickEvent(dispatchId, currentPlayerId,
                    playerTimes[currentPlayerId], game));

            if (controllerTask != null && timerSet >= playerTimes[currentPlayerId]) {
                controllerTask.run();
                controllerTask = null;
            }

            if (playerTimes[currentPlayerId] != 0) {
                switchTo(currentPlayerId);
            } else {
                controllerTask = null;
                game.dispatchEvent(new EliminationEvent(currentPlayerId, dispatchId, game));
            }
        }
    }

}
