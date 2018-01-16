package utilities;


import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Pause {

    //This class handles all the Menu rendering and button clicking
    private PVector resumeBtn, quitBtn;

    public Pause(PApplet applet, float x, float parts, float resumePart, float quitPart) {
        //X is not necessary here, but just in case we'll use it later
        resumeBtn = new PVector(x, resumePart * applet.height / parts);
        quitBtn = new PVector(x, quitPart * applet.height / parts);
    }

    public void display(PVector mouse, PGraphics graphics, AssetManager assetManager) {
        String button = checkButtons(mouse);
        graphics.imageMode(graphics.CENTER);
        switch (button) {

            case "resume":
                graphics.image(assetManager.resumeHighlightedImage, resumeBtn.x, resumeBtn.y);
                graphics.pushMatrix();
                {
                    //graphics.scale(.5F);
                    graphics.image(assetManager.quit, quitBtn.x, quitBtn.y);
                }
                graphics.popMatrix();
                break;
            case "quit":
                graphics.image(assetManager.resumeImage, resumeBtn.x, resumeBtn.y);
                graphics.pushMatrix();
                {
                    //graphics.scale(.5F);
                    graphics.image(assetManager.quitHighlighted, quitBtn.x, quitBtn.y);
                }
                graphics.popMatrix();
                    break;
            default:
                graphics.image(assetManager.resumeImage, resumeBtn.x, resumeBtn.y);
                graphics.pushMatrix();
                {
                    //graphics.scale(.5F);
                    graphics.image(assetManager.quit, quitBtn.x, quitBtn.y);
                }
                graphics.popMatrix();
                break;
        }
    }

    public String checkButtons(PVector mouseVector) {
        String result = "";
        if (Math.abs(mouseVector.x - resumeBtn.x) <= 200 && Math.abs(mouseVector.y - resumeBtn.y) <= 100)
            result = "resume";
        if (Math.abs(mouseVector.x - quitBtn.x) <= 400 && Math.abs(mouseVector.y - quitBtn.y) <= 200)
            result = "quit";


        return result;
    }
}
