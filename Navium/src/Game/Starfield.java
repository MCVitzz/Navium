package Game;

import Utilities.ResolutionManager;
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
        ArrayList<Star> buffer = new ArrayList<>();
        for (Star star : stars) {
            star.setPosition(star.getPosition().add(velocity.copy().mult(delta)));
            if (star.getPosition().x > applet.width || star.getPosition().x < 0 || star.getPosition().y > applet.height || star.getPosition().y < 0)
                buffer.add(star);
        }
        stars.removeAll(buffer);
        for (int i = stars.size(); i < numStars; i++)
            stars.add(new Star(applet, resolutionManager));
    }

    public void display(PGraphics graphics) {
        for (Star star : stars)
            star.display(graphics);
    }
}
