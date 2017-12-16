package Game;

import Utilities.AssetManager;
import Utilities.ResolutionManager;
import processing.core.PApplet;
import processing.core.PVector;

public class Spaceship {

    //This class is equivalent to the 'Player' class in most games, it also holds some of the game's parameters

    private PVector position;
    private int celestials, score;
    private float front, maxX, minX, maxY, minY, maxZ, minZ, speed, controlSpeed, health, radiusX, radiusY;

    public Spaceship(PVector position, float front, ResolutionManager resolutionManager) {
        this.position = position.copy();
        this.celestials = Math.round(resolutionManager.getResolvedOfWidth(1000));
        this.front = front;
        this.radiusX = resolutionManager.getResolvedOfWidth(1650);
        this.radiusY = resolutionManager.getResolvedOfHeight(928);
        maxX = resolutionManager.getResolvedOfWidth(10000);
        minX = resolutionManager.getResolvedOfWidth(-10000);
        maxY = resolutionManager.getResolvedOfHeight(10000);
        minY = resolutionManager.getResolvedOfHeight(-10000);
        maxZ = resolutionManager.getResolvedOfWidth(15000);
        minZ = resolutionManager.getResolvedOfWidth(500);
        speed = resolutionManager.getResolvedOfWidth(2);
        controlSpeed = resolutionManager.getResolvedOfWidth(2);
        score = 0;
        health = 100;
    }

    public void update(boolean up, boolean down, boolean left, boolean right, float deltaTime, boolean stopped) {

        float controlDistance = deltaTime * controlSpeed;

        float distance = deltaTime * speed;

        if (stopped)
            distance = 0;

        PVector velocity = new PVector(0, 0, distance);

        if (left)
            velocity.x -= controlDistance;
        if (right)
            velocity.x += controlDistance;
        if (up)
            velocity.y -= controlDistance;
        if (down)
            velocity.y += controlDistance;

        setPosition(getPosition().add(velocity));

        if (getPosition().x > (3 * maxX / 4))
            setPosition(new PVector((3 * maxX / 4), getPosition().y, getPosition().z));
        if (getPosition().x < (3 * minX / 4))
            setPosition(new PVector((3 * minX / 4), getPosition().y, getPosition().z));
        if (getPosition().y > (3 * maxY / 4))
            setPosition(new PVector(getPosition().x, (3 * maxY / 4), getPosition().z));
        if (getPosition().y < (3 * minY / 4))
            setPosition(new PVector(getPosition().x, (3 * minY / 4), getPosition().z));
    }

    public void drawCrosshair(PApplet applet, AssetManager manager) {
        applet.image(manager.crosshair, applet.width / 2, applet.height / 2);
    }

    public void drawHealth(PApplet applet, ResolutionManager resolutionManager) {
        applet.g.pushStyle();
        {
            applet.g.rectMode(applet.g.CORNER);
            applet.g.stroke(255, 0, 0);
            applet.g.noFill();
            applet.g.rect(resolutionManager.getResolvedOfWidth(1700), resolutionManager.getResolvedOfHeight(50), resolutionManager.getResolvedOfWidth(200), resolutionManager.getResolvedOfHeight(15)); //background bar of progress bar
            applet.g.fill(255, 0, 0);
            applet.g.rect(resolutionManager.getResolvedOfWidth(1700), resolutionManager.getResolvedOfHeight(50), resolutionManager.getResolvedOfWidth(health * 2), resolutionManager.getResolvedOfHeight(15));
        }
        applet.g.popStyle();
    }

    public PVector getPosition() {
        return position;
    }

    private void setPosition(PVector position) {
        this.position = position;
    }

    public int getCelestials() {
        return celestials;
    }

    public float getFront() {
        return this.front;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public float getMinX() {
        return minX;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxZ() {
        return maxZ;
    }

    public float getMinZ() {
        return minZ;
    }

    public void increaseScore(int increase) {
        this.score += increase;
    }

    public int getScore() {
        return score;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float newHealth) {
        this.health = newHealth;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public float getRadiusY() {
        return radiusY;
    }
}
