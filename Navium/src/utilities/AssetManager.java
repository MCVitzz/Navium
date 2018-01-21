package utilities;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class AssetManager {

    //This class handles all the assets in the game and keeps the in memory
    public PImage regularAsteroidImage, bigAsteroidImage, smallAsteroidImage, resumeImage, resumeHighlightedImage, logoImage, play, playHighlighted, highScores, back, backHighlighted, healthPowerUp, missilePowerUp, highScoresHighlighted, quit, quitHighlighted, crosshair, laser, cockpit, hit;
    public Animation antiGravitationalPellet;
    public PFont hudFont;

    public AssetManager(PApplet applet) {
        hudFont = applet.loadFont("bioliquid.vlw");
        hit = applet.loadImage("hit.png");
        smallAsteroidImage = applet.loadImage("smallAsteroid.png");
        resumeHighlightedImage = applet.loadImage("resumeButtonHighlighted.png");
        healthPowerUp = applet.loadImage("healthPowerUp.png");
        missilePowerUp = applet.loadImage("missilePowerUp.png");
        resumeImage = applet.loadImage("resumeButton.png");
        regularAsteroidImage = applet.loadImage("regularAsteroid.png");
        highScores = applet.loadImage("highScores.png");
        highScoresHighlighted = applet.loadImage("highScoresHighlighted.png");
        back = applet.loadImage("back.png");
        backHighlighted = applet.loadImage("backHighlighted.png");
        quit = applet.loadImage("quit.png");
        quitHighlighted = applet.loadImage("quitHighlighted.png");
        bigAsteroidImage = applet.loadImage("bigAsteroid.png");
        logoImage = applet.loadImage("title.png");
        play = applet.loadImage("play.png");
        playHighlighted = applet.loadImage("playHighlighted.png");
        crosshair = applet.loadImage("crosshair.png");
        laser = applet.loadImage("laser.png");
        antiGravitationalPellet = new Animation("antiGravitationalMissile", 6, applet);
        cockpit = applet.loadImage("cockpit.png");
    }

}
