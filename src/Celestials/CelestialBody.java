package Celestials;

import Game.Camera;
import Utilities.AssetManager;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class CelestialBody implements Comparable<CelestialBody> {

    //This class is a super class for all the bodies that can be displayed on the screen

    private PVector position;
    private boolean active;

    CelestialBody(PVector position) {
        setPosition(position.copy());
        setActive(true);
    }

    public abstract void update();

    public abstract void draw(PGraphics g, AssetManager manager);

    public void display(Camera camera, PApplet applet, AssetManager manager, float front, boolean debug) {

        PVector transformed = getTransformed(camera);

        if (transformed.z < front)
            return;

        applet.g.pushMatrix();
        {
            float scale = getScale(camera.getDistance(), transformed.z);
            PVector projected = getProjected(transformed, scale);

            applet.g.translate(projected.x, projected.y);
            applet.g.scale(scale);
            applet.g.pushStyle();
            {
                draw(applet.g, manager);
                if (debug)
                {
                    applet.g.rectMode(applet.g.CENTER);
                    applet.g.rect(0, 0, manager.asteroidImage.width * scale, manager.asteroidImage.height * scale);
                }
            }
            applet.g.popStyle();
        }
        applet.g.popMatrix();
    }

    PVector getTransformed(Camera camera){
        return PVector.sub(getPosition(), camera.getPosition());
    }

    PVector getProjected(PVector transformed, float scale){
        return PVector.mult(transformed, scale);
    }

    public boolean passedBy(Camera camera, float front){
        return PVector.sub(getPosition(), camera.getPosition()).z < front;
    }

    public void setPosition(PVector position) {
        this.position = position.copy();
    }

    float getScale(float cameraDistance, float objectDistance){
        return cameraDistance / objectDistance;
    }

    public int compareTo(CelestialBody other) {
        return Float.compare(other.position.z, this.position.z);
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean getActive(){
        return  active;
    }

    public PVector getPosition() {
        return position;
    }
}
