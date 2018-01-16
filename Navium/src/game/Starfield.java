package game;

import utilities.ResolutionManager;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;

public class Starfield {

    private PVector velocity;
    private int numStars;
    private ArrayList<Star> stars;

    public Starfield(PApplet applet, ResolutionManager resolutionManager, int numStars) {
        this.numStars = numStars;
        velocity = new PVector(applet.random(2), applet.random(1));
        this.stars = new ArrayList<>();
        for (int i = 0; i < this.numStars; i++)
            stars.add(new Star(applet, resolutionManager));
    }

    public void update(PApplet applet, ResolutionManager resolutionManager, float deltaTime) {
        float delta = deltaTime * .001F;
        for (Star star : stars) {
            star.setPositionTo(star.position().add(PVector.mult(velocity, delta)));
            if (star.position().x > applet.width)
                star.setPositionTo(new PVector(0, applet.random(0, applet.height)));
            if (star.position().x < 0)
                star.setPositionTo(new PVector(applet.width, applet.random(0, applet.height)));
            if (star.position().y > applet.height)
                star.setPositionTo(new PVector(applet.random(0, applet.height), 0));
            if (star.position().y < 0)
                star.setPositionTo(new PVector(applet.random(0, applet.width), applet.height));

            star.resize(applet, resolutionManager);
        }
        for (int i = stars.size(); i < numStars; i++)
            stars.add(new Star(applet, resolutionManager));
    }

    public void display(PGraphics graphics) {
        for (Star star : stars)
            star.display(graphics);
    }

    public class Star {
        private float size;
        private PVector position;

        Star(PApplet applet, ResolutionManager resolutionManager) {
            this.size = applet.random(resolutionManager.resolvedOfWidth(1), resolutionManager.resolvedOfWidth(3));
            this.position = new PVector(applet.random(0, applet.width), applet.random(0, applet.height));
        }

        void display(PGraphics graphics) {
            graphics.pushMatrix();
            graphics.pushStyle();
            {
                graphics.translate(position().x, position().y);
                graphics.fill(255);
                graphics.ellipseMode(graphics.CENTER);
                graphics.ellipse(0, 0, size, size);
            }
            graphics.popStyle();
            graphics.popMatrix();
        }

        PVector position() {
            return this.position.copy();
        }

        void resize(PApplet applet, ResolutionManager resolutionManager) {
            this.size = applet.random(resolutionManager.resolvedOfWidth(1), resolutionManager.resolvedOfWidth(3));
        }

        void setPositionTo(PVector position) {
            this.position = position.copy();
        }
    }
}
