package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static javafx.scene.input.KeyCode.*;

public class Sprite {
    Image image;
    public DoubleProperty posX= new SimpleDoubleProperty();
    public DoubleProperty posY= new SimpleDoubleProperty();
    public double speed;
    public String movement = "BASE";

    public Sprite() {
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image,posX.getValue(),posY.getValue());
    }

    public Rectangle getArea(){
        return(new Rectangle(posX.getValue(),posY.getValue(),image.getWidth(),image.getHeight()));
    }

    public Rectangle getAreaC(double xShift, double yShift){
        return(new Rectangle(posX.getValue()-xShift,posY.getValue()-yShift,image.getWidth(),image.getHeight()));
    }

    public boolean intersects(Sprite other) {
        return(this.getArea().intersects(other.getArea().getX(),other.getArea().getY(),other.getArea().getWidth(),other.getArea().getHeight()));
    }

    public boolean intersectsC(Sprite other, double xShift, double yShift){
        return(this.getArea().intersects(other.getAreaC(xShift, yShift).getX(),other.getAreaC(xShift, yShift).getY(),other.getAreaC(xShift, yShift).getWidth(),other.getAreaC(xShift, yShift).getHeight()));
    }
}
