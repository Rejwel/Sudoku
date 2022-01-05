package view;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sudoku.Repository;
import sudoku.difficulty.Level;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;


public class Controller {

    private Repository repo = new Repository(new BacktrackingSudokuSolver());

    private static int iteration = 0;
    private static boolean creatingNewSudokuBoard = false;
    private static Level level;
    private SudokuBoard board;
    private static SudokuBoard boardOriginal;

    public Controller() throws CloneNotSupportedException {
        board = repo.createSudokuBoard();
        if (iteration == 0) {
            bundle = ResourceBundle.getBundle("bundles.basic");
            creatingNewSudokuBoard = false;
            iteration++;
        }
    }

    @FXML
    private void setEasyDifficulty(ActionEvent event) throws IOException,
            CloneNotSupportedException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        level = Level.EASY;
        board.solveGame();
        level.removeFieldsFromBoard(board);
        startGame();
        boardOriginal = board.clone();
        GameController.setCreatingNewSudokuBoard(true);
    }

    @FXML
    private void setMediumDifficulty(ActionEvent event) throws IOException,
            CloneNotSupportedException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        level = Level.MEDIUM;
        board.solveGame();
        level.removeFieldsFromBoard(board);
        startGame();
        boardOriginal = board.clone();
        GameController.setCreatingNewSudokuBoard(true);
    }

    @FXML
    private void setHardDifficulty(ActionEvent event) throws IOException,
            CloneNotSupportedException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        level = Level.HARD;
        board.solveGame();
        level.removeFieldsFromBoard(board);
        startGame();
        boardOriginal = board.clone();
        GameController.setCreatingNewSudokuBoard(true);
    }

    @FXML
    private void start(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();

        FXMLLoader part = new FXMLLoader(Objects.requireNonNull(getClass()
                .getResource("/Levels.fxml")));
        part.setResources(bundle);
        Pane borderPane = part.load();
        Stage stage = new Stage();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("title.application"));
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setResizable(false);
        stage.show();
    }

    private void startGame() throws IOException {
        FXMLLoader part = new FXMLLoader(Objects.requireNonNull(getClass()
                .getResource("/Game.fxml")));

        part.setResources(bundle);

        Stage stage = new Stage();
        Pane borderPane = part.load();
        stage.setTitle(bundle.getString("title.application"));
        stage.setHeight(600);
        stage.setWidth(600);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        gameBoard = (VBox) borderPane.lookup("#gameBoard");
        for (int i = 0; i < 9; i++) {
            HBox wiersz = (HBox)gameBoard.getChildren().get(i);
            for (int j = 0; j < 9; j++) {
                TextField text = (TextField) wiersz.getChildren().get(j);

                text.setMaxWidth(44);
                text.setMaxHeight(46);
                text.setAlignment(Pos.CENTER);
                text.lengthProperty();
                if (board.get(i, j) != 0) {
                    text.setDisable(true);
                }
                text.textProperty().addListener(this::fieldListener);

            }
        }

        stage.show();
        GameController.bind(gameBoard,board);
        GameController.setBoard(board);
    }

    private void fieldListener(ObservableValue<? extends String> observableValue,
                               String s, String t1) {
        StringProperty text = (StringProperty) observableValue;
        if (t1.length() == 0) {
            return;
        }
        if (t1.length() > 1) {
            t1 = t1.substring(0, 1);
        }
        if (!(t1.charAt(0) >= 49 && t1.charAt(0) <= 57)) {
            t1 = "";
            text.setValue(t1);
        } else {
            text.setValue(t1);
        }
    }

    @FXML
    private Button save;

    @FXML
    private void wyjdz() {
        System.exit(0);
    }

    @FXML
    private VBox gameBoard;

    @FXML
    private Label languageLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label author1;
    @FXML
    private Label author2;
    @FXML
    private Label authorTitle;
    @FXML
    private Button start;
    @FXML
    private Button exit;
    private static Locale locale;
    private static ResourceBundle bundle;
    private static String language;
    private static ResourceBundle bundleList;

    @FXML
    private void english(ActionEvent event) {
        Node node = (Node)event.getSource();
        language = "en";
        loadLanguage(language,node);
    }

    @FXML
    private void polish(ActionEvent event) {
        Node node = (Node)event.getSource();
        language = "pl";
        loadLanguage(language,node);
    }

    private void loadLanguage(String lang,Node node) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("bundles.basic",locale);
        languageLabel.setText(bundle.getString("changeTolanguage"));
        welcomeLabel.setText(bundle.getString("welcomeToGame"));
        start.setText(bundle.getString("start"));
        exit.setText(bundle.getString("exit"));
        authorTitle.setText(bundle.getString("authors"));
        bundleList = ResourceBundle.getBundle("view.listbundle.Authors",locale);
        author1.setText(bundleList.getString("author1"));
        author2.setText(bundleList.getString("author2"));
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle(bundle.getString("title.application"));
    }

    public static SudokuBoard getBoardOriginal() throws CloneNotSupportedException {
        return boardOriginal.clone();
    }

    public static boolean isCreatingNewSudokuBoard() {
        return creatingNewSudokuBoard;
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

}
