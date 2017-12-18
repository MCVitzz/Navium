package Utilities;

import processing.core.PApplet;

public class TimingManager {

    //This class handles all the timing differences there are between frames, this way the game's flow doesn't depend on FPS but in time elapsed between frames

    private float deltaTime, prevMillis, millis, heatTimestamp;

    public TimingManager(PApplet applet) {
        prevMillis = applet.millis();
    }

    public void update(PApplet applet) {
        millis = applet.millis();
        deltaTime = millis - prevMillis;
        prevMillis = millis;
    }

    public void setHeatTimestamp() {
        heatTimestamp = millis;
    }

    public float getTimeSinceLastTimestampHeat() {
        return millis - heatTimestamp;
    }

    public float getDeltaTime() {
        return deltaTime;
    }
}
