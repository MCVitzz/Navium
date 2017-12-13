package Game;

import processing.core.PApplet;
import processing.core.PVector;

public class Spaceship {
    private PVector position;
    private int celestials;
    private float front;
    private float speed = 25;

    public Spaceship(float x, float y, float z, float front, PApplet applet) {
        position = new PVector(x, y, z);
        celestials = 10000;
        this.front = front;
    }

    public void update(boolean up, boolean down, boolean left, boolean right) {

        PVector velocity = new PVector(0, 0, speed);

        if (left)
            velocity.x -= speed;
        if (right)
            velocity.x += speed;
        if (up)
            velocity.y -= speed;
        if (down)
            velocity.y += speed;

        setPosition(getPosition().add(velocity));
    }

    public void setPositon(float x, float y, float z) {
        setPosition(new PVector(x, y, z));
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public int getCelestials() {
        return celestials;
    }

    public void setCelestials(int celestials) {
        this.celestials = celestials;
    }

    public float getFront() {
        return this.front;
    }
}
