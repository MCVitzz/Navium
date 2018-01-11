package celestials;

import processing.core.PGraphics;
import processing.core.PVector;
import utilities.AssetManager;
import utilities.ResolutionManager;

public class LaserBomb extends CelestialBody {

    //private PVector prevPosition;

    public LaserBomb(PVector position, ResolutionManager resolutionManager) {
        super(position.copy(), new PVector(0, 0, resolutionManager.resolvedOfWidth(100)), new PVector(), Float.MIN_VALUE);
        //this.prevPosition = position.copy();
    }

    public void update(float deltaTime) {
        //prevPosition = position().copy();
        updatePhysics(deltaTime);
    }

    public void draw(PGraphics graphics, AssetManager assetManager) {
        graphics.pushStyle();
        {
            graphics.imageMode(graphics.CENTER);
            graphics.image(assetManager.laser, position().x, position().y);
        }
    }

    public boolean isCollidingWith(ResolutionManager resolutionManager, CelestialBody celestialBody) {
        //boolean isBetweenPositionsZ = PVector.dist(celestialBody.position(), prevPosition) + PVector.dist(celestialBody.position(), position()) == PVector.dist(prevPosition, position());
        //boolean isInHitRadius = PVector.dist(new PVector(position().x, position().y), new PVector(celestialBody.position().x, celestialBody.position().y)) <= resolutionManager.resolvedOfWidth(300);
        return PVector.dist(position(), celestialBody.position()) <= resolutionManager.resolvedOfWidth(300);
    }
}
