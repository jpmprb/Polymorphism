package pt.ipbeja.po2.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TicTacToe model
 * @author João Paulo Barros
 * @version 2021/03/09
 */
public class GameModel {

    private View view;
    private List<Position> positions;
    private boolean[][] grid; // in the real game should NOT be boolean as we will need three values: empty, X and O

    public GameModel(View board)
    {
        this.view = board;
        this.positions = new ArrayList<>();
        this.grid = new boolean[3][3];
    }

    public void selectedPosition(int line, int col) {
        this.grid[line][col] = true;
        this.positions.add(new Position(line, col));
        view.disablePosition(line, col);
        view.updateCounter(this.positions.size());
        if(this.allLineSelected(line))
        {
            this.view.allLineSelected(line);
        }
    }

    private boolean allLineSelected(int line) {
        return this.grid[line][0] && this.grid[line][1] && this.grid[line][2];
    }

    public void exit() {
        this.view.selectedPositions(this.positions);
        System.exit(0);
    }
}




