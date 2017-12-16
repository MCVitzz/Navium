import Celestials.Asteroid;
import Celestials.CelestialBody;
import Game.Camera;
import Game.Spaceship;
import Utilities.AssetManager;
import Utilities.Menu;
import Utilities.ResolutionManager;
import Utilities.TimingManager;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MainApp extends PApplet {

    private static final int STATE_MAIN_MENU = 0;
    private static final int STATE_RUNNING = 1;
    private static final int STATE_PAUSE_MENU = 2;
    private boolean up, down, left, right, zoomed, fuzzy, debug;
    private Camera camera;
    private int state;
    private Menu menu;
    private ArrayList<CelestialBody> celestialBodies;
    private TimingManager timingManager;
    private ResolutionManager resolutionManager;
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
        resolutionManager = new ResolutionManager(width, height);
        zoomed = fuzzy = debug = false;
        camera = new Camera(new PVector(0, 0, 0), resolutionManager.getResolvedOfWidth(100));
        timingManager = new TimingManager(this);
        setState(STATE_MAIN_MENU);
    }

    public void draw() {
        background(0);
        text("FPS: " + Float.toString(Math.round(frameRate)), resolutionManager.getResolvedOfWidth(20), resolutionManager.getResolvedOfHeight(20));
        switch (state) {
            case STATE_MAIN_MENU:
                menu.display(this.g, assetManager);
                break;
            case STATE_RUNNING:
                timingManager.update(this);
                spaceship.update(up, down, left, right, timingManager.getDeltaTime());
                camera.update(spaceship.getPosition());
                camera.apply(this);

                java.util.Collections.sort(celestialBodies);

                for (CelestialBody celestialBody : celestialBodies) {
                    if (fuzzy)
                        celestialBody.update();
                    celestialBody.display(camera, this, assetManager, spaceship.getFront(), debug);
                    if (celestialBody.getClass().getName().equals("Celestials.Asteroid") && celestialBody.passedBy(camera, spaceship.getFront())) {
                            celestialBody.setPosition(generateNewCelestialPosition());
                            celestialBody.setActive(true);
                        }
                    }

                resetMatrix();

                spaceship.drawCrosshair(this, assetManager);

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
        if(key == 'f')
            fuzzy = !fuzzy;
        if(key == 'b')
            debug = !debug;
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
            case STATE_RUNNING:
                if (mouseButton == RIGHT) {
                    if (zoomed)
                        zoomOut();
                    else
                        zoomIn();
                }
                else if(mouseButton == LEFT){

                    ArrayList<Asteroid> asteroids = new ArrayList<>();


                    for (CelestialBody celestialBody: celestialBodies)
                        if(celestialBody.getClass().getName().equals("Celestials.Asteroid"))
                            asteroids.add((Asteroid)celestialBody);


                    for (Asteroid asteroid : asteroids)
                        if (asteroid.inSight(this, assetManager, camera, resolutionManager, debug))
                        {
                            println("Right in the kisser.");
                            spaceship.increaseScore(50);
                            asteroid.setActive(false);
                            break;
                        }
                }
                break;
        }
    }

    private void setState(int state) {
        this.state = state;
        switch (this.state) {
            case STATE_MAIN_MENU:
                cursor();
                menu = new Menu(this, width / 2, 7, 1, 4, 6);
                if (celestialBodies != null)
                    celestialBodies.clear();
                else
                    celestialBodies = new ArrayList<>();
                spaceship = null;
                break;
            case STATE_RUNNING:
                noCursor();
                menu = null;
                spaceship = new Spaceship(width / 2, height / 2, 0, resolutionManager.getResolvedOfWidth(10), resolutionManager);
                camera = new Camera(spaceship.getPosition(), 100);
                createCelestials(spaceship.getCelestials());
                break;
            case STATE_PAUSE_MENU:
                cursor();
                break;

        }
    }

    private void createCelestials(int number) {
        celestialBodies = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            celestialBodies.add(new Asteroid(generateNewCelestialPosition(), this, resolutionManager));
        }
    }

    private PVector generateNewCelestialPosition(){
        float x = random(spaceship.getMinX(), spaceship.getMaxX()); //random(width / 8, 7 * width / 8);
        float y = random(spaceship.getMinY(), spaceship.getMaxY()); //random(height / 8, 7 * height / 8);
        float z = random(spaceship.getMinZ(), spaceship.getMaxZ()) + camera.getPosition().z; //random((width/height) * 500, (width/height) * 1500);
        return new PVector(x, y, z);
    }

    private void zoomIn() {
        camera.setDistance(camera.getDistance() + 100);
        zoomed = true;
    }

    private void zoomOut() {
        camera.setDistance(camera.getDistance() - 100);
        zoomed = false;
    }

}
