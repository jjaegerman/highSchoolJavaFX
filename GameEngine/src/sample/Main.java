package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
//yup
public class Main extends Application {

    public static Stage window;
    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Game Engine");
        scene = new Scene(root, 1280, 800);
        URL url = this.getClass().getResource("Style.css");
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.getIcons().add(new Image(this.getClass().getResource("Icon.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
