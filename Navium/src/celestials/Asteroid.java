package celestials;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import utilities.AssetManager;
import utilities.ResolutionManager;

public class Asteroid extends CelestialBody {
    //This class is a child of the CelestialBody super class and handles all the 'Asteroid specific' behaviour there may be

    private Size size;
    private float health;

    public Asteroid(PVector position, PApplet applet, ResolutionManager resolutionManager, Size size) {
        super(position, new PVector(applet.random(resolutionManager.resolvedOf(-0), resolutionManager.resolvedOf(0)), applet.random(resolutionManager.resolvedOf(-0), resolutionManager.resolvedOf(0)), 0), new PVector(), 10F);
        this.size = size;
        switch (this.size) {
            case BIG:
                this.health = 2;
                break;
            case REGULAR:
                this.health = 1;
                break;
            case SMAlL:
                this.health = 1;
                break;
        }
    }

    public void draw(PGraphics g, AssetManager manager) {
        g.imageMode(g.CENTER);
        switch (this.size) {
            case BIG:
                g.image(manager.bigAsteroidImage, 0, 0);
                break;
            case REGULAR:
                g.image(manager.regularAsteroidImage, 0, 0);
                break;
            case SMAlL:
                g.image(manager.smallAsteroidImage, 0, 0);
                break;
        }
    }

    public void update(float deltaTime) {
        updatePhysics(deltaTime);
    }

    public float health() {
        return health;
    }

    public void setHealthTo(float health) {
        this.health = health;

        if (this.health < 0)
            this.health = 0;

    }

    public Size size() {
        return this.size;
    }

    public enum Size {
        BIG,
        REGULAR,
        SMAlL
    }
}
