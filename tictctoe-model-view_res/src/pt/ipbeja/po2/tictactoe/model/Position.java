package pt.ipbeja.po2.tictactoe.model;


/**
 * Simple data object to store position data
 * @author João Paulo Barros
 * @version 2021/03/09
 */
public class Position {
    private int line;
    private int col;

    public Position(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return this.line;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public String toString() {
        return "(" + line + ", " + col + ")";
    }
}
