package Utilities;

import processing.core.PApplet;
import processing.core.PImage;

public class AssetManager {
    public PImage asteroidImage, logoImage, playImage, quitImage;

    public AssetManager(PApplet applet){
        asteroidImage = applet.loadImage("asteroid.png");
        logoImage = applet.loadImage("title.png");
        playImage = applet.loadImage("play.png");
        quitImage = applet.loadImage("quit.png");
    }

}
