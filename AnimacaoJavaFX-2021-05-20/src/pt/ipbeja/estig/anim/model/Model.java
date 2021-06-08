package pt.ipbeja.estig.anim.model;

import pt.ipbeja.estig.anim.gui.View;

import java.util.*;

/**
 * The fifteen puzzle model
 *
 * @author João Paulo Barros
 * @version 2014/05/19 - 2016/04/03 - 2017/04/19 - 2019/05/06 - 2021/05/18 - 2021/05/21
 */
public class Model {
    public static final int N_LINES = 4;
    public static final int N_COLS = 4;

    private final static Random RAND = new Random();

    private int[][] pieces;


    private View view;

    /**
     * Creates board in winning position
     */
    public Model(View view) {
        this.resetBoard();
        this.view = view;
    }

    /**
     * Creates a random mixed board starting from a winning position
     *
     * @param minIter        minimum number of iterations to mix board
     * @param additionalIter maximum number of additional iterations to mix board
     */
    public Model(View view, int minIter, int additionalIter) {
        this(view); // call default constructor Fifteen()
        this.mix(minIter, additionalIter);
        this.resetTimer();
        this.startTimer();
    }

    /**
     * Puts the board in the winning position (numbers in sequence)
     */
    private void resetBoard() {
        this.pieces = new int[Model.N_LINES][Model.N_COLS];
        int pieceNumber = 1;
        for (int line = 0; line < Model.N_LINES; line++) {
            for (int col = 0; col < Model.N_COLS; col++) {
                this.pieces[line][col] = pieceNumber++;
            }
        }
    }

    /**
     * @return fifteen board content in text form
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int line = 0; line < N_LINES; line++) {
            for (int col = 0; col < N_COLS; col++) {
                s.append(String.format("%2d, ", this.pieces[line][col]));
            }
            s.setLength(s.length() - 2);
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * get piece at given position
     *
     * @param position to get piece
     * @return the piece at position
     */
    public int pieceAt(Position position) {
        return this.pieces[position.getLine()][position.getCol()];
    }




    public void keyPressed(Direction direction) {
        Position pos = getPositionNextToEmpty(direction);
        this.movePieceAt(line, col);
    }

    private Position getPositionNextToEmpty(Direction direction) {
        switch (direction) {
            case UP: return new Position(emptyPosition.getLine() + 1, emptyPosition.getCol());
            case DOWN: return new Position(emptyPosition.getLine() - 1, emptyPosition.getCol());
            case LEFT: return new Position(emptyPosition.getLine(), emptyPosition.getCol() + 1) ;
            case RIGHT: return new Position(emptyPosition.getLine(), emptyPosition.getCol() - 1);
        }
        return null; // should never happen! Added to avoid compilation error
    }

    /**
     * Tries to move a piece at position If moved notifies views
     *
     * @param position position of piece to move
     */
    private void movePieceAt(Position position) {
        if (position.isInside()) {
            Position emptyPos = this.getEmptyInNeighborhood(position);
            if (emptyPos != null) {
                Move newMove = new Move(position, emptyPos);
                this.applyMove(newMove);
                this.moves.addFirst(newMove); // add at head (begin) of deque
                boolean winning = inWinningPositions();
                this.notifyViews(newMove, winning, timerValue);
                if (winning) {
                    timerValue = 0;
                    timer.cancel();
                }
            }
        }
    }

    /**
     * Notify observers using methods inherited from from class Observable
     *
     * @param move    the executed move
     * @param winning true if this is a winning position
     * @param tValue  current time count
     */
    private void notifyViews(Move move, Boolean winning, int tValue) {

        this.view.notifyView(move, winning, tValue);
    }

    /**
     * Gets last executed move
     *
     * @return the last move
     */
    public Move getLastMove() {
        return this.moves.getFirst();
    }

    /**
     * Checks if board as all pieces in winning positions
     *
     * @return true if winning positions, false otherwise
     */
    public boolean inWinningPositions() {
        int n = 1;
        final int TOTAL = N_LINES * N_COLS;
        for (int line = 0; line < N_LINES; line++) {
            for (int col = 0; col < N_COLS; col++) {
                if (this.pieces[line][col] != n && n < TOTAL) {
                    return false;
                }
                n++;
            }
        }
        return true;
    }

    /**
     * Applies move m to the board assert(move != null)
     *
     * @param move the move to apply
     */
    private void applyMove(Move move) {
        assert (move != null);
        this.swap(move);
    }

    private void swap(Move move) {
        assert (move.getEnd().equals(emptyPosition));

        this.swap(move.getBegin(), move.getEnd());
        this.emptyPosition = move.getBegin();
    }

    private void swap(Position pInit, Position pEnd) {
        int posXi = pInit.getLine();
        int posYi = pInit.getCol();
        int posXe = pEnd.getLine();
        int posYe = pEnd.getCol();
        this.swap(posXi, posYi, posXe, posYe);
    }

    private void swap(int posXi, int posYi, int posXe, int posYe) {
        int aux = this.pieces[posXe][posYe];
        this.pieces[posXe][posYe] = this.pieces[posXi][posYi];
        this.pieces[posXi][posYi] = aux;
    }

    /**
     * Randomly selects position that can be moved to the empty position
     *
     * @param empty the empty position
     * @return the selected neighbor position
     */
    private Position randomlySelectNeighborOf(Position empty) {
        int line = 0;
        int col = 0;
        do {
            int[] pos = NEIGHBORS[RAND.nextInt(NEIGHBORS.length)];
            line = empty.getLine() + pos[0];
            col = empty.getCol() + pos[1];
        } while (Position.isInside(line, col) == false);
        return new Position(line, col);
    }

    /**
     * Tries to return the empty position in the neighborhood of line col
     *
     * @param center position to find empty in its neighborhood
     * @return the empty position or null if non-existent in the neighborhood
     */
    private Position getEmptyInNeighborhood(Position center) {
        boolean isNeighbor = Math.abs(center.getLine() - emptyPosition.getLine()) == 1 ^
                Math.abs(center.getCol() - emptyPosition.getCol()) == 1;
        return isNeighbor ? emptyPosition : null;
    }

    /**
     * Wait the specified time in milliseconds
     *
     * @param sleepTime time in miliseconds
     */
    public static void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            // nothing to do
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model that = (Model) o;
        return timerValue == that.timerValue &&
                Arrays.equals(pieces, that.pieces) &&
                Objects.equals(emptyPosition, that.emptyPosition) &&
                Objects.equals(moves, that.moves) &&
                Objects.equals(timer, that.timer) &&
                Objects.equals(view, that.view);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(emptyPosition, moves, timer, timerValue, view);
        result = 31 * result + Arrays.hashCode(pieces);
        return result;
    }

    /**
     * Creates a new timer and sets the timer count to zero
     */
    public void resetTimer() {
        this.timerValue = -1;
        this.timer = new Timer();
    }

    /**
     * Starts timer
     */
    public void startTimer() {
        this.resetTimer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timerValue++;
                notifyViews(null, false, timerValue);
            }
        };
        this.timer.schedule(timerTask, 0, 1000);
    }

    /**
     * Stops the current timer
     */
    public void stopTimer() {
        timer.cancel();
    }

    /**
     * Get current timer value
     *
     * @return time in seconds
     */
    public int getTimerValue() {
        return this.timerValue;
    }

}
