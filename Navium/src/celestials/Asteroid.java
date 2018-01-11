package celestials;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import utilities.AssetManager;
import utilities.ResolutionManager;

public class Asteroid extends CelestialBody {
    //This class is a child of the CelestialBody super class and handles all the 'Asteroid specific' behaviour there may be

    public Asteroid(PVector position, PApplet applet, ResolutionManager resolutionManager) {
        super(position, new PVector(applet.random(resolutionManager.resolvedOfWidth(-0), resolutionManager.resolvedOfWidth(0)), applet.random(resolutionManager.resolvedOfWidth(-0), resolutionManager.resolvedOfWidth(0)), 0), new PVector(), 10F);
    }

    public void draw(PGraphics g, AssetManager manager) {
        g.imageMode(g.CENTER);
        g.image(manager.asteroidImage, 0, 0);
    }

    public void update(float deltaTime) {
        updatePhysics(deltaTime);
    }
}
