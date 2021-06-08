package pt.ipbeja.po2.tictactoe.model;

import java.util.List;

/**
 * Requests from model to user interface
 * @author João Paulo Barros
 * @version 2021/03/09
 */
public interface View {

    void disablePosition(int line, int col);
    void updateCounter(int newCounterValue);
    void selectedPositions(List<Position> positions);
    void allLineSelected(int line);
}

