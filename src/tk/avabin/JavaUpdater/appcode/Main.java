package tk.avabin.JavaUpdater.appcode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private final double version = 0.1D;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("Updater paczki modów v"+version);
        primaryStage.setScene(new Scene(root, 480, 320));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
