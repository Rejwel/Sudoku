package view;

import java.io.IOException;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        Level.EASY.removeFieldsFromBoard(board);
        startGame();
    }

    @FXML
    private void setMediumDifficulty() throws IOException {
        board.solveGame();
        Level.MEDIUM.removeFieldsFromBoard(board);
        startGame();
    }

    @FXML
    private void setHardDifficulty() throws IOException {
        board.solveGame();
        Level.HARD.removeFieldsFromBoard(board);
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
        //        stage.setResizable(false);
        stage.setTitle("Gra Sudoku");
        Scene scene = new Scene(part);
        stage.setScene(scene);

        gameBoard = (GridPane) part.lookup("#gameBoard");
        gameBoard.setAlignment(Pos.CENTER);


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField text = new TextField(String.valueOf(board.get(i, j)));
                text.setMaxWidth(44);
                text.setMaxHeight(46);
                text.setAlignment(Pos.CENTER);
                text.lengthProperty();
                text.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        String string = text.getText();
                        if (string.length() == 0 || t1.length() == 0) {
                            return;
                        } else if (string.length() > 1) {
                            text.setText(string.substring(0, 1));
                            text.positionCaret(string.length());
                        }
                        if (!(text.getText().charAt(0) >= 49 && text.getText().charAt(0) <= 57)) {
                            text.setText("");
                            text.positionCaret(string.length());
                        }
                    }
                });
                System.out.println(text.getText());

                if (board.get(i, j) != 0) {
                    text.setDisable(true);
                } else {
                    text.setText(String.valueOf(""));
                }
                gameBoard.add(text, i, j);
            }
        }

        stage.show();
    }

    @FXML
    private void wyjdz() {
        System.exit(0);
    }
}
