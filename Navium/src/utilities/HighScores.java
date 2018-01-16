package utilities;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class HighScores {

    private PVector backBtn;

    public HighScores(PApplet applet) {
        backBtn = new PVector(applet.width / 2, (applet.height / 20) * 18);
    }

    public void display(PApplet applet, AssetManager assetManager, ArrayList<SaveGame.HighScore> highScores, PVector mouseVector) {

        applet.g.pushMatrix();
        applet.g.pushStyle();
        {
            applet.g.textSize(72);
            applet.g.textAlign(PConstants.CENTER);
            applet.g.textFont(assetManager.hudFont);
            applet.g.text("Highscores", applet.width / 2, (applet.height / 8));
        }
        applet.popStyle();
        applet.popMatrix();

        applet.g.pushMatrix();
        applet.g.pushStyle();
        {
            applet.g.textSize(24);
            applet.g.textFont(assetManager.hudFont);
            for (int i = 0; i < highScores.size(); i++) {
                applet.g.pushStyle();
                {
                    applet.g.textAlign(PConstants.CENTER);
                    applet.g.text(highScores.get(i).score(), (applet.width / 2), (applet.height / 20) * (i + 6));
                }
                applet.popStyle();
            }
        }
        applet.popStyle();
        applet.popMatrix();

        applet.g.pushMatrix();
        applet.g.pushStyle();
        {
            applet.g.imageMode(PConstants.CENTER);
            if(checkButtons(mouseVector).equals("back"))
                applet.g.image(assetManager.backHighlighted, backBtn.x, backBtn.y);
            else
                applet.g.image(assetManager.back, backBtn.x, backBtn.y);
        }
        applet.popStyle();
        applet.popMatrix();
    }

    public String checkButtons(PVector mouseVector) {
        String result = "";
        if (Math.abs(mouseVector.x - backBtn.x) <= 400 && Math.abs(mouseVector.y - backBtn.y) <= 200)
            result = "back";
        return result;
    }

}
