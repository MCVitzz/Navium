package celestials;

import utilities.AssetManager;
import utilities.ResolutionManager;
import processing.core.PGraphics;
import processing.core.PVector;

public class LaserBomb extends CelestialBody {

    private PVector prevPosition;

    private float speed;

    public LaserBomb(PVector position, ResolutionManager resolutionManager) {
        super(position.copy());
        this.prevPosition = position.copy();
        this.speed = resolutionManager.resolvedOfWidth(200);
    }

    public void update() {
        prevPosition = getPosition().copy();
        setPosition(getPosition().copy().add(0, 0, speed));
    }

    public void draw(PGraphics graphics, AssetManager assetManager) {
        graphics.pushStyle();
        {
            graphics.imageMode(graphics.CENTER);
            graphics.image(assetManager.laser, getPosition().x, getPosition().y);
        }
    }

    public boolean collidingWith(ResolutionManager resolutionManager, Asteroid asteroid) {
        return getPosition().dist(asteroid.getPosition()) < resolutionManager.resolvedOfWidth(300);
    }
}
