package game;

import processing.core.PApplet;
import utilities.AssetManager;
import utilities.GameManager;
import utilities.ResolutionManager;

public class HUD {
    //This class handles the display of all the values that need to be shown on screen it won't hold such values, that is left for the Game Manager to do
    //Since it does not have any values it doesn't need to be instantiated, so it's kept static

    public static void drawCrosshair(PApplet applet, AssetManager manager) {
        applet.image(manager.crosshair, applet.width / 2, applet.height / 2);
    }

    public static void drawHealth(float health, PApplet applet, ResolutionManager resolutionManager) {
        applet.g.pushStyle();
        {
            applet.g.rectMode(applet.g.CORNER);
            applet.g.stroke(255, 0, 0);
            applet.g.noFill();
            //background bar of progress bar
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(50), resolutionManager.resolvedOfWidth(200), resolutionManager.resolvedOfHeight(15));
            applet.g.fill(255, 0, 0);
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(50), resolutionManager.resolvedOfWidth(health * 2), resolutionManager.resolvedOfHeight(15));
        }
        applet.g.popStyle();
    }

    public static void drawHeat(float heat, PApplet applet, ResolutionManager resolutionManager) {
        applet.g.pushStyle();
        {
            applet.g.rectMode(applet.g.CORNER);
            applet.g.stroke(255, 0, 0);
            applet.g.noFill();
            //Background bar of progress bar
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(90), resolutionManager.resolvedOfWidth(200), resolutionManager.resolvedOfHeight(15));
            applet.g.fill(255, 0, 0);
            gradient(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(90), resolutionManager.resolvedOfWidth(heat * 2), applet.color(255, 255, 255), applet.color((heat * 2 * 255) / 200, 0, 0), applet);
            applet.g.rect(resolutionManager.resolvedOfWidth(1700), resolutionManager.resolvedOfHeight(90), resolutionManager.resolvedOfWidth(heat * 2), resolutionManager.resolvedOfHeight(15));
        }
        applet.g.popStyle();
    }

    public static void drawScore(float score, PApplet applet, ResolutionManager resolutionManager) {
        applet.g.text("Score: " + Float.toString(score), resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(40));
    }

    public static void drawVariables(PApplet applet, GameManager gameManager, ResolutionManager resolutionManager) {

        //Here we draw the indicators to all the control variables

        applet.g.text("FPS: " + Float.toString(Math.round(applet.frameRate)), resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(20));

        drawIndicator(gameManager.debug(), "Debug", 60, applet, resolutionManager);

        drawIndicator(gameManager.zoomed(), "Zoomed", 80, applet, resolutionManager);

        drawIndicator(gameManager.fuzzy(), "Fuzzy", 100, applet, resolutionManager);

        drawIndicator(gameManager.stopped(), "Stopped", 120, applet, resolutionManager);

        drawIndicator(gameManager.god(), "God", 140, applet, resolutionManager);

        drawIndicator(gameManager.shaking(), "Shaking", 160, applet, resolutionManager);
    }

    private static void drawIndicator(boolean variable, String text, float y, PApplet applet, ResolutionManager resolutionManager) {
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

    private static void gradient(float x, float y, float w, int c1, int c2, PApplet applet) {
        for (float i = x; i <= x + w; i++) {
            float inter = PApplet.map(i, x, x + w, 0, 1);
            int c = applet.g.lerpColor(c1, c2, inter);
            applet.g.pushStyle();
            {
                applet.g.stroke(c);
                applet.g.line(i, y, i, y + 15);
            }
            applet.g.popStyle();
        }
    }
}
