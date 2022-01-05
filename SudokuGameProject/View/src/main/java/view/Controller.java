package view;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sudoku.Repository;
import sudoku.StaticFunctions;
import sudoku.difficulty.Level;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class Controller {

    private Repository repo = new Repository(new BacktrackingSudokuSolver());
    private SudokuBoard board;
    private static int iteration = 0;

    public Controller() throws CloneNotSupportedException {
        board = repo.createSudokuBoard();
        if (iteration == 0) {
            bundle = ResourceBundle.getBundle("bundles.basic");
            iteration++;
        }
    }

    @FXML
    private GridPane gameBoard;

    @FXML
    private void setEasyDifficulty(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        board.solveGame();
        Level.EASY.removeFieldsFromBoard(board);
        startGame();
    }

    @FXML
    private void setMediumDifficulty(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        board.solveGame();
        Level.MEDIUM.removeFieldsFromBoard(board);
        startGame();
    }

    @FXML
    private void setHardDifficulty(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        board.solveGame();
        Level.HARD.removeFieldsFromBoard(board);
        startGame();
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
        stage.setTitle("Wybierz poziom");
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startGame() throws IOException {


        FXMLLoader part = new FXMLLoader(Objects.requireNonNull(getClass()
                .getResource("/Game.fxml")));
        part.setResources(bundle);
        Stage stage = new Stage();
        Pane borderPane = part.load();
        stage.setTitle("Gra Sudoku");
        stage.setHeight(600);
        stage.setWidth(600);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        gameBoard = (GridPane) borderPane.lookup("#gameBoard");
        gameBoard.setAlignment(Pos.CENTER);
        StaticFunctions.printBoard(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField text = new TextField();

                text.setMaxWidth(44);
                text.setMaxHeight(46);
                text.setAlignment(Pos.CENTER);
                text.lengthProperty();

                if (board.get(i, j) != 0) {
                    text.setDisable(true);
                }
                text.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue,
                                        String s, String t1) {
                        if (t1.length() == 0) {
                            return;
                        }
                        if (t1.length() > 1) {
                            t1 = t1.substring(0, 1);
                        }
                        if (!(t1.charAt(0) >= 49 && t1.charAt(0) <= 57)) {
                            t1 = "";
                            text.setText(t1);
                            text.positionCaret(text.getText().length());
                        } else {
                            text.setText(t1);
                        }
                    }
                });
                gameBoard.add(text, j, i);

            }
        }

        stage.show();
        bind();
    }

    private void bind() {
        System.out.println(gameBoard.getChildren());
        List<Node> lista = gameBoard.getChildren();
        lista.remove(0);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    SudokuBidirectionalBinding fieldAdapter = new
                            SudokuBidirectionalBinding(board, i, j);
                    StringProperty textField = JavaBeanStringPropertyBuilder.create()
                            .bean(fieldAdapter).name("xd").build();

                    for (Node node : lista) {
                        if (gameBoard.getRowIndex(node) == i
                                && gameBoard.getColumnIndex(node) == j) {
                            ((TextField) node).textProperty().bindBidirectional(textField);
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    private void wyjdz() {
        System.exit(0);
    }

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
    private static Locale locale;
    private static ResourceBundle bundle;
    private static String language;
    private static ResourceBundle bundleList;

    @FXML
    private Button start;

    @FXML
    private Button exit;


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

    @FXML
    private void loadMainScene(ActionEvent event) throws IOException, InterruptedException {
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            FXMLLoader main = new FXMLLoader(Objects
                    .requireNonNull(getClass().getResource("/sampleJavaFX.fxml")));


            main.setResources(bundle);
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(main.load()));
            primaryStage.setTitle(bundle.getString("title.application"));

            primaryStage.setResizable(false);
            primaryStage.setWidth(600);
            primaryStage.setHeight(600);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public class SudokuBidirectionalBinding {
        private SudokuBoard board;
        private int xd;
        private int yd;

        public SudokuBidirectionalBinding(SudokuBoard board, int x, int y) {
            this.board = board;
            this.xd = x;
            this.yd = y;
        }

        public String getXd() {
            return String.valueOf(this.board.get(xd, yd));
        }

        public void setXd(String value) {
            if (value.equals("")) {
                board.set(xd, yd, 0);
            } else {
                board.set(xd, yd, Integer.parseInt(value));
            }
            StaticFunctions.printBoard(board);
            System.out.println();
        }
    }



}
