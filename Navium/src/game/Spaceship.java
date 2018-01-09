package game;

import processing.core.PApplet;
import processing.core.PVector;
import utilities.AssetManager;
import utilities.GameManager;
import utilities.ResolutionManager;
import utilities.TimingManager;

public class Spaceship {

    //This class is equivalent to the 'Player' class in most games, it also holds some of the game's parameters

    private PVector position;
    private int score, heat;
    private float front, speed, controlSpeed, health, radiusX, radiusY, heatCooldown, rotation;

    public Spaceship(PVector position, float front, ResolutionManager resolutionManager) {
        this.position = position.copy();
        this.front = front;
        this.rotation = 0;
        this.radiusX = resolutionManager.resolvedOfWidth(400);
        this.radiusY = resolutionManager.resolvedOfHeight(225);
        this.speed = resolutionManager.resolvedOfWidth(2);
        this.controlSpeed = resolutionManager.resolvedOfHeight(2);
        this.heatCooldown = 1500;
        this.health = 100;
        this.score = this.heat = 0;
    }

    public void update(GameManager gameManager, boolean up, boolean down, boolean left, boolean right, boolean rotateLeft, boolean rotateRight, float deltaTime, boolean stopped) {

        float controlDistance = deltaTime * controlSpeed;

        float distance = deltaTime * speed;

        if (stopped)
            distance = 0;

        PVector velocity = new PVector(0, 0, distance);

        if (rotateLeft)
            rotation(rotation() + (3.14F / 8142) * deltaTime);
        if (rotateRight)
            rotation(rotation() - (3.14F / 8142) * deltaTime);
        if (left)
            velocity.x -= controlDistance;
        if (right)
            velocity.x += controlDistance;
        if (up)
            velocity.y -= controlDistance;
        if (down)
            velocity.y += controlDistance;

        position(position().add(velocity));

        if (position().x > (3 * gameManager.maxX() / 4))
            position(new PVector((3 * gameManager.maxX() / 4), position().y, position().z));
        if (position().x < (3 * gameManager.minX() / 4))
            position(new PVector((3 * gameManager.minX() / 4), position().y, position().z));
        if (position().y > (3 * gameManager.maxY() / 4))
            position(new PVector(position().x, (3 * gameManager.maxY() / 4), position().z));
        if (position().y < (3 * gameManager.minY() / 4))
            position(new PVector(position().x, (3 * gameManager.minY() / 4), position().z));
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
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(50), resolutionManager.resolvedOfWidth(200), resolutionManager.resolvedOfHeight(15)); //background bar of progress bar
            applet.g.fill(255, 0, 0);
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(50), resolutionManager.resolvedOfWidth(health * 2), resolutionManager.resolvedOfHeight(15));
        }
        applet.g.popStyle();
    }

    public void drawHeat(PApplet applet, ResolutionManager resolutionManager) {
        applet.g.pushStyle();
        {
            applet.g.rectMode(applet.g.CORNER);
            applet.g.stroke(255, 0, 0);
            applet.g.noFill();
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(90), resolutionManager.resolvedOfWidth(200), resolutionManager.resolvedOfHeight(15)); //background bar of progress bar
            applet.g.fill(255, 0, 0);
            gradient(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(90), resolutionManager.resolvedOfWidth(heat * 2), applet.color(255, 255, 255), applet.color((heat * 2 * 255) / 200, 0, 0), applet);
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(90), resolutionManager.resolvedOfWidth(heat * 2), resolutionManager.resolvedOfHeight(15));
        }
        applet.g.popStyle();
    }

    private void gradient(float x, float y, float w, int c1, int c2, PApplet applet) {
        for (float i = x; i <= x + w; i++) {
            float inter = PApplet.map(i, x, x + w, 0, 1);
            int c = applet.g.lerpColor(c1, c2, inter);
            applet.g.pushStyle();
            {
                applet.g.stroke(c);
                applet.g.line(i, y, i, y + 15);
            }
            applet.g.popStyle();
        }
    }

    private void rotation(float rotation) {
        this.rotation = rotation;
    }

    private float rotation() {
        return this.rotation;
    }

    public PVector position() {
        return position;
    }

    private void position(PVector position) {
        this.position = position;
    }

    public float front() {
        return this.front;
    }

    public void increaseScore(int increase) {
        this.score += increase;
    }

    public int score() {
        return score;
    }

    public float health() {
        return health;
    }

    public void health(float newHealth) {
        this.health = newHealth;
    }

    public float radiusX() {
        return radiusX;
    }

    public float radiusY() {
        return radiusY;
    }

    public int heat() {
        return heat;
    }

    public void heat(int heat, TimingManager timingManager) {
        timingManager.heatTimestamp();
        this.heat = heat;
        if (this.heat <= 0)
            this.heat = 0;
    }

    public float heatCooldown() {
        return heatCooldown;
    }
}
