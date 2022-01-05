package view;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.StaticFunctions;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.elements.SudokuBoard;

public class GameController {

    private ResourceBundle bundle = Controller.getBundle();
    private static SudokuBoard board;
    private SudokuBoard boardFromLoading;
    private static boolean creatingNewSudokuBoard = false;

    @FXML
    private static VBox gameBoard;

    public static void setBoard(SudokuBoard board1) {
        board = board1;
    }

    public static void setCreatingNewSudokuBoard(boolean creatingNewSudokuBoard) {
        GameController.creatingNewSudokuBoard = creatingNewSudokuBoard;
    }

    static void bind(VBox vbox, SudokuBoard board) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    TextField text = (TextField) ((HBox) vbox.getChildren().get(i))
                            .getChildren().get(j);
                    GameController.SudokuBidirectionalBinding fieldAdapter = new
                            GameController.SudokuBidirectionalBinding(board, i, j);
                    StringProperty textField = JavaBeanStringPropertyBuilder.create()
                            .bean(fieldAdapter).name("xd").build();

                    text.textProperty().bindBidirectional(textField);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
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
    private void save(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(
                "C:\\kompo\\mka_pn_1015_02\\SudokuGameProject\\View"
                        + "\\src\\main\\resources\\sudoku.boards"));
        fileChooser.setInitialFileName("sudoku");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("bin file", "*.bin"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            String path = file.getPath();
            String fileName = file.getName();
            new SudokuBoardDaoFactory().getFileDao(path).write(board);
            creatingNewSudokuBoard = Controller.isCreatingNewSudokuBoard();
            SudokuBoard boardOriginal = Controller.getBoardOriginal();

            if (creatingNewSudokuBoard && !boardOriginal.equals(board)) {
                String newPath = path.substring(0, path.length() - 4) + "Original.bin";
                new SudokuBoardDaoFactory().getFileDao(newPath).write(boardOriginal);
                creatingNewSudokuBoard = false;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void loadSudokuBoard(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(
                "C:\\kompo\\mka_pn_1015_02\\SudokuGameProject\\View\\src\\"
                        + "main\\resources\\sudoku.boards"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "bin file", "*.bin"));
        Stage stage = new Stage();
        try {
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());

            ((Node) event.getSource()).getScene().getWindow().hide();
            boardFromLoading = new SudokuBoardDaoFactory().getFileDao(file.getAbsolutePath())
                    .read();
            SudokuBoard boardToCompare = null;
            if (!file.getAbsolutePath().substring(file.getAbsolutePath().length() - 12, file
                    .getAbsolutePath()
                    .length() - 4).equals("Original")) {
                String path = file.getAbsolutePath().substring(0,
                        file.getAbsolutePath().length() - 4) + "Original.bin";
                boardToCompare = new SudokuBoardDaoFactory().getFileDao(path).read();
            }

            Stage stage1 = creatingGamePane(bundle);
            for (int i = 0; i < 9; i++) {
                HBox wiersz = (HBox) gameBoard.getChildren().get(i);
                for (int j = 0; j < 9; j++) {
                    TextField text = (TextField) wiersz.getChildren().get(j);

                    text.setMaxWidth(44);
                    text.setMaxHeight(46);
                    text.setAlignment(Pos.CENTER);
                    text.lengthProperty();
                    if (boardToCompare != null) {
                        if (boardFromLoading.get(i, j) == boardToCompare.get(i, j)
                                && boardFromLoading.get(i, j) != 0) {
                            text.setDisable(true);
                        }
                    } else {
                        if (boardFromLoading.get(i, j) != 0) {
                            text.setDisable(true);
                        }
                    }

                    text.textProperty().addListener(this::fieldListener);

                }
            }

            bind(gameBoard, boardFromLoading);
            stage1.show();

            creatingNewSudokuBoard = false;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Stage creatingGamePane(ResourceBundle bundle) throws IOException {
        FXMLLoader part = new FXMLLoader(Objects.requireNonNull(GameController.class
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
        return stage1;
    }

    @FXML
    private void loadMainScene(ActionEvent event) throws IOException, InterruptedException {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
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

    public static class SudokuBidirectionalBinding {
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
