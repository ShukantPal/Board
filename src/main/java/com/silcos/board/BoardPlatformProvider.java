package com.silcos.board;

/**
 * BoardPlatformProvider allows the application using the Board library to
 * ultimately decide this library will use its platform - to use multiple
 * threads, draw on the UI, and handle events, etc.
 */
public abstract class BoardPlatformProvider {

    public static String COMPUTER_TASK = "BoardComputerTask";
    public static String GAME_EVENT_TASK = "BoardEventsTask";

    public abstract void runComputeIntensiveTask(String taskName, Runnable taskExec);

    public abstract void runComputeIntensiveTaskAfter(int ms, String taskName, Runnable taskExec);

}
