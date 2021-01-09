package sample.Sprites;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import sample.Controller;

import java.beans.PropertyChangeListener;

public class BaseObject implements Sprite {
    Image image;
    public DoubleProperty posX= new SimpleDoubleProperty();
    public DoubleProperty posY= new SimpleDoubleProperty();
    private double velX=0.0;
    private double velY=0.0;
    private double accX=0.0;
    private double accY=9.8;
    public double radius;
    private double eConst;
    private double mass;

    public BaseObject(double x, double y, double eC, double size, String thing, double m) {
        mass=m;
        eConst=eC;
            image = new Image((this.getClass().getResource("Images/" + thing + ".png").toExternalForm()), size, size, false, false);
        radius=size;
        posX.setValue(x-(radius/2.0));
        posX.addListener((v, oldValue, newValue) -> {
            if(newValue.doubleValue()<(0.0)){
                posX.setValue(0.0);
                velX=((Math.sqrt(velX*velX+2.0*accX*((oldValue.doubleValue())/200.0))-velX)+velX)*Math.sqrt(eConst);
                }
                if(newValue.doubleValue()>(1100-radius)){
                posX.setValue(1100-radius);
                velX=-((Math.sqrt(velX*velX+2.0*accX*(1100-radius-oldValue.doubleValue())/200.0)-velX)+velX)*Math.sqrt(eConst);
                }
        });
        posY.setValue(y-(radius/2.0));
        posY.addListener((v, oldValue, newValue) -> {
            if(newValue.doubleValue()>(750.5-radius)){
                posY.setValue(750.5-radius);
                velY=-((Math.sqrt(velY*velY+2.0*accY*(750.5-radius-oldValue.doubleValue())/200.0)-velY)+velY)*Math.sqrt(eConst);
            }
            if(newValue.doubleValue()<0.0){
                posY.setValue(0.0);
                velY=((Math.sqrt(velY*velY+2.0*accY*(oldValue.doubleValue())/200.0)-velY)+velY)*Math.sqrt(eConst);
            }
        });
    }

