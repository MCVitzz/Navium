package celestials;

import game.Camera;
import utilities.AssetManager;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class CelestialBody extends PhysicsObject implements Comparable<CelestialBody> {

    //This class is a super class for all the bodies that can be displayed on the screen
    private boolean active;

    CelestialBody(PVector position, PVector velocity, PVector force, float mass) {
        super(position, velocity, force, mass);
        active(true);
    }

    public abstract void update(float deltaTime);

    public abstract void draw(PGraphics g, AssetManager manager);

    public void display(Camera camera, PApplet applet, AssetManager manager, float front, boolean debug) {

        PVector transformed = transformed(camera);

        if (transformed.z < front)
            return;

        applet.g.pushMatrix();
        {
            float scale = scale(camera.distance(), transformed.z);
            PVector projected = projected(transformed, scale);

            applet.g.translate(projected.x, projected.y);
            applet.g.scale(scale);
            applet.g.pushStyle();
            {
                draw(applet.g, manager);
                if (debug) {
                    applet.g.rectMode(applet.g.CENTER);
                    applet.g.rect(0, 0, manager.asteroidImage.width * scale, manager.asteroidImage.height * scale);
                }
            }
            applet.g.popStyle();
        }
        applet.g.popMatrix();
    }

    private PVector transformed(Camera camera) {
        return PVector.sub(position(), camera.position());
    }

    private PVector projected(PVector transformed, float scale) {
        return PVector.mult(transformed, scale);
    }

    public boolean passedBy(Camera camera, float front) {
        return PVector.sub(position(), camera.position()).z < front;
    }

    private float scale(float cameraDistance, float objectDistance) {
        return cameraDistance / objectDistance;
    }

    public int compareTo(CelestialBody other) {
        return Float.compare(other.position().z, this.position().z);
    }

    public boolean active() {
        return active;
    }

    public void active(boolean active) {
        this.active = active;
    }
}
