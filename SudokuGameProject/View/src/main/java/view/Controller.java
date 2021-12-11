package view;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;


public class Controller {

    @FXML
    private void start() throws IOException {

        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);

        Parent part = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/Levels.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(part);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void wyjdz() {
        System.exit(0);
    }
}
