package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

import static javafx.scene.input.KeyCode.*;

public class Player extends Sprite {
    Image normal;
    Image special;

    public Player(double x, double y, String regular, String special) {
        image = normal = new Image((this.getClass().getResource("Images/" + regular + ".png").toExternalForm()));
        this.special = new Image((this.getClass().getResource("Images/" + special + ".png").toExternalForm()));
        posX.setValue(x-(normal.getWidth()/2.0));
        posY.setValue(y-(normal.getHeight()/2.0));
        speed=7;
    }

    public void action(String code, Boolean on){

        if(on){
            switch(code) {
                case "UP":
                    if(!movement.contains(code))
                        movement+=("_"+code);
                    break;
                case "DOWN":
                    if(!movement.contains(code))
                        movement+=("_"+code);
                    break;
                case "RIGHT":
                    if(!movement.contains(code))
                        movement+=("_"+code);
                    break;
                case "LEFT":
                    if(!movement.contains(code))
                        movement+=("_"+code);
                    break;
                case "SHIFT":
                    speed = 20;
                    image = special;
                    break;
                default:
                    break;
            }
        }else{
            switch(code){
                case "UP":
                    movement = movement.replace("_"+code,"");
                    break;
                case "DOWN":
                    movement = movement.replace("_"+code,"");
                    break;
                case "LEFT":
                    movement = movement.replace("_"+code,"");
                    break;
                case "RIGHT":
                    movement = movement.replace("_"+code,"");
                    break;
                case "SHIFT":
                    speed = 7;
                    image = normal;
                    break;
                default:
                    break;
            }
        }
    }

}
