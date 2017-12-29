package Celestials;

import Game.Camera;
import Utilities.AssetManager;
import Utilities.ResolutionManager;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Asteroid extends CelestialBody {

    //This class is a child of the CelestialBody super class and handles all the 'Asteroid specific' behaviour there may be

    private PVector velocity;

    public Asteroid(PVector position, PApplet applet, ResolutionManager resolutionManager) {
        super(position);
        velocity = new PVector(applet.random(resolutionManager.getResolvedOfWidth(-20), resolutionManager.getResolvedOfWidth(20)), applet.random(resolutionManager.getResolvedOfHeight(-20), resolutionManager.getResolvedOfHeight(20)), 0);
    }

    public void draw(PGraphics g, AssetManager manager) {
        g.imageMode(g.CENTER);
        g.image(manager.asteroidImage, 0, 0);
    }

    public void update() {
        setPosition(getPosition().add(velocity));
    }

    public boolean inSight(PApplet applet, AssetManager manager, Camera camera, ResolutionManager resolutionManager, boolean debug) {
        PVector transformed = getTransformed(camera);
        float scale = getScale(camera.getDistance(), transformed.z);
        PVector projected = getProjected(transformed, scale);

        PVector projCenter = camera.project(new PVector(applet.width / 2, applet.height / 2, 0));

        float widTolerance = manager.asteroidImage.width * scale + (manager.crosshair.width / 2);
        float heiTolerance = manager.asteroidImage.height * scale + (manager.crosshair.height / 2);

        float dist = PVector.dist(projected, projCenter);
        float max = PApplet.max(widTolerance, heiTolerance);

        Boolean inSight = dist <= max;

        if (debug) {
            applet.g.rectMode(applet.g.CENTER);
            applet.g.rect(applet.width / 2, applet.height / 2, resolutionManager.getResolvedOfWidth(50), resolutionManager.getResolvedOfWidth(50));
        }
        return inSight;
    }
}
