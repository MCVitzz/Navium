package utilities;


import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Menu {

    //This class handles all the Menu rendering and button clicking
    private PVector playBtn, highScorebtn, title;

    public Menu(PApplet applet, float x, float parts, float titlePart, float buttonsPart) {
        //X is not necessary here, but just in case we'll use it later
        title = new PVector(x, titlePart * applet.height / parts);
        playBtn = new PVector(applet.width / 8 * 2, buttonsPart * applet.height / parts);
        highScorebtn = new PVector(applet.width / 8 * 6, buttonsPart * applet.height / parts);
    }

    public void display(PVector mouse, PGraphics graphics, AssetManager assetManager) {
        graphics.pushStyle();
        {
            String button = checkButtons(mouse);
            graphics.imageMode(graphics.CENTER);
            graphics.image(assetManager.logoImage, title.x, title.y);
            switch (button) {
                case "play":
                    graphics.image(assetManager.playHighlighted, playBtn.x, playBtn.y);
                    graphics.image(assetManager.highScores, highScorebtn.x, highScorebtn.y);
                    break;
                case "highScores":
                    graphics.image(assetManager.play, playBtn.x, playBtn.y);
                    graphics.image(assetManager.highScoresHighlighted, highScorebtn.x, highScorebtn.y);
                default:
                    graphics.image(assetManager.play, playBtn.x, playBtn.y);
                    graphics.image(assetManager.highScores, highScorebtn.x, highScorebtn.y);
                    break;
            }
        }
        graphics.popStyle();
    }

    public String checkButtons(PVector mouseVector) {
        String result = "";
        if (Math.abs(mouseVector.x - playBtn.x) <= 400 && Math.abs(mouseVector.y - playBtn.y) <= 200)
            result = "play";
        if (Math.abs(mouseVector.x - highScorebtn.x) <= 650 && Math.abs(mouseVector.y - highScorebtn.y) <= 200)
            result = "highScores";
        return result;
    }
}
