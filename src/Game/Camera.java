package Game;

import processing.core.PApplet;
import processing.core.PVector;

public class Camera {

    private float distance;
    private PVector position;

    public Camera(PVector position, float distance) {
        this.position = position.copy();
        this.distance = distance;
    }

    public void update(PVector newPos) {
        position = newPos.copy();
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
