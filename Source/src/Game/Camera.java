package Game;

import processing.core.PApplet;
import processing.core.PVector;

public class Camera {

    //This class handles all the Camera activity there may be

    private float distance;
    private PVector position;

    public Camera(PVector position, float distance) {
        this.position = position.copy();
        this.distance = distance;
    }

    public void update(PVector newPos) {
        position = newPos.copy();
    }

    public PVector project(PVector position){
        PVector transformed = PVector.sub(position, getPosition());
        float scale = distance / transformed.z;
        return  PVector.mult(transformed, scale);
    }

    public void apply(PApplet applet) {
        applet.g.translate(applet.width / 2, applet.height / 2);
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public PVector getPosition() {
        return this.position;
    }
}