    @Override
    public void update(double time) {
        velY+=accY*time;
        posY.setValue(posY.getValue()+velY*time*200.0);
        velX+=accX*time;
        posX.setValue(posX.getValue()+velX*time*200.0);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image,posX.getValue(),posY.getValue());
    }

    @Override
    public Circle getBoundary() {
        return new Circle((posX.getValue()+radius/2),(posY.getValue()+radius/2),radius/2);
    }

    public boolean intersects(BaseObject s) {
        double xDif = (this.posX.getValue()+(this.radius/2.0)-(s.posX.getValue()+(s.radius/2.0)));
        double yDif = (this.posY.getValue()+(this.radius/2.0)-(s.posY.getValue()+(s.radius/2.0)));
        if((xDif*xDif)+(yDif*yDif)<(this.radius+s.radius)*(this.radius+s.radius)/4){
            return true;
        }else{
            return false;
        }
    }


    public void collision(BaseObject s){
        double xDif = Math.abs((this.posX.getValue()+(this.radius/2.0)-(s.posX.getValue()+s.radius/2.0))/200.0);
        double yDif = Math.abs((this.posY.getValue()+(this.radius/2.0)-(s.posY.getValue()+s.radius/2.0))/200.0);
        double velX1;
        double velY1;
        double velX2;
        double velY2;
        if(s.velX<this.velX&&this.velX-s.velX<this.velX){
            velX2=-s.velX;
            velX1=this.velX;
        }else if(s.velX>this.velX&&this.velX-s.velX>this.velX){
            velX2=s.velX;
            velX1=-this.velX;
        }else if(this.velX<s.velX&&s.velX-this.velX<s.velX){
            velX2=s.velX;
            velX1=-this.velX;
        }else if(this.velX>s.velX&&s.velX-this.velX>s.velX){
            velX2=-s.velX;
            velX1=this.velX;
        }else{
            velX1=Math.abs(this.velX);
            velX2=Math.abs(s.velX);
        }
        if(s.velY<this.velY&&this.velY-s.velY<this.velY){
            velY2=-s.velY;
            velY1=this.velY;
        }else if(s.velY>this.velY&&this.velY-s.velY>this.velY){
            velY2=s.velY;
            velY1=-this.velY;
        }else if(this.velY<s.velY&&s.velY-this.velY<s.velY){
            velY2=s.velY;
            velY1=-this.velY;
        }else if(this.velY>s.velY&&s.velY-this.velY>s.velY){
            velY2=-s.velY;
            velY1=this.velY;
        }else{
            velY2=Math.abs(s.velY);
            velY1=Math.abs(this.velY);
        }
        double a = (velX1*velX1)+(velY1*velY1)+(velX2*velX2)+(velY2*velY2)+2*((velX2*velX1)+(velY2*velY1));
        double b = -2*(+(xDif*velX1)+(yDif*velY1)+(xDif*velX2)+(yDif*velY2));
        double c = (xDif*xDif)+(yDif*yDif)-(((s.radius+this.radius)/400)*((s.radius+this.radius)/400));
        Double tempTime = (-b-Math.sqrt((b*b)+(-4*a*c)))/(2*a);

        //interaction with wall fix

        if(this.posX.getValue()+(this.velX*tempTime*200.0)<0){
            s.posX.set(s.posX.getValue()-(this.velX*tempTime*200.0+this.posX.getValue()));
            this.posX.set(0.0);
        }else if(this.posX.getValue()+(this.velX*tempTime*200.0)>1100-this.radius){
            s.posX.set(s.posX.getValue()-(this.velX*tempTime*200-(1100-this.radius-this.posX.getValue())));
            this.posX.set(1100-this.radius);
        }else{
            this.posX.set(this.posX.getValue()+(this.velX*tempTime*200.0));
        }
        if(this.posY.getValue()+(this.velY*tempTime*200.0)<0){
            s.posY.set(s.posY.getValue()-(this.velY*tempTime*200.0+this.posY.getValue()));
            this.posX.set(0.0);
        }else if(this.posY.getValue()+(this.velY*tempTime*200.0)>750.5-this.radius){
            s.posY.set(s.posY.getValue()-(this.velY*tempTime*200-(750.5-this.radius-this.posY.getValue())));
            this.posY.set(750.5-this.radius);
        }else{
            this.posY.set(this.posY.getValue()+(this.velY*tempTime*200.0));
        }
        if(s.posX.getValue()+(s.velX*tempTime*200.0)<0){
            this.posX.set(this.posX.getValue()-(s.velX*tempTime*200.0+s.posX.getValue()));
            s.posX.set(0.0);
        }else if(s.posX.getValue()+(s.velX*tempTime*200.0)>1100-s.radius){
            this.posX.set(this.posX.getValue()-(s.velX*tempTime*200-(1100-s.radius-s.posX.getValue())));
            s.posX.set(1100-s.radius);
        }else{
            s.posX.set(s.posX.getValue()+(s.velX*tempTime*200.0));
        }
        if(s.posY.getValue()+(s.velY*tempTime*200.0)<0){
            this.posY.set(this.posY.getValue()-(s.velY*tempTime*200.0+s.posY.getValue()));
            s.posX.set(0.0);
        }else if(s.posY.getValue()+(s.velY*tempTime*200.0)>750.5-s.radius){
            this.posY.set(this.posY.getValue()-(s.velY*tempTime*200-(750.5-s.radius-s.posY.getValue())));
            s.posY.set(750.5-s.radius);
        }else{
            s.posY.set(s.posY.getValue()+(s.velY*tempTime*200.0));
        }
        this.velX+=tempTime*this.accX;
        this.velY+=tempTime*this.accY;
        s.velX+=tempTime*s.accX;
        s.velY+=tempTime*s.accY;
        double sVelX=s.velX;
        double sVelY=s.velY;
        double thisVelX= this.velX;
        double thisVelY= this.velY;
        xDif = (this.posX.getValue()+(this.radius/2.0)-(s.posX.getValue()+s.radius/2.0))/200.0;
        yDif = (this.posY.getValue()+(this.radius/2.0)-(s.posY.getValue()+s.radius/2.0))/200.0;

        System.out.println((this.radius+s.radius)/400+" "+Math.sqrt(xDif*xDif+yDif*yDif));

        sVelX=Math.signum(sVelX)*Math.sqrt(sVelX*sVelX*(xDif*xDif/((yDif*yDif)+(xDif*xDif))));
        sVelY=Math.signum(sVelY)*Math.sqrt(sVelY*sVelY*(yDif*yDif/((yDif*yDif)+(xDif*xDif))));
        thisVelX=Math.signum(thisVelX)*Math.sqrt(thisVelX*thisVelX*(xDif*xDif/((yDif*yDif)+(xDif*xDif))));
        thisVelY=Math.signum(thisVelY)*Math.sqrt(thisVelY*thisVelY*(yDif*yDif/((yDif*yDif)+(xDif*xDif))));

        s.velX-=sVelX;
        s.velY-=sVelY;
        this.velX-=thisVelX;
        this.velY-=thisVelY;

        s.velY+=(-Math.signum(yDif)*Math.sqrt(thisVelX*thisVelX*(xDif*xDif/((yDif*yDif)+(xDif*xDif))))+Math.signum(thisVelY)*Math.sqrt(thisVelY*thisVelY*(yDif*yDif/((yDif*yDif)+(xDif*xDif)))))*this.mass/s.mass;
        s.velX+=(Math.signum(thisVelX)*Math.sqrt(thisVelX*thisVelX*(xDif*xDif/((yDif*yDif)+(xDif*xDif))))-Math.signum(xDif)*Math.sqrt(thisVelY*thisVelY*(yDif*yDif/((yDif*yDif)+(xDif*xDif)))))*this.mass/s.mass;
        this.velY+=(Math.signum(yDif)*Math.sqrt(sVelX*sVelX*(xDif*xDif/((yDif*yDif)+(xDif*xDif))))+Math.signum(sVelY)*Math.sqrt(sVelY*sVelY*(yDif*yDif/((yDif*yDif)+(xDif*xDif)))))*s.mass/this.mass;
        this.velX+=(Math.signum(sVelX)*Math.sqrt(sVelX*sVelX*(xDif*xDif/((yDif*yDif)+(xDif*xDif))))+Math.signum(xDif)*Math.sqrt(sVelY*sVelY*(yDif*yDif/((yDif*yDif)+(xDif*xDif)))))*s.mass/this.mass;
        System.out.println(sVelY*sVelY+sVelX*sVelX+thisVelY*thisVelY+thisVelX*thisVelX);
    }
}
