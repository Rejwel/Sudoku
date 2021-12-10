package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Controller {

    @FXML
    private void start() throws IOException {


        Parent part = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Levels.fxml")));
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
