package Celestials;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import Utilities.AssetManager;
import Game.Camera;

public abstract class CelestialBody implements Comparable<CelestialBody> {

    private PVector position;

    public CelestialBody(PVector position) {
        this.position = position;
    }

    public abstract void update();

    public void display(Camera camera, PApplet applet, AssetManager manager, float front) {

        PVector transformed = PVector.sub(getPosition(), camera.getPosition());

        if (transformed.z < front)
            return;

        applet.g.pushMatrix();
        {
            float scale = camera.getDistance() / transformed.z;
            PVector projected = PVector.mult(transformed, scale);

            applet.g.translate(projected.x, projected.y);
            applet.g.scale(scale);
            applet.g.pushStyle();
            {
                draw(applet.g, manager);
            }
            applet.g.popStyle();
        }
        applet.g.popMatrix();
    }

    public abstract void draw(PGraphics g, AssetManager manager);

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public int compareTo(CelestialBody other) {
        if (this.position.z < other.position.z)
            return 1;
        if (this.position.z > other.position.z)
            return -1;
        return 0;
    }
}
