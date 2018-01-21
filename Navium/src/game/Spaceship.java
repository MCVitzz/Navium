package game;

import processing.core.PVector;
import utilities.GameManager;
import utilities.ResolutionManager;
import utilities.TimingManager;

public class Spaceship {

    //This class is equivalent to the 'Player' class in most games

    private PVector position;
    private int heat, missiles;
    private float front, speed, controlSpeed, health, radiusX, radiusY, heatCooldown, rotation;

    public Spaceship(PVector position, float front, ResolutionManager resolutionManager) {
        this.position = position.copy();
        this.front = front;
        this.rotation = 0;
        this.radiusX = resolutionManager.resolvedOf(400);
        this.radiusY = resolutionManager.resolvedOf(225);
        this.speed = resolutionManager.resolvedOf(2);
        this.controlSpeed = resolutionManager.resolvedOf(2);
        this.heatCooldown = 1500;
        this.health = 100;
        this.heat = 0;
    }

    public void update(GameManager gameManager,  float deltaTime) {

        float controlDistance = deltaTime * controlSpeed;

        float distance = deltaTime * speed;

        if (gameManager.isStopped())
            distance = 0;

        PVector velocity = new PVector(0, 0, distance);

        if (gameManager.rotateLeft())
            setRotationTo(rotation() + (3.14F / 8142) * deltaTime);
        if (gameManager.rotateRight())
            setRotationTo(rotation() - (3.14F / 8142) * deltaTime);
        if (gameManager.left())
            velocity.x -= controlDistance;
        if (gameManager.right())
            velocity.x += controlDistance;
        if (gameManager.up())
            velocity.y -= controlDistance;
        if (gameManager.down())
            velocity.y += controlDistance;

        setPositionTo(PVector.add(position(), velocity));

        if (position().x > (3 * gameManager.maxX() / 4))
            setPositionTo(new PVector((3 * gameManager.maxX() / 4), position().y, position().z));
        if (position().x < (3 * gameManager.minX() / 4))
            setPositionTo(new PVector((3 * gameManager.minX() / 4), position().y, position().z));
        if (position().y > (3 * gameManager.maxY() / 4))
            setPositionTo(new PVector(position().x, (3 * gameManager.maxY() / 4), position().z));
        if (position().y < (3 * gameManager.minY() / 4))
            setPositionTo(new PVector(position().x, (3 * gameManager.minY() / 4), position().z));
    }

    private void setRotationTo(float rotation) {
        this.rotation = rotation;
    }

    private float rotation() {
        return this.rotation;
    }

    public PVector position() {
        return position;
    }

    private void setPositionTo(PVector position) {
        this.position = position;
    }

    public float front() {
        return this.front;
    }

    public int missiles() {
        return this.missiles;
    }

    public void setMissilesTo(int missiles) {
        this.missiles = missiles;
    }

    public float health() {
        return health;
    }

    public void setHealthTo(float newHealth) {
        this.health = newHealth;
        if (health() > 100)
            health = 100;
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

    public void setHeatTo(int heat, TimingManager timingManager) {
        timingManager.heatTimestamp();
        this.heat = heat;
        if (this.heat <= 0)
            this.heat = 0;
    }

    public float heatCooldown() {
        return heatCooldown;
    }
}
