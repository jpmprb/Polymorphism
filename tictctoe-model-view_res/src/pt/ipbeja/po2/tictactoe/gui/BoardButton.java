package pt.ipbeja.po2.tictactoe.gui;

import javafx.scene.control.Button;

/**
 * Button with line and col data
 * @author João Paulo Barros
 * @version 2021/03/09
 */
public class BoardButton extends Button {

    private int line;
    private int col;

    public BoardButton(String text, int line, int col) {
        super(text);
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

}
