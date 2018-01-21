package celestials;

import processing.core.PGraphics;
import processing.core.PVector;
import utilities.AssetManager;
import utilities.ResolutionManager;

public class LaserBomb extends CelestialBody {

    public LaserBomb(PVector position, ResolutionManager resolutionManager) {
        super(position, new PVector(0, 0, resolutionManager.resolvedOf(150)), new PVector(), Float.MIN_VALUE);
    }

    public void update(float deltaTime) {
        updatePhysics(deltaTime);
    }

    public void draw(PGraphics graphics, AssetManager assetManager) {
        graphics.pushStyle();
        {
            graphics.imageMode(graphics.CENTER);
            graphics.image(assetManager.laser,0, 0);
        }
        graphics.popStyle();
    }
}
