package view;

import java.io.*;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Repository;
import sudoku.StaticFunctions;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.difficulty.Level;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class Controller {

    private Repository repo = new Repository(new BacktrackingSudokuSolver());
    private static int iteration = 0;
    private SudokuBoard board;
    private static SudokuBoard boardToSaving;
    private static String isDisabled = "";

    public Controller() throws CloneNotSupportedException {
        board = repo.createSudokuBoard();
        if (iteration == 0) {
            bundle = ResourceBundle.getBundle("bundles.basic");
            iteration++;
        }
    }

    @FXML
    private void setEasyDifficulty(ActionEvent event)  {
        ((Node)event.getSource()).getScene().getWindow().hide();
        setLevel(Level.EASY);
    }

    @FXML
    private void setMediumDifficulty(ActionEvent event)  {
        ((Node)event.getSource()).getScene().getWindow().hide();
        setLevel(Level.MEDIUM);
    }

    @FXML
    private void setHardDifficulty(ActionEvent event)  {
        ((Node)event.getSource()).getScene().getWindow().hide();
        setLevel(Level.HARD);
    }

    private void setLevel(Level level) {
        try {
            board.solveGame();
            level.removeFieldsFromBoard(board);
            startGame();
            isDisabled = isTextFieldDisabled(board.clone());
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void start(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        try {
            Stage stage = creatingGamePane(bundle,"/Levels.fxml");
            stage.show();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void startGame() throws IOException {
        try {
            Stage stage = creatingGamePane(bundle,"/Game.fxml");
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
            bind(gameBoard,board);
            boardToSaving = board;
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public String isTextFieldDisabled(SudokuBoard board){
        String string = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board.get(i,j) != 0){
                    string += "1,";
                } else  {
                    string += "0,";
                }
            }
        }
        return string;
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
        try {
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
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void saveCurrent(ActionEvent event) throws CloneNotSupportedException {
        save(boardToSaving);
    }

    @FXML
    private void saveOriginal(ActionEvent event) throws CloneNotSupportedException {
        SudokuBoard board = boardToSaving.clone();
        int counter = 0;
        String[] shouldBeDisabled = isDisabled.split(",");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (shouldBeDisabled[counter].equals("0")) {
                    board.set(i,j,0);
                }
                counter++;
            }
        }
        save(board);
    }

    private void save(SudokuBoard board) throws CloneNotSupportedException {
        try {
            SudokuBoard saving = board.clone();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath().getParent()
                    + "\\mka_pn_1015_02\\SudokuGameProject\\View\\src\\"
                    + "main\\resources\\sudoku.boards" ));
            fileChooser.setInitialFileName("sudoku");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("bin file", "*.bin"));
            Stage stage = new Stage();
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            String path = file.getPath();
            String fileName = file.getName();
            new SudokuBoardDaoFactory().getFileDao(path).write(saving);
            path = file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-4) + "TXT.txt";
            writeToFile(path,isDisabled);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void writeToFile(String path, String isDisabled) throws IOException {
        try(FileWriter fw = new FileWriter(path)){
            fw.write(isDisabled);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private String readFromFile(String path) throws IOException {
        String line = "";
        try (BufferedReader bf = new BufferedReader(new FileReader(path))){
            line = bf.readLine();
        } catch (Exception e){
            System.out.println(e);
        }
        return line;
    }

    @FXML
    private void loadSudokuBoard(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath().getParent()
                    + "\\mka_pn_1015_02\\SudokuGameProject\\View\\src\\"
                    + "main\\resources\\sudoku.boards" ));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                    "bin file", "*.bin"));
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            board = repo.createSudokuBoard();
            ((Node) event.getSource()).getScene().getWindow().hide();
            board = new SudokuBoardDaoFactory().getFileDao(file.getAbsolutePath())
                    .read();
            String path = file.getAbsolutePath().substring(0,file.getAbsolutePath().length() - 4) + "TXT.txt";
            isDisabled = readFromFile(path);
            String[] shouldBeDisabled = isDisabled.split(",");
            int counter = 0;

            Stage stage1 = creatingGamePane(bundle,"/Game.fxml");
            for (int i = 0; i < 9; i++) {
                HBox wiersz = (HBox) gameBoard.getChildren().get(i);
                for (int j = 0; j < 9; j++) {
                    TextField text = (TextField) wiersz.getChildren().get(j);

                    text.setMaxWidth(44);
                    text.setMaxHeight(46);
                    text.setAlignment(Pos.CENTER);
                    text.lengthProperty();
                    if (shouldBeDisabled[counter].equals("1")) {
                            text.setDisable(true);
                        }
                    text.textProperty().addListener(this::fieldListener);
                    counter++;
                }
            }

            bind(gameBoard, board);
            stage1.show();
            boardToSaving = board;

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void bind(VBox vbox, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    TextField text = (TextField) ((HBox) vbox.getChildren().get(i))
                            .getChildren().get(j);
                    SudokuBidirectionalBinding fieldAdapter = new
                            SudokuBidirectionalBinding(board, i, j);
                    StringProperty textField = JavaBeanStringPropertyBuilder.create()
                            .bean(fieldAdapter).name("value").build();

                    text.textProperty().bindBidirectional(textField);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Stage creatingGamePane(ResourceBundle bundle, String path) throws IOException {
        try {
            FXMLLoader part = new FXMLLoader(Objects.requireNonNull(Controller.class
                    .getResource(path)));
            part.setResources(bundle);
            Stage stage1 = new Stage();
            Pane borderPane = part.load();
            stage1.setTitle(bundle.getString("title.application"));
            stage1.setHeight(600);
            stage1.setWidth(600);
            Scene scene = new Scene(borderPane);
            stage1.setScene(scene);
            gameBoard = (VBox) borderPane.lookup("#gameBoard");
            return stage1;
        } catch (Exception e){
            System.out.println(e);
        }
        return new Stage();
    }

    @FXML
    private void loadMainScene(ActionEvent event) throws IOException, InterruptedException {
        try {
              ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = creatingGamePane(bundle,"/sampleJavaFX.fxml");
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static class SudokuBidirectionalBinding {
        private SudokuBoard board;
        private int numberOfRow;
        private int numberOfColumn;

        public SudokuBidirectionalBinding(SudokuBoard board, int x, int y) {
            this.board = board;
            this.numberOfRow = x;
            this.numberOfColumn = y;
        }

        public String getValue() {
            return String.valueOf(this.board.get(numberOfRow, numberOfColumn));
        }

        public void setValue(String value) {
            if (value.equals("")) {
                board.set(numberOfRow, numberOfColumn, 0);
            } else {
                board.set(numberOfRow, numberOfColumn, Integer.parseInt(value));
            }
            StaticFunctions.printBoard(board);
            System.out.println();
        }
    }
}
