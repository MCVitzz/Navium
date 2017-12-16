package Celestials;

import Utilities.ResolutionManager;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import Utilities.AssetManager;
import Game.Camera;

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

    public boolean inSight(PApplet applet, AssetManager manager, Camera camera, ResolutionManager resolutionManager, boolean debug)
    {
        PVector transformed = getTransformed(camera);
        float scale = getScale(camera.getDistance(), transformed.z);
        PVector projected = getProjected(transformed, scale);

        PVector projCenter = camera.project(new PVector(applet.width/2, applet.height/2, 0));

        float widTolerance = manager.asteroidImage.width * scale;
        float heiTolerance = manager.asteroidImage.height * scale;

        float dist = PVector.dist(projected, projCenter);
        float max = applet.max(widTolerance, heiTolerance);

        Boolean inSight = dist <= max;

        if (debug)
        {
            applet.g.rectMode(applet.g.CENTER);
            applet.g.rect(applet.width/2, applet.height / 2, resolutionManager.getResolvedOfWidth(50), resolutionManager.getResolvedOfWidth(50));
        }
        return inSight;
    }

    //    public void display(PVector transformed, float scale, PApplet applet, AssetManager assetManager) {
//        applet.g.pushMatrix();
//        {
//            applet.g.translate(applet.width / 2, applet.height / 2);
//            applet.g.imageMode(applet.g.CENTER);
//            applet.g.image(assetManager.asteroidImage, transformed.x, transformed.y);
//        }
//        applet.g.popMatrix();
//    }

//    public void draw(Camera camera, PApplet applet, AssetManager manager, float front) {
//        PVector transformed = PVector.sub(getPosition(), camera.getPosition());
//
//        if (transformed.z < front)
//            return;
//
//        applet.g.pushMatrix();
//        {
//            float scale = camera.getDistance() / transformed.z;
//            PVector projected = PVector.mult(transformed, scale);
//
//            applet.g.translate(projected.x, projected.y);
//            applet.g.scale(scale);
//            applet.g.pushStyle();
//            {
//                applet.g.imageMode(applet.g.CENTER);
//                applet.g.image(manager.asteroidImage, 0, 0);
//            }
//            applet.g.popStyle();
//        }
//        applet.g.popMatrix();
//    }
}
