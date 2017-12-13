package Celestials;

import processing.core.PGraphics;
import processing.core.PVector;
import Utilities.AssetManager;

public class Asteroid extends CelestialBody {

    public Asteroid(float x, float y, float z) {
        super(new PVector(x, y, z));
    }

    public void draw(PGraphics g, AssetManager manager) {
        g.imageMode(g.CENTER);
        g.image(manager.asteroidImage, 0, 0);
    }

    public void update() {
        this.setPosition(this.getPosition().add(new PVector(0, 0, -1)));
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
