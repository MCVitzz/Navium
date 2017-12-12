import Abstract.CelestialBody;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Asteroid extends CelestialBody {

    private PImage sprite;

    Asteroid(PVector position, PApplet applet) {
        super(position, applet.loadImage("asteroid.png"));
        sprite = applet.loadImage("asteroid.png");
    }

    Asteroid(float x, float y, float z, PApplet applet) {
        super(new PVector(x, y, z), applet.loadImage("asteroid.png"));
        sprite = applet.loadImage("asteroid.png");
    }

    public void display(PVector transformed, float scale, PApplet applet) {
        applet.g.pushMatrix();
            applet.g.scale(scale);
            applet.g.translate(applet.width / 2, applet.height / 2);
            applet.g.imageMode(applet.g.CENTER);
            applet.g.image(sprite, transformed.x, transformed.y);
        applet.g.popMatrix();
    }

    public void update() {
        this.setPosition(this.getPosition().add(new PVector(0,0, -1)));
    }
}
    