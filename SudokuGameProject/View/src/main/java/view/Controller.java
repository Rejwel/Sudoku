package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;
import sudoku.Repository;
import sudoku.StaticFunctions;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.difficulty.Level;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.CalculationsException;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DatabaseConnectionError;
import sudoku.exceptions.DatabaseGeneralError;
import sudoku.exceptions.GetSetException;
import sudoku.exceptions.SetLevelException;
import sudoku.exceptions.SolverException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.BacktrackingSudokuSolver;
import view.exceptions.BindException;
import view.exceptions.CreateGamePaneException;
import view.exceptions.GameStatusException;
import view.exceptions.LanguageException;
import view.exceptions.LoadBoardException;
import view.exceptions.LoadMainSceneException;
import view.exceptions.MainLogicException;
import view.exceptions.ReadFromFileException;
import view.exceptions.SaveException;
import view.exceptions.WriteToFileException;

public class Controller {

    private Repository repo = new Repository(new BacktrackingSudokuSolver());
    private static int iteration = 0;
    private SudokuBoard board;
    private static SudokuBoard boardToSaving;
    private static SudokuBoard boardOriginal;
    private static String isDisabled = "";
    private static JavaBeanIntegerProperty[][] tablica = new JavaBeanIntegerProperty[9][9];
    private static Logger log = Logger.getLogger(Controller.class.getName());

    public Controller() throws SudokuElementConstructorException {
        try {
            board = repo.createSudokuBoard();
            if (iteration == 0) {
                bundle = ResourceBundle.getBundle("bundles.basic");
                iteration++;
            }
        } catch (Exception e) {
            log.error(new MainLogicException(bundle.getString("MainLogicException"), e));
        }

    }

