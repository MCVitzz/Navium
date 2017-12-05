package Abstract;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public abstract class CelestialBody {

    private PVector position;

    private float radius;

    public CelestialBody(PVector position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public CelestialBody(PVector position, PImage sprite) {
        this.position = position;
        this.radius = sprite.width;
    }

    public abstract void update();

    public abstract void display(PVector transformed, float scale, PApplet applet);

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        setPosition(new PVector(x, y, 0));
    }

    public float getRadius() {
        return this.radius;
    }

    public void setPosition(float x, float y, float z) {
        this.position = new PVector(x, y, z);
    }
}
