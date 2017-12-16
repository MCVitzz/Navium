package Utilities;

import processing.core.PApplet;

public class TimingManager {

    //This class handles all the timing differences there are between frames, this way the game's flow doesn't depend on FPS but in time elapsed between frames

    private float deltaTime, prevMillis;

    public TimingManager(PApplet applet) {
        prevMillis = applet.millis();
    }

    public void update(PApplet applet) {
        float millis = applet.millis();
        deltaTime = millis - prevMillis;
        prevMillis = millis;
    }

    public float getDeltaTime() {
        return  deltaTime;
    }
}
