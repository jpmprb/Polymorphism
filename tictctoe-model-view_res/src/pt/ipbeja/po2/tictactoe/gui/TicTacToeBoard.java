package pt.ipbeja.po2.tictactoe.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipbeja.po2.tictactoe.model.GameModel;
import pt.ipbeja.po2.tictactoe.model.Position;
import pt.ipbeja.po2.tictactoe.model.View;

import java.util.List;

/**
 * GUI interface for TicTacToe
 * @author João Paulo Barros
 * @version 2021/03/09
 */

public class TicTacToeBoard extends VBox
                            implements View {

    private static final int SIZE = 3;
    private GameModel gameModel;

    private Label counterLabel;
    private Button exitButton; // the button is a JavaFX button: javafx.scene.control.Button

    private BoardButton[][] buttons;

    public TicTacToeBoard()
    {
        this.gameModel =  new GameModel( this );

        this.counterLabel = new Label();

        this.exitButton = new Button("Exit");
        this.exitButton.setOnMouseClicked(
                event -> {
                    this.gameModel.exit();
                }
        );

        this.buttons = new BoardButton[SIZE][SIZE];

        GridPane board = this.createBoard();
        this.getChildren().addAll(counterLabel, board, exitButton);
    }

    private GridPane createBoard() {
        GridPane pane = new GridPane();
        ButtonHandler bHandler = new ButtonHandler();
        for (int line = 0; line < SIZE; line++) {
            for (int col = 0; col < SIZE; col++) {
                BoardButton button = new BoardButton(line + ", " + col, line, col);
                button.setOnAction( bHandler ) ;
                pane.add(button, col, line); // add grid, estava this porque antes era um grid
                buttons[line][col] = button;
            }
        }
        return pane;
    }

    @Override
    public void disablePosition(int line, int col) {
        buttons[line][col].setDisable(true);
    }

    @Override
    public void updateCounter(int newCounterValue) {
        this.counterLabel.setText("" + newCounterValue);
    }

    @Override
    public void selectedPositions(List<Position> positions) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TicTacToe");
        alert.setHeaderText("Selected positions");

        String s = "";
        for(Position pos : positions)
            s += pos.toString();  // ou apenas s += pos;
        alert.setContentText(s);

        alert.showAndWait();
    }

    @Override
    public void allLineSelected(int line) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TicTacToe");
        alert.setHeaderText("Whole line selected");
        alert.setContentText("Line " + line + " selected");
        alert.showAndWait();
    }

    class ButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            BoardButton button = (BoardButton)event.getSource();
            int line = button.getLine();
            int col = button.getCol();
            gameModel.selectedPosition(line, col);
        }
    }
}
