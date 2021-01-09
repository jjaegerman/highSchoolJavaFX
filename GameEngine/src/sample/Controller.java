package sample;

import javafx.animation.AnimationTimer;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sample.Sprites.BaseObject;
import sample.Sprites.Sprite;

import java.net.URL;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.rgb;
//yup

public class Controller implements Initializable {

    public Label xPos;
    public Label yPos;
    public Canvas canvas;
    public ImageView Start;
    public ImageView Pause;
    public ImageView FastForward;
    public AnimationTimer animate;
    public AnimationTimer logic;
    public ArrayList<BaseObject> all = new ArrayList<BaseObject>();
    public ListView list;
    public ChoiceBox texture;
    public TextField rigidity;
    public TextField mass;
    public TextField size;
    public ToggleGroup mode;
    public RadioButton radioCreate;
    public RadioButton radioDelete;
    public RadioButton radioDrag;
    public GridPane baseItemMenu;

    public void MouseXY(MouseEvent event){

        xPos.setText(Double.toString(event.getX()));
        yPos.setText(Double.toString(event.getY()));
    }

    public void Click(MouseEvent event){
        switch(mode.getSelectedToggle().getUserData().toString()) {
            case "Create":
                try {
                    if (Double.parseDouble(size.getText()) != 0) {
                        BaseObject temp = new BaseObject(event.getX(), event.getY(), Double.parseDouble(rigidity.getText()), Double.parseDouble(size.getText()), texture.getValue().toString(), Double.parseDouble(mass.getText()));
                        all.add(temp);
                        temp.render(canvas.getGraphicsContext2D());
                    }
                } catch (NullPointerException e) {}
                break;
            case "Delete":
                int x = -1;
                for(BaseObject s: all){
                    if(s.getBoundary().contains(event.getX(), event.getY())) {
                        x = all.indexOf(s);
                    }
                }
                if(x>-1) {
                    all.remove(x);
                }
                canvas.getGraphicsContext2D().clearRect(0.0,0.0,1101.0,750.5);
                for(BaseObject s: all){
                    s.render(canvas.getGraphicsContext2D());
                }
                break;
            default:
                break;
        }
        }

    public void Drag(MouseEvent event){
        if(mode.getSelectedToggle().getUserData().toString().equals("Drag")){
            for(BaseObject s: all){
                if(s.getBoundary().contains(event.getX(), event.getY())) {
                    s.posX.setValue(event.getX()-s.radius/2.0);
                    s.posY.setValue(event.getY()-s.radius/2.0);
                }
            }
            canvas.getGraphicsContext2D().clearRect(0.0,0.0,1101.0,750.5);
            for(BaseObject s: all){
                s.render(canvas.getGraphicsContext2D());
            }
        }


    }
    public void Game(){

        Start.setVisible(false);
        Start.setFitHeight(0.000001);
        Pause.setVisible(true);
        FastForward.setVisible(true);
        logic = new AnimationTimer(){
            long before=System.nanoTime();
            double time;
            @Override
            public void handle(long now) {
                time = (now - before) / 1000000000.0;
                before = now;
                for (BaseObject s : all) {
                    s.update(time);
                }
                for (int i=0;i<all.size();i++) {
                    for (int y=i+1;y<all.size();y++) {
                        if (all.get(i).intersects(all.get(y))) {
                            all.get(i).collision(all.get(y));
                        }
                    }
                }
            }
        };
        logic.start();
        animate = new AnimationTimer(){
            @Override
            public void handle(long now) {
                canvas.getGraphicsContext2D().clearRect(0.0,0.0,1101.0,750.5);
                for(BaseObject s: all){
                    s.render(canvas.getGraphicsContext2D());
                }}
        };
        animate.start();
    }

    public void Clear(){
        all.clear();
        canvas.getGraphicsContext2D().clearRect(0.0,0.0,1101.0,750.5);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public void End(){
        animate.stop();
        logic.stop();
        Pause.setVisible(false);
        FastForward.setVisible(false);
        Start.setFitHeight(16);
        Start.setVisible(true);
    }

    public void Exit(){
        Main.window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(this);
        list.getItems().add("Basic Object");
        list.getItems().add("Rope");
        texture.getItems().add("Ball");
        texture.getItems().add("Gaalaas");
        list.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
            try {
                switch (oldValue.toString()) {
                    case "Basic Object":
                        baseItemMenu.setVisible(false);
                        break;
                }
            }catch(NullPointerException e){}
                switch (newValue.toString()) {
                    case "Basic Object":
                        baseItemMenu.setVisible(true);
                        break;
                }
        });
        texture.getSelectionModel().selectedItemProperty().addListener( (v,oldValue,newValue) -> {
            switch(newValue.toString()){
                case "Ball":
                    size.setText("25.0");
                    rigidity.setText("1.0");
                    mass.setText("0.5");
                    break;
                case "Gaalaas":
                    size.setText("100.0");
                    rigidity.setText("1.0");
                    mass.setText("60.0");
                    break;
                default:
                    size.clear();
                    rigidity.clear();
                    mass.clear();
            }
        });
        mode=new ToggleGroup();
        radioCreate.setUserData("Create");
        radioCreate.setToggleGroup(mode);
        radioDelete.setUserData("Delete");
        radioDelete.setToggleGroup(mode);
        radioDrag.setUserData("Drag");
        radioDrag.setToggleGroup(mode);
    }
}
