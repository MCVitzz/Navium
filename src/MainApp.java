import Celestials.Asteroid;
import Celestials.CelestialBody;
import Game.Camera;
import Game.Spaceship;
import Utilities.AssetManager;
import Utilities.Menu;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MainApp extends PApplet {

    private static final int STATE_MAIN_MENU = 0;
    private static final int STATE_RUNNING = 1;
    private static final int STATE_PAUSE_MENU = 2;
    boolean up, down, left, right;
    private Camera camera;
    private int state;
    private Menu menu;
    private ArrayList<CelestialBody> celestialBodies;
    private Spaceship spaceship;
    private AssetManager assetManager;

    public static void main(String[] args) {
        PApplet.main(new String[]{MainApp.class.getName()});
    }

    public void settings() {
        fullScreen(P2D);
    }

    public void setup() {
        background(0);
        frameRate(120);
        assetManager = new AssetManager(this);
        camera = new Camera(new PVector(0, 0, 0), 100);
        setState(STATE_MAIN_MENU);
    }

    public void draw() {
        background(0);
        text("FPS: " + Float.toString(Math.round(frameRate)), 20, 20);
        switch (state) {
            case STATE_MAIN_MENU:
                menu.display(this.g, assetManager);
                break;
            case STATE_RUNNING:
                spaceship.update(up, down, left, right);
                camera.update(spaceship.getPosition());
                camera.apply(this);

                java.util.Collections.sort(celestialBodies);

                for (CelestialBody celestialBody : celestialBodies) {
                    //celestialBody.update();
                    celestialBody.display(camera, this, assetManager, spaceship.getFront());
                }
                break;
        }
    }

    public void keyPressed() {
        if (key == 'a')
            left = true;
        if (key == 'd')
            right = true;
        if (key == 'w')
            up = true;
        if (key == 's')
            down = true;
    }

    public void keyReleased() {
        if (key == 'a')
            left = false;
        if (key == 'd')
            right = false;
        if (key == 'w')
            up = false;
        if (key == 's')
            down = false;
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
                spaceship = new Spaceship(width / 2, height / 2, 0, 10, this);
                camera = new Camera(spaceship.getPosition(), 100);
                createCelestials(spaceship.getCelestials());
                break;
            case STATE_PAUSE_MENU:
                break;

        }
    }

    private void createCelestials(int number) {
        celestialBodies = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            //new PVector(, , )
            float x = random(-10000, 10000); //random(width / 8, 7 * width / 8);
            float y = random(-10000, 10000); //random(height / 8, 7 * height / 8);
            float z = random(500, 15000); //random((width/height) * 500, (width/height) * 1500);
            celestialBodies.add(new Asteroid(x, y, z));
        }
    }
}
