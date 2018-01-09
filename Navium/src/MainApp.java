import celestials.Asteroid;
import celestials.CelestialBody;
import celestials.LaserBomb;
import game.Camera;
import game.Spaceship;
import game.Starfield;
import processing.core.PApplet;
import processing.core.PVector;
import utilities.*;

import java.util.ArrayList;

public class MainApp extends PApplet {

    private TimingManager timingManager;
    private ResolutionManager resolutionManager;
    private ArrayList<CelestialBody> celestialBodies;
    private Spaceship spaceship;
    private Camera camera;
    private Menu menu;
    private GameManager gameManager;
    private AssetManager assetManager;
    private Starfield starfield;

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
        gameManager = new GameManager(resolutionManager);
        starfield = new Starfield(this, resolutionManager, Math.round(resolutionManager.resolvedOfWidth(1200)));
        timingManager = new TimingManager(this);
        setState(States.MAIN_MENU);
        camera = new Camera(new PVector(0, 0, 0), resolutionManager.resolvedOfWidth(100));
    }

    public void draw() {
        background(0);
        switch (gameManager.state()) {
            case MAIN_MENU:
                timingManager.update(this);
                starfield.update(this, resolutionManager, timingManager.deltaTime());
                starfield.display(this.g);
                text("FPS: " + Float.toString(Math.round(frameRate)), resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(20));
                menu.display(this.g, assetManager);
                break;

            case RUNNING:
                timingManager.update(this);
                starfield.update(this, resolutionManager, timingManager.deltaTime());
                starfield.display(this.g);
                spaceship.update(gameManager, gameManager.up(), gameManager.down(), gameManager.left(), gameManager.right(), gameManager.rotateLeft(), gameManager.rotateRight(), timingManager.deltaTime(), gameManager.stopped());
                spaceship.increaseScore(Math.round(timingManager.deltaTime() * .1F));
                camera.update(spaceship.position());
                camera.apply(this);

                java.util.Collections.sort(celestialBodies);

                for (CelestialBody celestialBody : celestialBodies) {
                    if (gameManager.fuzzy())
                        celestialBody.update();

                    if (celestialBody.getActive())
                        celestialBody.display(camera, this, assetManager, spaceship.front(), gameManager.debug());

                    if (celestialBody.getClass().getName().equals("celestials.Asteroid") && celestialBody.passedBy(camera, spaceship.front())) {
                        boolean betweenX = isBetween(celestialBody.getPosition().x, spaceship.position().x - spaceship.radiusX(), spaceship.position().x + spaceship.radiusX());
                        boolean betweenY = isBetween(celestialBody.getPosition().y, spaceship.position().y - spaceship.radiusY(), spaceship.position().y + spaceship.radiusY());

                        if (betweenX && betweenY && !gameManager.god()) {
                            spaceship.health(spaceship.health() - 10);
                            setShaking(true);
                        }
                        celestialBody.setPosition(generateNewCelestialPosition());
                        celestialBody.setActive(true);
                    }
                }

                if (timingManager.timeSinceLastTimestampHeat() >= spaceship.heatCooldown())
                    spaceship.heat(spaceship.heat() - 2, timingManager);

                ArrayList<Asteroid> asteroids = new ArrayList<>();

                for (CelestialBody celestialBody : celestialBodies)
                    if (celestialBody.getClass().getName().equals("celestials.Asteroid"))
                        asteroids.add((Asteroid) celestialBody);

                //Sorting the asteroids and then reverse the order, if it's closer it'll be handled first

                java.util.Collections.sort(asteroids);
                java.util.Collections.reverse(asteroids);

                ArrayList<CelestialBody> buffer = new ArrayList<>();

                for (CelestialBody celestialBody : celestialBodies)
                    if (celestialBody.getClass().getName().equals("celestials.LaserBomb")) {
                        celestialBody.update();
                        if (celestialBody.getPosition().z > spaceship.position().z + gameManager.maxZ())
                            buffer.add(celestialBody);
                        for (Asteroid asteroid : asteroids)
                            if (((LaserBomb) celestialBody).collidingWith(resolutionManager, asteroid)) {
                                spaceship.increaseScore(50);
                                asteroid.setActive(false);
                                buffer.add(celestialBody);
                                break;
                            }
                    }

                celestialBodies.removeAll(buffer);

                resetMatrix();
                if (gameManager.shaking()) {
                    if (timingManager.timeSinceLastShakeTimestamp() < camera.shakeCooldown())
                        camera.shake(this/*, resolutionManager, spaceship.getPosition()*/);
                    else
                        setShaking(false);
                }

                spaceship.drawHealth(this, resolutionManager);
                spaceship.drawHeat(this, resolutionManager);
                spaceship.drawCrosshair(this, assetManager);
                text("Score: " + Float.toString(spaceship.score()), resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(40));

                if (gameManager.indicators())
                    showVariables();

                if (spaceship.health() <= 0)
                    setState(States.GAME_OVER);
                break;

            case GAME_OVER:
                textMode(CENTER);
                textSize(15);
                text("GAME OVER", width / 2, height / 2);
                break;
        }
    }

    public void keyPressed() {
        if (key == 'a')
            gameManager.left(true);
        if (key == 'd')
            gameManager.right(true);
        if (key == 'w')
            gameManager.up(true);
        if (key == 's')
            gameManager.down(true);
        if (key == 'q')
            gameManager.rotateLeft(true);
        if (key == 'e')
            gameManager.rotateRight(true);
        if (key == 'f')
            gameManager.fuzzy(!gameManager.fuzzy());
        if (key == 'b')
            gameManager.debug(!gameManager.debug());
        if (key == 'z')
            gameManager.stopped(!gameManager.stopped());
        if (key == 'i')
            gameManager.indicators(!gameManager.indicators());
        if (key == 'g')
            gameManager.god(!gameManager.god());
    }

    public void keyReleased() {
        if (key == 'a')
            gameManager.left(false);
        if (key == 'd')
            gameManager.right(false);
        if (key == 'w')
            gameManager.up(false);
        if (key == 's')
            gameManager.down(false);
        if (key == 'q')
            gameManager.rotateLeft(false);
        if (key == 'e')
            gameManager.rotateRight(false);
    }

    public void mousePressed() {
        switch (gameManager.state()) {
            case MAIN_MENU:
                //Check if any button is clicked
                String result = menu.checkButtons(mouseX, mouseY);

                //If it is execute the action corresponding to that button
                if (!result.isEmpty()) {
                    switch (result) {
                        case "play":
                            setState(States.RUNNING);
                            break;
                        case "quit":
                            exit();
                            break;
                    }
                    break;
                }
            case RUNNING:
                if (mouseButton == RIGHT) {
                    if (gameManager.zoomed())
                        zoomOut();
                    else
                        zoomIn();
                } else if (mouseButton == LEFT) {
                    if (spaceship.heat() <= 98 || gameManager.god()) {

                        //Create Laser
                        celestialBodies.add(new LaserBomb(spaceship.position().copy(), resolutionManager));

                        //Increase the heat
                        if (!gameManager.god())
                            spaceship.heat(spaceship.heat() + 2, timingManager);
                    }
                }
                break;

            case GAME_OVER:
                setState(States.MAIN_MENU);
                break;
        }
    }

    private void showVariables() {

        //Here we draw the indicators to all the control variables

        text("FPS: " + Float.toString(Math.round(frameRate)), resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(20));

        drawIndicator(gameManager.debug(), "Debug", 60);

        drawIndicator(gameManager.zoomed(), "Zoomed", 80);

        drawIndicator(gameManager.fuzzy(), "Fuzzy", 100);

        drawIndicator(gameManager.stopped(), "Stopped", 120);

        drawIndicator(gameManager.god(), "God", 140);

        drawIndicator(gameManager.shaking(), "Shaking", 160);
    }

    //This method handles all the behaviour related to the change that is about to be made
    private void setState(States state) {
        gameManager.state(state);
        switch (gameManager.state()) {
            case MAIN_MENU:
                cursor();
                menu = new Menu(this, width / 2, 6, 3, 5);
                if (celestialBodies != null)
                    celestialBodies.clear();
                else
                    celestialBodies = new ArrayList<>();
                spaceship = null;
                break;
            case RUNNING:
                noCursor();
                menu = null;
                spaceship = new Spaceship(new PVector(width / 2, height / 2, 0), resolutionManager.resolvedOfWidth(10), resolutionManager);
                camera = new Camera(spaceship.position(), 100);
                createCelestials(gameManager.asteroids());
                gameManager.zoomed(false);
                gameManager.indicators(false);
                gameManager.fuzzy(false);
                gameManager.debug(false);
                gameManager.stopped(false);
                break;
            case PAUSE:
                cursor();
                break;
            case GAME_OVER:
                cursor();
                spaceship = new Spaceship(new PVector(width / 2, height / 2, 0), resolutionManager.resolvedOfWidth(10), resolutionManager);
                celestialBodies.clear();
                break;

        }
    }

    private void createCelestials(int number) {
        celestialBodies = new ArrayList<>();
        for (int i = 0; i < number; i++)
            celestialBodies.add(new Asteroid(generateNewCelestialPosition(), this, resolutionManager));
    }

    private void drawIndicator(boolean variable, String text, float y) {
        int tru = color(0, 255, 0);
        int flse = color(255, 0, 0);

        int drawColor;

        if (variable)
            drawColor = tru;
        else
            drawColor = flse;

        pushStyle();
        {
            fill(drawColor);
            text(text, resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(y));
        }
        popStyle();

    }

    private void zoomIn() {
        camera.distance(camera.distance() + 100);
        gameManager.zoomed(true);
    }

    private void zoomOut() {
        camera.distance(camera.distance() - 100);
        gameManager.zoomed(false);
    }

    private PVector generateNewCelestialPosition() {
        float x = random(gameManager.minX(), gameManager.maxX());
        float y = random(gameManager.minY(), gameManager.maxY());
        float z = random(gameManager.minZ(), gameManager.maxZ()) + camera.position().z;
        return new PVector(x, y, z);
    }

    private boolean isBetween(float number, float min, float max) {
        //In this block we just make sure that the minimum and max value are indeed the minimum and the maximum values, respectively
        float buffer = max(min, max);
        min = min(min, max);
        max = buffer;

        return min < number && number < max;
    }

    private void setShaking(boolean shake) {
        timingManager.shakingTimestamp();
        gameManager.shaking(shake);
    }
}
