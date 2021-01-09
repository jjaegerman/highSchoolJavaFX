package sample.Sprites;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;

public interface Sprite {

    public void update(double time);

    public void render(GraphicsContext gc);

    public Circle getBoundary();

    public boolean intersects(BaseObject s);
}
