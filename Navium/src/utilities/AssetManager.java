package utilities;

import processing.core.PApplet;
import processing.core.PImage;

public class AssetManager {

    //This class handles all the assets in the game and keeps the in memory
    public PImage asteroidImage, logoImage, playImage, crosshair, laser;
    public Animation antiGravitationalPellet;

    public AssetManager(PApplet applet) {
        asteroidImage = applet.loadImage("asteroid.png");
        logoImage = applet.loadImage("title.png");
        playImage = applet.loadImage("play.png");
        crosshair = applet.loadImage("crosshair.png");
        laser = applet.loadImage("laser.png");
        antiGravitationalPellet = new Animation("antiGravitationalMissile", 6, applet);
    }

}
