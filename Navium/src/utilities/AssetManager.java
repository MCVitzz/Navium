package utilities;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class AssetManager {

    //This class handles all the assets in the game and keeps the in memory
    public PImage regularAsteroidImage, bigAsteroidImage, smallAsteroidImage, logoImage, playImage, crosshair, laser, cockpit, hit;
    public Animation antiGravitationalPellet;
    public PFont hudFont;

    public AssetManager(PApplet applet) {
        hudFont = applet.loadFont("bioliquid.vlw");
        hit = applet.loadImage("hit.png");
        smallAsteroidImage = applet.loadImage("smallAsteroid.png");
        regularAsteroidImage = applet.loadImage("regularAsteroid.png");
        bigAsteroidImage = applet.loadImage("bigAsteroid.png");
        logoImage = applet.loadImage("title.png");
        playImage = applet.loadImage("play.png");
        crosshair = applet.loadImage("crosshair.png");
        laser = applet.loadImage("laser.png");
        antiGravitationalPellet = new Animation("antiGravitationalMissile", 6, applet);
        cockpit = applet.loadImage("cockpit.png");
    }

}
