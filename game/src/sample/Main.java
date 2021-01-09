package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.*;

public class Main extends Application {

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Life");
        scene = new Scene(root, 1680, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();

        //character action
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {


                //movement
                if(event.getCode()==W){
                    Controller.character.action("UP", true);
                }else if(event.getCode()==A){
                    Controller.character.action("LEFT", true);
                }else if(event.getCode()==S){
                    Controller.character.action("DOWN", true);
                }else if(event.getCode()==D){
                    Controller.character.action("RIGHT", true);
                }else{
                    Controller.character.action(event.getCode().toString(), true);
                }

            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {


                //stopping
                if(event.getCode()==W){
                    Controller.character.action("UP", false);
                }else if(event.getCode()==A){
                    Controller.character.action("LEFT", false);
                }else if(event.getCode()==S){
                    Controller.character.action("DOWN", false);
                }else if(event.getCode()==D){
                    Controller.character.action("RIGHT", false);
                }else{
                    Controller.character.action(event.getCode().toString(), false);
                }

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
