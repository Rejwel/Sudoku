package view;

import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.basic");
        FXMLLoader main = new FXMLLoader(Objects
                .requireNonNull(getClass().getResource("/sampleJavaFX.fxml")));

        main.setResources(bundle);
        primaryStage.setScene(new Scene(main.load()));
        primaryStage.setTitle(bundle.getString("title.application"));
        primaryStage.setResizable(false);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
