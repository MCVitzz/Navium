package game;

import utilities.ResolutionManager;
import processing.core.PApplet;
import processing.core.PVector;

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

    public PVector project(PVector position) {
        PVector transformed = PVector.sub(position, getPosition());
        float scale = distance / transformed.z;
        return PVector.mult(transformed, scale);
    }

    public void shake(PApplet applet, ResolutionManager resolutionManager, PVector base) {
//        float x = applet.random(resolutionManager.getResolvedOfWidth(-70), resolutionManager.getResolvedOfWidth(70));
//        float y = applet.random(resolutionManager.getResolvedOfHeight(-70), resolutionManager.getResolvedOfHeight(70));
//
//        update(PVector.add(new PVector(x, y), base));
//        apply(applet);

        applet.g.pushStyle();
        {
            applet.g.fill(applet.color(255, 0, 0, 50));
            applet.g.rect(0, 0, applet.width, applet.height);
        }
        applet.g.popStyle();

    }

    public void apply(PApplet applet) {
        //applet.g.translate(applet.width / 2, applet.height / 2);
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

    public float getShakeCooldown() {
        return shakeCooldown;
    }
}
