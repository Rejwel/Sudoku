package view;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sudoku.Repository;
import sudoku.difficulty.Level;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class Controller {

    private Repository repo = new Repository(new BacktrackingSudokuSolver());
    private SudokuBoard board;

    public Controller() throws CloneNotSupportedException {
        board = repo.createSudokuBoard();
    }

    @FXML
    private GridPane gameBoard;

    @FXML
    private void setEasyDifficulty() throws IOException {
        board.solveGame();
        Level.EASY.setDifficulty(board);
        startGame();
    }

    @FXML
    private void setMediumDifficulty() throws IOException {
        board.solveGame();
        Level.MEDIUM.setDifficulty(board);
        startGame();
    }

    @FXML
    private void setHardDifficulty() throws IOException {
        board.solveGame();
        Level.HARD.setDifficulty(board);
        startGame();
    }

    @FXML
    private void start() throws IOException {

        Parent part = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/Levels.fxml")));

        Stage stage = new Stage();
        Scene scene = new Scene(part);
        stage.setScene(scene);
        stage.setTitle("Wybierz poziom");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startGame() throws IOException {
        Parent part = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/Game.fxml")));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Gra Sudoku");
        Scene scene = new Scene(part);
        stage.setScene(scene);

        gameBoard = (GridPane) part.lookup("#gameBoard");
        gameBoard.setAlignment(Pos.CENTER);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameBoard.add(new Label(String.valueOf(board.get(i, j))), i, j);
            }
        }

        stage.show();
    }

    @FXML
    private void wyjdz() {
        System.exit(0);
    }
}
