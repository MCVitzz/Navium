package game;

import processing.core.PApplet;
import processing.core.PVector;
import utilities.ResolutionManager;

public class Camera {

    //This class handles all the Camera activity there may be

    private float distance, shakeCooldown;
    private PVector position;

    public Camera(PVector position, float distance) {
        this.position = position.copy();
        this.distance = distance;
        this.shakeCooldown = 300;
    }

    public void update(PVector newPos) {
        position = newPos.copy();
    }

    public void shake(PApplet applet, ResolutionManager resolutionManager, PVector base) {
        float x = applet.random(resolutionManager.resolvedOfWidth(-50), resolutionManager.resolvedOfWidth(50));
        float y = applet.random(resolutionManager.resolvedOfHeight(-50), resolutionManager.resolvedOfHeight(50));

        update(PVector.add(new PVector(x, y), base));
    }

    public void apply(PApplet applet) {
        //applet.g.translate(applet.width / 2, applet.height / 2);
        applet.g.translate(applet.width / 2, applet.height / 2);
    }

    public float distance() {
        return distance;
    }

    public void setDistanceTo(float distance) {
        this.distance = distance;
    }

    public PVector position() {
        return this.position;
    }

    public float shakeCooldown() {
        return shakeCooldown;
    }
}
