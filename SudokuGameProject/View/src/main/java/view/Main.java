package view;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent main = FXMLLoader.load(Objects
                .requireNonNull(getClass().getResource("/sampleJavaFX.fxml")));

        primaryStage.setTitle("Sudoku Game");
        primaryStage.setScene(new Scene(main));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
