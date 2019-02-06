package com.silcos.board;

import java.util.ArrayList;

public class BoardGame {

    public class GameInputController {

        public PlayerRotator playerRotator;

        protected ArrayList<Move> history = new ArrayList<>();

        protected void onPlayerLostAllPieces(int loserId) {

        }

        public boolean placeMove(Move yourMove) {
            if (isDead())
                return false;

            final Object srcHolder = mBoard.getCell(yourMove.sourceRow(),
                    yourMove.sourceColumn()).getHolder();
            final Object dstHolder = mBoard.getCell(yourMove.destinationRow(),
                    yourMove.destinationColumn()).getHolder();

            if(!mBoard.handle(yourMove))
                return false;

            history.add(yourMove);

            dispatchEvent(new MoveEvent(BoardGame.this, yourMove.sourceRow(),
                    yourMove.sourceColumn(),
                    yourMove.destinationRow(),
                    yourMove.destinationColumn(),
                    srcHolder,
                    dstHolder));

            playerRotator.nextPlayer().onTurn();

            if (timer != null)
                timer.switchTo(playerRotator.getCurrentId());

            return true;
        }

        public void acceptResignation(int winnerId) {
            dispatchEvent(new FinishEvent(BoardGame.this, winnerId));
        }
    }

    private static int eventCount = 0;
    protected boolean isDead = false;

    public static final int PIECE_MOVE_EVENT = 35332;
    public static final int GAME_FINISH_EVENT = 3424;
    public static final int PLAYER_WIRE_EVENT = 24242;
    public static final int TIMER_TICK_EVENT = 242111;
    public static final int ELIMINATION_EVENT = 13553;

    protected Player[] mPlayers;
    protected PlayerStats[] mPlayerStats;

    protected Board mBoard;
    private ArrayList<BoardEventListener> mBoardEventListeners;

    protected GameInputController mController;

    private final PlayerRotatorFactory mRotatorFactory;
    protected final BoardPlatformProvider platformProvider;

    protected void renewInputController() {
       mController.playerRotator = mRotatorFactory.newRotator(mPlayers);
    }

    protected BoardGame(PlayerRotatorFactory rotatorFactory, int playerCount) {
        mPlayers = new Player[playerCount];
        mPlayerStats = new PlayerStats[playerCount];
        mController = new GameInputController();
        mRotatorFactory = rotatorFactory;
        renewInputController();
        platformProvider = getDefaultProvider();
    }

    /**
     * Override this to return your own implementation of BoardController
     * by casting mBoard.
     *
     * @return BoardController holding the game state
     */
    protected Board board() {
        return mBoard;
    }

    public int getMoveCount() {
        return mController.history.size();
    }

    public Move getMove(int index) {
        return mController.history.get(index);
    }

    PlayerRotatorFactory getRotatorFactory() {
        return mRotatorFactory;
    }

    public Player getPlayer(int index) {
        return mPlayers[index];
    }

    public void setPlayer(Player player, int index) {
        mPlayers[index] = player;
        mPlayerStats[index] = new PlayerStats(player);
    }

    protected PlayerStats getPlayerStat(int playerId) {
        return mPlayerStats[playerId];
    }

    public Piece pieceAt(int row, int col) {
        return (Piece) mBoard.getCell(row, col).getHolder();
    }

    public BoardPlatformProvider getPlatformProvider() {
        return platformProvider;
    }

    public void addBoardEventListener(BoardEventListener listener) {
        if(mBoardEventListeners == null) {
            mBoardEventListeners = new ArrayList<>(2);
        }

        mBoardEventListeners.add(listener);
    }

    public void removeBoardEventListener(BoardEventListener listener) {
        mBoardEventListeners.remove(listener);
    }

    public boolean isDead() {
        return isDead;
    }

    public void dispatchEvent(Event e) {
        if (e.typeId() == GAME_FINISH_EVENT)
            isDead = true;
        for(BoardEventListener listener : mBoardEventListeners) {
            listener.handleEvent(e);
        }
    }

    protected void dispatchPlayerWiringEvent() {
        dispatchEvent(new Event(this) {
            @Override
            public int typeId() {
                return PLAYER_WIRE_EVENT;
            }
        });
    }
    private static BoardPlatformProvider defaultProvider;

    public static BoardPlatformProvider getDefaultProvider() {
        return defaultProvider;
    }

    public static void setDefaultProvider(BoardPlatformProvider newDefaultProvider) {
        defaultProvider = newDefaultProvider;
    }

    protected  Timer timer;

    public Timer attachTimer(Timer t) {
        timer = t;
        return timer;
    }

    public void stopGame() {
        if (timer != null) {
            timer.pause();
            timer = null;
        }
        isDead = true;
    }

    public Timer getTimer() {
        return timer;
    }

    protected class PlayerStats {

        protected int mLivePieces;
        protected ArrayList<Piece> mKilledPieces;

        public PlayerStats(Player forPlayer) {
            mLivePieces = forPlayer.myPieces.length;
            mKilledPieces = new ArrayList<>();
        }

        public void onKilled(Piece killedPiece) {
            --mLivePieces;
            mKilledPieces.add(killedPiece);
        }

        public int livePieces() {
            return mLivePieces;
        }

        public Piece[] killedPieces() {
            return (Piece[]) mKilledPieces.toArray();
        }

    }

}
