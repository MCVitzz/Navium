package Game;

import Utilities.ResolutionManager;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Star {
    private float size;
    private PVector position;

    Star (PApplet applet, ResolutionManager resolutionManager) {
        this.size = applet.random(resolutionManager.getResolvedOfWidth(1), resolutionManager.getResolvedOfWidth(3));

        this.position = new PVector(applet.random(resolutionManager.getResolvedOfWidth(0), resolutionManager.getResolvedOfWidth(1920)), applet.random(resolutionManager.getResolvedOfHeight(0), resolutionManager.getResolvedOfHeight(1920)));
    }

    public void display(PGraphics graphics) {
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

    public void setPosition(PVector position) {
        this.position = position.copy();
    }

    public PVector getPosition() {
        return this.position;
    }
}
