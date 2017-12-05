import Abstract.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MainApp extends PApplet {

    private static final int STATE_MAIN_MENU = 0;
    private static final int STATE_RUNNING = 1;
    private static final int STATE_PAUSE_MENU = 2;

    private int state;
    private Menu menu;
    private ArrayList<CelestialBody> celestialBodies;
    private Spaceship spaceship;

    public static void main(String[] args) {
        PApplet.main("MainApp", args);
    }

    public void settings() {
        fullScreen(P2D);
    }

    public void setup() {
        background(0);
        frameRate(120);
        setState(STATE_MAIN_MENU);
    }

    public void draw() {
        background(0);
        text("FPS: " + Float.toString(Math.round(frameRate)), 20, 20);
        switch (state) {
            case STATE_MAIN_MENU:
                menu.display(this.g);
                break;
            case STATE_RUNNING:
                Camera cam = spaceship.getCamera();
                for (CelestialBody celestialBody : celestialBodies) {
                    PVector pos = cam.transformationOf(celestialBody.getPosition());
                    float scale = cam.getScaleOf(celestialBody.getRadius(), celestialBody.getPosition().z);
                    celestialBody.display(pos, scale, this);
                }
                break;
        }
    }

    private void setState(int state) {
        this.state = state;
        switch (this.state) {
            case STATE_MAIN_MENU:
                menu = new Menu(this, width / 2, 7, 1, 4, 6);
                if (celestialBodies != null)
                    celestialBodies.clear();
                else
                    celestialBodies = new ArrayList<>();
                spaceship = null;
                break;
            case STATE_RUNNING:
                menu = null;
                spaceship = new Spaceship(width / 2, height / 2, 0, this);
                createCelestials(spaceship.getCelestials());
                break;
            case STATE_PAUSE_MENU:
                break;

        }
    }

    public void mousePressed() {
        switch (state) {
            case STATE_MAIN_MENU:
                String result = menu.checkButtons(mouseX, mouseY);

                switch (result) {
                    case "play":
                        setState(STATE_RUNNING);
                        break;
                    case "quit":
                        exit();
                        break;
                }
                break;

        }
    }

    private void createCelestials(int number) {
        celestialBodies = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            float x = random(width / 8, 7 * width / 8);
            float y = random(height / 8, 7 * height / 8);
            celestialBodies.add(new Asteroid(x, y, 2000, this));
        }
    }
}
