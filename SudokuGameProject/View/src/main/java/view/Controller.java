package view;

import java.io.File;
import java.io.FileWriter;
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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Repository;
import sudoku.StaticFunctions;
import sudoku.dao.Dao;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.difficulty.Level;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class Controller {

    private Repository repo = new Repository(new BacktrackingSudokuSolver());
    private SudokuBoard board;
    private static SudokuBoard boardOriginal;
    private static int iteration = 0;
    private static boolean creatingNewSudokuBoard = false;

    public Controller() throws CloneNotSupportedException {
        System.out.println("aaaa");
        board = repo.createSudokuBoard();
        if (iteration == 0) {
            bundle = ResourceBundle.getBundle("bundles.basic");
            creatingNewSudokuBoard = false;
            iteration++;
        }
    }

    @FXML
    private VBox gameBoard;

    @FXML
    private void setEasyDifficulty(ActionEvent event) throws IOException, CloneNotSupportedException {
        System.out.println("setEasy");
        StaticFunctions.printBoard(board);
        ((Node)event.getSource()).getScene().getWindow().hide();
        board.solveGame();
        Level.EASY.removeFieldsFromBoard(board);
        boardOriginal = board.clone();
        startGame();
        creatingNewSudokuBoard = true;
    }

    @FXML
    private void setMediumDifficulty(ActionEvent event) throws IOException, CloneNotSupportedException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        board.solveGame();
        Level.MEDIUM.removeFieldsFromBoard(board);
        boardOriginal = board.clone();
        startGame();
        creatingNewSudokuBoard = true;
    }

    @FXML
    private void setHardDifficulty(ActionEvent event) throws IOException, CloneNotSupportedException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        board.solveGame();
        Level.HARD.removeFieldsFromBoard(board);
        boardOriginal = board.clone();
        startGame();
        creatingNewSudokuBoard = true;
    }

    @FXML
    private void start(ActionEvent event) throws IOException {
        System.out.println("start");
        StaticFunctions.printBoard(board);
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

    @FXML
    private void startGame() throws IOException {
        System.out.println("startGame");
        StaticFunctions.printBoard(board);
        board.set(0,0,5);
        board.set(0,1,5);
        StaticFunctions.printBoard(board);

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
        bind();
    }


    private void bind() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    TextField text = (TextField) ((HBox)gameBoard.getChildren().get(i)).getChildren().get(j);
                    SudokuBidirectionalBinding fieldAdapter = new
                            SudokuBidirectionalBinding(board, i, j);
                    StringProperty textField = JavaBeanStringPropertyBuilder.create()
                            .bean(fieldAdapter).name("xd").build();

                    text.textProperty().bindBidirectional(textField);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    private Button save;

    @FXML
    private void save(ActionEvent event){
        System.out.println("save");
        StaticFunctions.printBoard(this.board);
        System.out.println();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\kompo\\mka_pn_1015_02\\SudokuGameProject\\View\\src\\main\\resources\\sudoku.boards"));
        fileChooser.setInitialFileName("sudoku");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("bin file", "*.bin"));
        //Stage stage = new Stage();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        try {
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            String path = file.getPath();
            String fileName = file.getName();
            new SudokuBoardDaoFactory().getFileDao(path).write(board);
            if(creatingNewSudokuBoard && !boardOriginal.equals(board)){
                String newPath = path.substring(0,path.length()-4) + "Original.bin";
                new SudokuBoardDaoFactory().getFileDao(newPath).write(boardOriginal);
                creatingNewSudokuBoard = false;
            }

        } catch (Exception e){
            System.out.println(e);
        }
        System.out.println("saveKoniec");
        StaticFunctions.printBoard(board);
    }

    @FXML
    private void loadSudokuBoard(ActionEvent event){
        StaticFunctions.printBoard(boardOriginal);
        System.out.println();
        StaticFunctions.printBoard(board);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\kompo\\mka_pn_1015_02\\SudokuGameProject\\View\\src\\main\\resources\\sudoku.boards"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("bin file", "*.bin"));
        Stage stage = new Stage();
        try {
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());

            ((Node)event.getSource()).getScene().getWindow().hide();
            board = new SudokuBoardDaoFactory().getFileDao(file.getAbsolutePath()).read();

            FXMLLoader part = new FXMLLoader(Objects.requireNonNull(getClass()
                    .getResource("/Game.fxml")));
            part.setResources(bundle);

            Stage stage1 = new Stage();
            Pane borderPane = part.load();
            stage1.setTitle(bundle.getString("title.application"));
            stage1.setHeight(600);
            stage1.setWidth(600);
            Scene scene = new Scene(borderPane);
            stage1.setScene(scene);

            gameBoard = (VBox) borderPane.lookup("#gameBoard");

            for (int i = 0; i < 9; i++) {
                HBox wiersz = (HBox)gameBoard.getChildren().get(i);
                for (int j = 0; j < 9; j++) {
                    TextField text = (TextField) wiersz.getChildren().get(j);
                    System.out.println(i);
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
            System.out.println("loading function");
            StaticFunctions.printBoard(board);
            bind();
            stage1.show();


            creatingNewSudokuBoard = false;
        } catch (Exception e){
            System.out.println(e);
        }
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
            Pane mainPane = main.load();
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(mainPane));
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
