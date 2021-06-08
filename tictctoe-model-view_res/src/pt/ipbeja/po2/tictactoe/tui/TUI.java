package pt.ipbeja.po2.tictactoe.tui;

import pt.ipbeja.po2.tictactoe.model.GameModel;
import pt.ipbeja.po2.tictactoe.model.Position;
import pt.ipbeja.po2.tictactoe.model.View;

import java.util.List;
import java.util.Scanner;

/**
 * Simple text user interface
 * End condition is missing!
 * @author João Paulo Barros
 * @version 2021/03/09
 */

public class TUI implements View {

    private final GameModel gameModel;

    public TUI() {
        this.gameModel = new GameModel(this);
    }

    @Override
    public void disablePosition(int line, int col) {
        System.out.println("Disable position (" +
                line + "), (" + col + ")");
    }

    @Override
    public void updateCounter(int newCounterValue) {
        System.out.println("number of selected positions: " + newCounterValue);
    }

    @Override
    public void selectedPositions(List<Position> positions) {
        String s = "";
        for (Position pos : positions) s += pos;
        System.out.println(s);
    }

    @Override
    public void allLineSelected(int line) {
        System.out.println("All line " + line + " is selected");
    }

    private void gameLoop() {
        for(;;) {
            Position position = readInput();
            this.gameModel.selectedPosition(position.getLine(), position.getCol());
        }
    }

    private Position readInput() {
        System.out.println("Specify line and col");
        Scanner s = new Scanner(System.in);
        int line = s.nextInt();
        int col = s.nextInt();
        return new Position(line, col);
    }

    public static void main(String[] args) {
        TUI tui = new TUI();
        tui.gameLoop();
    }
}
