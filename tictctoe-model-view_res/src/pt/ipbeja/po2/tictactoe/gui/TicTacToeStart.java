package pt.ipbeja.po2.tictactoe.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Start GUI
 * @author João Paulo Barros
 * @version 2021/03/09
 */
public class TicTacToeStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TicTacToeBoard board = new TicTacToeBoard();
        Scene scene = new Scene(board);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args)
    {
        Application.launch(args);
    }
}
