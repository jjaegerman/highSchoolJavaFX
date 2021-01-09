package sample;

import javafx.scene.image.Image;

public class Obstacle extends Sprite {

    public Obstacle(double x, double y, String regular, String mov) {
        image =  new Image((this.getClass().getResource("Images/" + regular + ".png").toExternalForm()));
        posX.setValue(x);
        posY.setValue(y);
        speed=2;
        movement=mov;
    }


}
