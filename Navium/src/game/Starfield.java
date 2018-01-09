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
        velocity = new PVector(applet.random(resolutionManager.getResolvedOfWidth(1), resolutionManager.getResolvedOfWidth(8)), applet.random(resolutionManager.getResolvedOfHeight(1), resolutionManager.getResolvedOfHeight(8)));
        this.stars = new ArrayList<>();
        for (int i = 0; i < this.numStars; i++)
            stars.add(new Star(applet, resolutionManager));
    }

    public void update(PApplet applet, ResolutionManager resolutionManager, float deltaTime) {
        float delta = deltaTime * .001F;
        for (Star star : stars) {
            star.setPosition(star.getPosition().add(velocity.copy().mult(delta)));
            if (star.getPosition().x > applet.width)
                star.setPosition(new PVector(0, applet.random(0, applet.height)));
            if (star.getPosition().x < 0)
                star.setPosition(new PVector(applet.width, applet.random(0, applet.height)));
            if (star.getPosition().y > applet.height)
                star.setPosition(new PVector(applet.random(0, applet.height), 0));
            if (star.getPosition().y < 0)
                star.setPosition(new PVector(applet.random(0, applet.width), applet.height));
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
            this.size = applet.random(resolutionManager.getResolvedOfWidth(1), resolutionManager.getResolvedOfWidth(3));

            this.position = new PVector(applet.random(0, applet.width), applet.random(0, applet.height));
        }

        void display(PGraphics graphics) {
            graphics.pushMatrix();
            graphics.pushStyle();
            {
                graphics.translate(getPosition().x, getPosition().y);
                graphics.fill(255);
                graphics.ellipseMode(graphics.CENTER);
                graphics.ellipse(0, 0, size, size);
            }
            graphics.popStyle();
            graphics.popMatrix();
        }

        PVector getPosition() {
            return this.position.copy();
        }

        void setPosition(PVector position) {
            this.position = position.copy();
        }
    }
}