    @FXML
    private void setEasyDifficulty(ActionEvent event)  {
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            setLevel(Level.EASY);
        } catch (Exception e) {
            log.error(new SetLevelException(bundle.getString("SetLevelException"), e));
        }
    }

    @FXML
    private void setMediumDifficulty(ActionEvent event)  {
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            setLevel(Level.MEDIUM);
        } catch (Exception e) {
            log.error(new SetLevelException(bundle.getString("SetLevelException"), e));
        }
    }

    @FXML
    private void setHardDifficulty(ActionEvent event)  {
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            setLevel(Level.HARD);
        } catch (Exception e) {
            log.error(new SetLevelException(bundle.getString("SetLevelException"), e));
        }
    }

    private void setLevel(Level level) {
        try {
            board.solveGame();
            level.removeFieldsFromBoard(board);
            boardOriginal = board.clone();
            startGame();
            isDisabled = isTextFieldDisabled(board.clone());
        } catch (SetLevelException e) {
            log.error(new SetLevelException(e.getMessage(), e));
        } catch (SolverException e) {
            log.error(new SolverException(e.getMessage(), e));
        } catch (Exception e) {
            log.error(new SetLevelException(bundle.getString("SetLevelException"), e));
        }
    }

    @FXML
    private void start(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
        try {
            Stage stage = creatingGamePane(bundle,"/Levels.fxml");
            stage.show();
        } catch (Exception e) {
            log.error(new MainLogicException(bundle.getString("MainLogicException"), e));
        }
    }

    private void startGame() throws CloneNotSupportedException, SolverException, GetSetException {
        try {
            Stage stage = creatingGamePane(bundle,"/Game.fxml");
            bind(gameBoard,board);
            for (int i = 0; i < 9; i++) {
                HBox wiersz = (HBox)gameBoard.getChildren().get(i);
                for (int j = 0; j < 9; j++) {
                    TextField text = (TextField) wiersz.getChildren().get(j);
                    text.setMaxWidth(44);
                    text.setMaxHeight(46);
                    text.setAlignment(Pos.CENTER);
                    text.lengthProperty();
                    text.textProperty().addListener(this::fieldListener);
                    if (boardOriginal.get(i, j) != 0) {
                        text.setDisable(true);
                    }
                }
            }

            stage.show();
            boardToSaving = board;

        } catch (Exception e) {
            log.error(new GameStatusException(bundle.getString("GameStatusException"), e));
        }

        //TODO: Remove \/ after binding is properly done
        SudokuBoard test = boardToSaving.clone();
        test.solveGame();
        StaticFunctions.printBoard(test);
    }

    static void bind(VBox vbox, SudokuBoard board) {
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    TextField text = (TextField) ((HBox) vbox.getChildren().get(i))
                            .getChildren().get(j);
                    SudokuBidirectionalBinding fieldAdapter = new
                            SudokuBidirectionalBinding(board, i, j);
                    tablica[i][j] = JavaBeanIntegerPropertyBuilder.create()
                            .bean(fieldAdapter).name("value").build();

                    final StringConverter converter = new Converter();
                    //Bindings.bindBidirectional(text.textProperty(), textField, converter);
                    text.textProperty().bindBidirectional(tablica[i][j],converter);
                }
            }
        } catch (Exception e) {
            log.error(new BindException(bundle.getString("BindException"), e));
        }
    }

    public String isTextFieldDisabled(SudokuBoard board) {
        StringBuilder string = new StringBuilder();
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board.get(i,j) != 0) {
                        string.append("1,");
                    } else  {
                        string.append("0,");
                    }
                }
            }
            return string.toString();
        } catch (GetSetException e) {
            log.error(new GetSetException(e.getMessage(), e));
        } catch (Exception e) {
            log.error(new MainLogicException(bundle.getString("MainLogicException"), e));
        }
        return string.toString();
    }

    private void fieldListener(ObservableValue<? extends String> observableValue,
                               String s, String t1) {
        try {
            StringProperty text = (StringProperty) observableValue;
            log.info(text.getBean());
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
            text.setValue(t1);
        } catch (Exception e) {
            log.error(new MainLogicException(bundle.getString("MainLogicException"), e));
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
        try {
            Node node = (Node)event.getSource();
            language = "en";
            loadLanguage(language,node);
        } catch (Exception e) {
            log.error(new LanguageException(bundle.getString("LanguageException"), e));
        }
    }

    @FXML
    private void polish(ActionEvent event) {
        try {
            Node node = (Node)event.getSource();
            language = "pl";
            loadLanguage(language,node);
        } catch (Exception e) {
            log.error(new LanguageException(bundle.getString("LanguageException"), e));
        }
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
        } catch (Exception e) {
            log.error(new LanguageException(bundle.getString("LanguageException"), e));
        }
    }

    @FXML
    private void saveCurrent(ActionEvent event) {
        try {
            save(boardToSaving);
        } catch (Exception e) {
            log.error(new SaveException(bundle.getString("SaveException"), e));
        }
    }

    @FXML
    private void saveOriginal(ActionEvent event) {
        try {
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
        } catch (GetSetException e) {
            log.error(new GetSetException(e.getMessage(), e));
        } catch (Exception e) {
            log.error(new SaveException(bundle.getString("SaveException"), e));
        }
    }

    @FXML
    private void checkBoard(ActionEvent event) throws CalculationsException, GetSetException {

        StaticFunctions.printBoard(boardToSaving);

        if (boardToSaving.checkBoard()) {
            showAlert("dialogCheckBoardTitle","dialogCheckBoardContentGood");
        } else {
            showAlert("dialogCheckBoardTitle","dialogCheckBoardContentBad");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString(title));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString(content));
        alert.showAndWait();
    }

    private void save(SudokuBoard board) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(
                    new File(
                            Paths.get("").toAbsolutePath()
                    + "\\View\\src\\"
                    + "main\\resources\\sudoku.boards"));
            fileChooser.setInitialFileName("sudoku");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("bin file", "*.bin"));
            Stage stage = new Stage();
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            String path = file.getPath();
            String fileName = file.getName();
            SudokuBoard saving = board.clone();
            SudokuBoardDaoFactory
                    .getFileDao(path)
                    .write(saving);
            path = file.getAbsolutePath()
                    .substring(0,file.getAbsolutePath().length() - 4)
                    + "TXT.txt";
            writeToFile(path,isDisabled);
        } catch (DaoException e) {
            log.error(new DaoException(e.getMessage(), e));
        } catch (Exception e) {
            log.error(new SaveException(bundle.getString("SaveException"), e));
        }
    }

    private void writeToFile(String path, String isDisabled) {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(isDisabled);
        } catch (Exception e) {
            log.error(new WriteToFileException(bundle.getString("WriteToFileException"), e));
        }
    }

    private String readFromFile(String path) {
        String line = "";
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            line = bf.readLine();
        } catch (Exception e) {
            log.error(new ReadFromFileException(bundle.getString("ReadFromFileException"), e));
        }
        return line;
    }

    @FXML
    private void loadSudokuBoard(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(
                    new File(
                            Paths.get("").toAbsolutePath()
                    + "\\View\\src\\"
                    + "main\\resources\\sudoku.boards"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                    "bin file", "*.bin"));
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            board = repo.createSudokuBoard();
            ((Node) event.getSource())
                    .getScene()
                    .getWindow()
                    .hide();
            board = SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath())
                    .read();
            String path = file.getAbsolutePath()
                    .substring(0,file.getAbsolutePath().length() - 4)
                    + "TXT.txt";
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

        } catch (DaoException e) {
            log.error(new DaoException(e.getMessage(), e));
        } catch (Exception e) {
            log.error(new LoadBoardException(bundle.getString("LoadBoardException"), e));
        }
    }

    public Stage creatingGamePane(ResourceBundle bundle, String path) {
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
            chooseHBox = (HBox) borderPane.lookup("#chooseHBox");
            savingInput = (VBox) borderPane.lookup("#savingInput");
            return stage1;
        } catch (Exception e) {
            log.error(new CreateGamePaneException(bundle.getString("CreateGamePaneException"), e));
        }
        return new Stage();
    }

    @FXML
    private void loadMainScene(ActionEvent event) {
        try {

            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = creatingGamePane(bundle,"/sampleJavaFX.fxml");
            primaryStage.show();
        } catch (Exception e) {
            log.error(new LoadMainSceneException(bundle.getString("LoadMainSceneException"), e));
        }
    }

    @FXML
    private HBox chooseHBox;

    private int choice = -1;

    @FXML
    private void loadBoards(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            final Stage stage = creatingGamePane(bundle,"/chooseBoard.fxml");
            ListView listView = new ListView();

            List<String> boardsInDatabase = Objects.requireNonNull(SudokuBoardDaoFactory
                    .getDatabaseDao()).getAll();

            for (String nazwa: boardsInDatabase) {
                if (!nazwa.contains("Original")) {
                    listView.getItems().add(nazwa);
                }
            }
            Button button = new Button(bundle.getString("opening"));
            button.setMinWidth(90);
            Button removeButton = new Button(bundle.getString("remove"));
            removeButton.setMinWidth(90);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ObservableList selectedIndices = listView
                            .getSelectionModel().getSelectedIndices();

                    for (Object o : selectedIndices) {
                        choice = (Integer)o;
                        try {
                            if (choice == -1) {
                                showAlert("Error", "noBoardChoose");
                            } else {

                                board = SudokuBoardDaoFactory.getDatabaseDao()
                                        .get(listView.getItems().get(choice).toString());
                                boardOriginal = SudokuBoardDaoFactory.getDatabaseDao()
                                        .get(listView.getItems().get(choice).toString()
                                                + "Original");
                                ((Node) event.getSource()).getScene().getWindow().hide();
                                startGame();
                            }
                        } catch (DatabaseConnectionError e) {
                            log.error(new DatabaseConnectionError(bundle
                                    .getString("DBConnectionException"), e));
                        } catch (DatabaseGeneralError e) {
                            log.error(new DatabaseGeneralError(bundle
                                    .getString("DBGeneralException"), e));
                        } catch (Exception e) {
                            log.error(new MainLogicException(bundle
                                    .getString("MainLogicException"), e));
                        }
                    }
                }
            });

            removeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ObservableList selectedIndices = listView
                            .getSelectionModel().getSelectedIndices();

                    for (Object o : selectedIndices) {
                        choice = (Integer)o;
                        try {
                            if (choice == -1) {
                                showAlert("Error", "noBoardChoose");
                            } else {
                                SudokuBoardDaoFactory.getDatabaseDao()
                                        .deleteRecord(listView.getItems().get(choice).toString());
                                SudokuBoardDaoFactory.getDatabaseDao()
                                        .deleteRecord(listView.getItems().get(choice).toString()
                                                + "Original");
                                ((Node) event.getSource()).getScene()
                                        .getWindow().hide();
                                loadBoards(event);
                            }
                        } catch (DatabaseConnectionError e) {
                            log.error(new DatabaseConnectionError(bundle
                                    .getString("DBConnectionException"), e));
                        } catch (DatabaseGeneralError e) {
                            log.error(new DatabaseGeneralError(bundle
                                    .getString("DBGeneralException"), e));
                        } catch (Exception e) {
                            log.error(new MainLogicException(bundle
                                    .getString("MainLogicException"), e));
                        }
                    }
                }
            });
            chooseHBox.getChildren().add(listView);
            chooseHBox.getChildren().add(button);
            chooseHBox.getChildren().add(removeButton);
            stage.show();

        } catch (DatabaseConnectionError e) {
            log.error(new DatabaseConnectionError(bundle
                    .getString("DBConnectionException"), e));
        } catch (DatabaseGeneralError e) {
            log.error(new DatabaseGeneralError(bundle
                    .getString("DBGeneralException"), e));
        } catch (Exception e) {
            log.error(new CreateGamePaneException(bundle
                    .getString("CreateGamePaneException"), e));
        }
    }

    @FXML
    private VBox savingInput;

    @FXML
    private void saveToDataBase(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = creatingGamePane(bundle,"/saveToDataBase.fxml");
        TextField text = (TextField) savingInput.getChildren().get(0);
        text.textProperty()
                .addListener((ObservableValue<? extends String>
                                      observableValue, String s, String t1) -> {
            try {
                if (t1.length() == 0) {
                    return;
                }
                if (t1.length() > 1) {
                    t1 = t1.substring(0, 15);
                }
            } catch (Exception e) {
                log.error(new MainLogicException(bundle.getString("MainLogicException"), e));
            }
        });
        stage.show();
    }

    @FXML
    private void confirmSavingToDataBase(ActionEvent event) {
        saveBoard(boardToSaving,boardOriginal,event);
    }

    @FXML
    private void confirmSavingOriginalToDataBase(ActionEvent event) {
        saveBoard(boardOriginal,boardOriginal,event);
    }

    private void saveBoard(SudokuBoard saveBoard, SudokuBoard originalBoard,
            ActionEvent event) {
        try {
            Pattern pattern = Pattern.compile("[A-Za-z]([\\w])+");
            Pattern notOriginal = Pattern.compile("[oO][Rr][iI][Gg][iI][nN][Aa][Ll]");
            TextField text = (TextField) savingInput.getChildren().get(0);
            if (pattern.matcher(text.getText()).matches() && !notOriginal
                    .matcher(text.getText()).find()) {
                SudokuBoardDaoFactory.jdbcSudokuBoardDao(text
                        .getText()).write(saveBoard.clone());
                SudokuBoardDaoFactory.jdbcSudokuBoardDao(text
                        .getText() + "Original").write(originalBoard.clone());
                ((Node) event.getSource()).getScene().getWindow().hide();
                board = repo.createSudokuBoard();
                board = boardToSaving.clone();
                startGame();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("badSavingNameTitle"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("badSavingName"));
                alert.showAndWait();
            }
        } catch (DatabaseConnectionError e) {
            log.error(new DatabaseConnectionError(bundle
                    .getString("DBConnectionException"), e));
        } catch (DatabaseGeneralError e) {
            log.error(new DatabaseGeneralError(bundle
                    .getString("DBGeneralException"), e));
        } catch (Exception e) {
            log.error(new MainLogicException(bundle
                    .getString("MainLogicException"), e));
        }
    }

    @FXML
    private void backToGame(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            board = repo.createSudokuBoard();
            board = boardToSaving.clone();
            startGame();

        } catch (Exception e) {
            log.error(new CreateGamePaneException(bundle.getString("CreateGamePaneException"), e));
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

        public int getValue() throws GetSetException {
            return this.board.get(numberOfRow, numberOfColumn);
        }

        public void setValue(int value) throws GetSetException {
            board.set(numberOfRow, numberOfColumn, value);
            StaticFunctions.printBoard(board);
        }
    }
}
