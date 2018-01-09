package utilities;

import processing.core.PApplet;

public class TimingManager {

    //This class handles all the timing differences there are between frames, this way the game's flow doesn't depend on FPS but in time elapsed between frames

    private float deltaTime, prevMillis, millis, heatTimestamp, shakeTimestamp;

    public TimingManager(PApplet applet) {
        prevMillis = applet.millis();
    }

    public void update(PApplet applet) {
        millis = applet.millis();
        deltaTime = millis - prevMillis;
        prevMillis = millis;
    }

    public void heatTimestamp() {
        heatTimestamp = millis;
    }

    public void shakingTimestamp() {
        shakeTimestamp = millis;
    }

    public float timeSinceLastTimestampHeat() {
        return millis - heatTimestamp;
    }

    public float timeSinceLastShakeTimestamp() {
        return millis - shakeTimestamp;
    }

    public float deltaTime() {
        return deltaTime;
    }
}
