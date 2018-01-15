package utilities;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Menu {

    //This class handles all the Menu rendering and button clicking

    private PVector playBtn, titleTitle;

    public Menu(PApplet applet, float x, float parts, float titlePart, float playPart) {

        //X is not necessary here, but just in case we'll use it later
        titleTitle = new PVector(x, titlePart * applet.height / parts);
        playBtn = new PVector(x, playPart * applet.height / parts);
    }

    public void display(PGraphics graphics, AssetManager assetManager) {
        graphics.imageMode(graphics.CENTER);
        //Draw Title
        graphics.image(assetManager.logoImage, titleTitle.x, titleTitle.y);
        //Draw Play
        graphics.image(assetManager.playImage, playBtn.x, playBtn.y);
    }

    public String checkButtons(PVector mouseVector) {
        int pixelRadius = 100;
        String result = "";
        if (playBtn.dist(mouseVector) <= pixelRadius)
            result = "play";

        return result;
    }
}