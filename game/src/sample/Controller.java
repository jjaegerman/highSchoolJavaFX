package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    AnimationTimer animate;
    public Canvas canvas;
    Scene scene = Main.scene;


    public static Player character;
    ArrayList<Sprite> sprites = new ArrayList<Sprite>();

//NEXT STEP; OPTIMIZE PERFORMANCE SUCH THAT ONLY OBJECTS IN THE SCREEN ARE SEEN


    ColorAdjust whiter = new ColorAdjust();
    double glow = 0.6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //afterglow
        whiter.setBrightness(glow);

        //creation of character
        character = new Player(815,475, "character", "sprint");

        //walls
            for(int i=-2000; i<=2000;i+=41){
                for(int o=-2000; o<=2000; o+=41){
                    if(Math.random()<0.1){
                        sprites.add(new Obstacle(i,o,"wall","BASE"));
                    }
                }
            }


        //animation loop
        animate = new AnimationTimer() {

            //long before=0;

            @Override
            public void handle(long now) {

                //System.out.println(1000000000/(now-before));
                //before = System.nanoTime();


                canvas.getGraphicsContext2D().applyEffect(whiter);

                //character movement
                double xShift=0;
                double yShift=0;
                if (!character.movement.equals("BASE")) {
                    String[] characterM = character.movement.split("_");
                    switch (characterM[characterM.length - 1]) {
                        case "UP":
                            switch (characterM[characterM.length - 2]) {
                                case "RIGHT":
                                    yShift = -character.speed / Math.sqrt(2);
                                    xShift = character.speed / Math.sqrt(2);
                                    break;
                                case "LEFT":
                                    yShift= - character.speed / Math.sqrt(2);
                                    xShift = - character.speed / Math.sqrt(2);
                                    break;
                                default:
                                    yShift = - character.speed;
                                    break;
                            }
                            break;
                        case "DOWN":
                            switch (characterM[characterM.length - 2]) {
                                case "RIGHT":
                                    yShift =  character.speed / Math.sqrt(2);
                                    xShift =  character.speed / Math.sqrt(2);
                                    break;
                                case "LEFT":
                                    yShift =  character.speed / Math.sqrt(2);
                                    xShift =  - character.speed / Math.sqrt(2);
                                    break;
                                default:
                                    yShift = character.speed;
                                    break;
                            }
                            break;
                        case "LEFT":
                            switch (characterM[characterM.length - 2]) {
                                case "DOWN":
                                    yShift =  character.speed / Math.sqrt(2);
                                    xShift = - character.speed / Math.sqrt(2);
                                    break;
                                case "UP":
                                    yShift = - character.speed / Math.sqrt(2);
                                    xShift = - character.speed / Math.sqrt(2);
                                    break;
                                default:
                                    xShift = - character.speed;
                                    break;
                            }
                            break;
                        case "RIGHT":
                            switch (characterM[characterM.length - 2]) {
                                case "DOWN":
                                    yShift =  character.speed / Math.sqrt(2);
                                    xShift =  character.speed / Math.sqrt(2);
                                    break;
                                case "UP":
                                    yShift = - character.speed / Math.sqrt(2);
                                    xShift =  character.speed / Math.sqrt(2);
                                    break;
                                default:
                                    xShift = character.speed;
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                }
                character.render(canvas.getGraphicsContext2D());
                
                //all non-character based sprites
                for(Sprite spriteN : sprites) {
                    if (!spriteN.movement.equals("BASE")) {
                        String[] spriteM = spriteN.movement.split("_");
                        switch (spriteM[spriteM.length - 1]) {
                            case "UP":
                                switch (spriteM[spriteM.length - 2]) {
                                    case "RIGHT":
                                        spriteN.posY.setValue(spriteN.posY.getValue() - spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() + spriteN.speed / Math.sqrt(2));
                                        break;
                                    case "LEFT":
                                        spriteN.posY.setValue(spriteN.posY.getValue() - spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() - spriteN.speed / Math.sqrt(2));
                                        break;
                                    default:
                                        spriteN.posY.setValue(spriteN.posY.getValue() - spriteN.speed);
                                        break;
                                }
                                break;
                            case "DOWN":
                                switch (spriteM[spriteM.length - 2]) {
                                    case "RIGHT":
                                        spriteN.posY.setValue(spriteN.posY.getValue() + spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() + spriteN.speed / Math.sqrt(2));
                                        break;
                                    case "LEFT":
                                        spriteN.posY.setValue(spriteN.posY.getValue() + spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() - spriteN.speed / Math.sqrt(2));
                                        break;
                                    default:
                                        spriteN.posY.setValue(spriteN.posY.getValue() + spriteN.speed);
                                        break;
                                }
                                break;
                            case "LEFT":
                                switch (spriteM[spriteM.length - 2]) {
                                    case "DOWN":
                                        spriteN.posY.setValue(spriteN.posY.getValue() + spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() - spriteN.speed / Math.sqrt(2));
                                        break;
                                    case "UP":
                                        spriteN.posY.setValue(spriteN.posY.getValue() - spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() - spriteN.speed / Math.sqrt(2));
                                        break;
                                    default:
                                        spriteN.posX.setValue(spriteN.posX.getValue() - spriteN.speed);
                                        break;
                                }
                                break;
                            case "RIGHT":
                                switch (spriteM[spriteM.length - 2]) {
                                    case "DOWN":
                                        spriteN.posY.setValue(spriteN.posY.getValue() + spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() + spriteN.speed / Math.sqrt(2));
                                        break;
                                    case "UP":
                                        spriteN.posY.setValue(spriteN.posY.getValue() - spriteN.speed / Math.sqrt(2));
                                        spriteN.posX.setValue(spriteN.posX.getValue() + spriteN.speed / Math.sqrt(2));
                                        break;
                                    default:
                                        spriteN.posX.setValue(spriteN.posX.getValue() + spriteN.speed);
                                        break;
                                }
                                break;
                            default:
                                break;
                        }

                        //sprite-character collision
                        if(spriteN.intersects(character)){
                            double dX;
                            double dY;
                            if(spriteN.posX.getValue()>character.posX.getValue()){
                                dX = character.posX.getValue()+character.image.getWidth()-spriteN.posX.getValue();
                            }else{
                                dX = -spriteN.posX.getValue()-spriteN.image.getWidth()+character.posX.getValue();
                            }
                            if(spriteN.posY.getValue()>character.posY.getValue()){
                                dY = character.posY.getValue()+character.image.getHeight()-spriteN.posY.getValue();
                            }else{
                                dY = -spriteN.posY.getValue()-spriteN.image.getHeight()+character.posY.getValue();
                            }
                            if (Math.abs(dX) > Math.abs(dY)) {
                                spriteN.posY.setValue(spriteN.posY.getValue()+dY);
                            }else{
                                spriteN.posX.setValue(spriteN.posX.getValue()+dX);
                            }
                        }

                        //sprite - sprite collision
                        for(Sprite other : sprites){
                            if(other!=spriteN){
                                if(spriteN.intersects(other)){
                                    double dX;
                                    double dY;
                                    if(spriteN.posX.getValue()>other.posX.getValue()){
                                        dX = other.posX.getValue()+other.image.getWidth()-spriteN.posX.getValue();
                                    }else{
                                        dX = -spriteN.posX.getValue()-spriteN.image.getWidth()+other.posX.getValue();
                                    }
                                    if(spriteN.posY.getValue()>other.posY.getValue()){
                                        dY = other.posY.getValue()+other.image.getHeight()-spriteN.posY.getValue();
                                    }else{
                                        dY = -spriteN.posY.getValue()-spriteN.image.getHeight()+other.posY.getValue();
                                    }
                                    if (Math.abs(dX) > Math.abs(dY)) {
                                        spriteN.posY.setValue(spriteN.posY.getValue()+dY);
                                    }else{
                                        spriteN.posX.setValue(spriteN.posX.getValue()+dX);
                                    }
                                }
                            }
                        }
                    }
                }

                //character - sprite collision
                for(Sprite spriteN : sprites) {
                    double dX;
                    double dY;
                    if(character.intersectsC(spriteN,xShift,yShift)) {
                        if (spriteN.posX.getValue() > character.posX.getValue()) {
                            dX = character.posX.getValue() + character.image.getWidth() - spriteN.posX.getValue();
                        } else {
                            dX = -spriteN.posX.getValue() - spriteN.image.getWidth() + character.posX.getValue();
                        }
                        if (spriteN.posY.getValue() > character.posY.getValue()) {
                            dY = character.posY.getValue() + character.image.getHeight() - spriteN.posY.getValue();
                        } else {
                            dY = -spriteN.posY.getValue() - spriteN.image.getHeight() + character.posY.getValue();
                        }
                        if (Math.abs(dX) > Math.abs(dY)) {
                            yShift=-dY;
                        } else {
                            xShift=-dX;
                        }
                    }
                }

//SOMETHING HERE
                for(Sprite spriteN : sprites){
                    spriteN.posY.setValue(spriteN.posY.getValue()-yShift);
                    spriteN.posX.setValue(spriteN.posX.getValue()-xShift);
                    spriteN.render(canvas.getGraphicsContext2D());
                }
            }
        };
        animate.start();

    }
}
