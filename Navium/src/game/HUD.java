package game;

import processing.core.PApplet;
import processing.core.PConstants;
import utilities.AssetManager;
import utilities.GameManager;
import utilities.ResolutionManager;

import java.text.DecimalFormat;

public class HUD {
    //This class handles the display of all the values that need to be shown on screen it won't hold such values, that is left for the Game Manager/Spaceship to do

    public void drawCrosshair(PApplet applet, AssetManager manager) {
        applet.image(manager.crosshair, applet.width / 2, applet.height / 2);
    }

    public void drawCockpit(PApplet applet, AssetManager assetManager) {
        applet.g.pushStyle();
        applet.g.pushMatrix();
        {
            applet.g.translate(0, 0);
            applet.g.imageMode(PConstants.CENTER);
            applet.g.image(assetManager.cockpit, applet.width / 2, applet.height / 2);
        }
        applet.popMatrix();
        applet.popStyle();
    }

    public void drawHealth(float health, PApplet applet, ResolutionManager resolutionManager, AssetManager assetManager) {
        applet.g.pushMatrix();
        applet.g.pushStyle();
        {
            applet.g.stroke(0, 255, 255, 33);
            applet.g.fill(127, 255, 232, 95);
            applet.g.textFont(assetManager.hudFont);
            applet.g.text("Health", 1280, 940);
            applet.g.rectMode(applet.g.CORNER);
            applet.g.fill(127, 255, 232, 20);
            applet.g.rect(resolutionManager.resolvedOfWidth(1280), resolutionManager.resolvedOfHeight(950), resolutionManager.resolvedOfWidth(350), resolutionManager.resolvedOfHeight(25));
            applet.g.fill(applet.color(0, 255, 210), 50);
            applet.g.rect(resolutionManager.resolvedOfWidth(1280), resolutionManager.resolvedOfHeight(950), resolutionManager.resolvedOfWidth((health * 350) / 100), resolutionManager.resolvedOfHeight(25));
        }
        applet.g.popStyle();
        applet.g.popMatrix();
    }

    public void drawHeat(float heat, PApplet applet, ResolutionManager resolutionManager, AssetManager assetManager) {
        applet.g.pushMatrix();
        applet.g.pushStyle();
        {
            applet.g.stroke(255, 0, 0, 33);
            applet.g.fill(127, 0, 0, 95);
            applet.g.textFont(assetManager.hudFont);
            applet.g.text("Heat", 1280, 1015);
            applet.g.rectMode(applet.g.CORNER);
            applet.g.fill(255, 50, 50, 20);
            applet.g.rect(resolutionManager.resolvedOfWidth(1280), resolutionManager.resolvedOfHeight(1025), resolutionManager.resolvedOfWidth(350), resolutionManager.resolvedOfHeight(25));
            applet.g.fill(applet.color(255, 0, 0), 50);
            applet.g.rect(resolutionManager.resolvedOfWidth(1280), resolutionManager.resolvedOfHeight(1025), resolutionManager.resolvedOfWidth((heat * 350) / 100), resolutionManager.resolvedOfHeight(25));
        }
        applet.g.popStyle();
        applet.g.popMatrix();
    }

    public void drawScore(float score, PApplet applet, ResolutionManager resolutionManager, AssetManager assetManager) {
        applet.g.pushMatrix();
        applet.g.pushStyle();
        {
            applet.g.stroke(255, 255, 255, 33);
            applet.g.fill(127, 127, 127, 95);
            applet.g.textFont(assetManager.hudFont);
            applet.g.text("Score", resolutionManager.resolvedOfWidth(290), resolutionManager.resolvedOfHeight(990));
            applet.g.text(new DecimalFormat("#.#").format(score), resolutionManager.resolvedOfWidth(290), resolutionManager.resolvedOfHeight(1025));
        }
        applet.g.popStyle();
        applet.g.popMatrix();
    }

    public void drawVariables(PApplet applet, GameManager gameManager, ResolutionManager resolutionManager) {

        //Here we draw the indicators to all the control variables

        applet.g.text("FPS: " + Float.toString(Math.round(applet.frameRate)), resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(20));

        drawIndicator(gameManager.isZoomed(), "Zoomed", 80, applet, resolutionManager);

        drawIndicator(gameManager.isFuzzy(), "Fuzzy", 100, applet, resolutionManager);

        drawIndicator(gameManager.isStopped(), "Stopped", 120, applet, resolutionManager);

        drawIndicator(gameManager.god(), "God", 140, applet, resolutionManager);

        drawIndicator(gameManager.isShaking(), "Shaking", 160, applet, resolutionManager);
    }

    private void drawIndicator(boolean variable, String text, float y, PApplet applet, ResolutionManager resolutionManager) {
        int tru = applet.g.color(0, 255, 0);
        int flse = applet.g.color(255, 0, 0);

        int drawColor;

        if (variable)
            drawColor = tru;
        else
            drawColor = flse;

        applet.g.pushStyle();
        {
            applet.g.fill(drawColor);
            applet.g.text(text, resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(y));
        }
        applet.g.popStyle();

    }

    public void drawHit(PApplet applet, AssetManager manager) {
        applet.image(manager.hit, applet.width / 2, applet.height / 2);
    }
    private void gradient(float x, float y, float w, int c1, int c2, PApplet applet) {
        for (float i = x; i <= x + w; i++) {
            float inter = PApplet.map(i, x, x + w, 0, 1);
            int c = applet.g.lerpColor(c1, c2, inter);
            applet.g.pushStyle();
            {
                applet.g.stroke(c);
                applet.g.line(i, y, i, y + 45);
            }
            applet.g.popStyle();
        }
    }
}
