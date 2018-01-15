import celestials.AntiGravitationalMissile;
import celestials.Asteroid;
import celestials.CelestialBody;
import celestials.LaserBomb;
import game.Camera;
import game.HUD;
import game.Spaceship;
import game.Starfield;
import processing.core.PApplet;
import processing.core.PVector;
import utilities.*;

import java.util.ArrayList;

public class MainApp extends PApplet {

    private TimingManager timingManager;
    private ResolutionManager resolutionManager;
    private Spaceship spaceship;
    private Camera camera;
    private Menu menu;
    private HUD hud;
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
                //Updating the Timing Manager, so that the game has a refreshed millis() count
                timingManager.update(this);
                //Updating the starfield and displaying it
                starfield.update(this, resolutionManager, timingManager.deltaTime());
                starfield.display(this.g);
                //Displaying the FPS if the game's indicators are enabled
                if (gameManager.indicators())
                    text("FPS: " + Float.toString(Math.round(frameRate)), resolutionManager.resolvedOfWidth(20), resolutionManager.resolvedOfHeight(20));
                //Displaying the Menu itself
                menu.display(this.g, assetManager);
                break;

            case RUNNING:
                //Updating the Timing Manager, so that the game has a refreshed millis() count
                timingManager.update(this);
                //Updating the starfield and displaying it
                starfield.update(this, resolutionManager, timingManager.deltaTime());
                starfield.display(this.g);

                //Updating the spaceship's position to the required one by the player and the game's speed.
                spaceship.update(gameManager, gameManager.up(), gameManager.down(), gameManager.left(), gameManager.right(), gameManager.rotateLeft(), gameManager.rotateRight(), timingManager.deltaTime(), gameManager.isStopped());

                //Increasing the player's score
                gameManager.increaseScore(Math.round(timingManager.deltaTime() * .1F));

                //Update camera to the spaceship's position
                camera.update(spaceship.position());
                //If the camera is shaking keep it shaking until shake time is over
                if (gameManager.isShaking()) {
                    if (timingManager.timeSinceLastShakeTimestamp() < camera.shakeCooldown())
                        camera.shake(this, resolutionManager, spaceship.position());
                    else
                        shaking(false);
                }
                camera.apply(this);

                //Sorting the Celestial Bodies, so that the ones that are furthest can be handled first, we do this to draw
                java.util.Collections.sort(gameManager.celestialBodies());

                for (Asteroid asteroid : gameManager.asteroids()) {
                    //Updating the Asteroids
                    asteroid.update(timingManager.deltaTime() * .001F);

                    //If the asteroid passed by the player
                    if (asteroid.passedBy(camera, spaceship.front())) {

                        //If the asteroid is in the players hit box in X and Y
                        boolean betweenX = isBetween(asteroid.position().x, spaceship.position().x - spaceship.radiusX(), spaceship.position().x + spaceship.radiusX());
                        boolean betweenY = isBetween(asteroid.position().y, spaceship.position().y - spaceship.radiusY(), spaceship.position().y + spaceship.radiusY());

                        //If the asteroid hit the player and the player is not in god mode decrease health and shake camera
                        if (betweenX && betweenY && !gameManager.god() && asteroid.isActive()) {
                            spaceship.setHealthTo(spaceship.health() - 10);
                            shaking(true);
                        }

                        //Reset position of the asteroid and their active state to true so they can be displayed.
                        asteroid.setForceTo(new PVector());
                        switch (asteroid.size()) {
                            case BIG:
                                asteroid.setHealthTo(2);
                                break;
                            case REGULAR:
                                asteroid.setHealthTo(1);
                                break;
                            case SMAlL:
                                asteroid.setHealthTo(1);
                                break;
                        }
                        asteroid.setPositionTo(gameManager.generateNewCelestialPosition(this, camera));
                        asteroid.setActiveTo(true);
                    }
                }

                //Displaying all Celestial Bodies, if they're active
                for (CelestialBody celestialBody : gameManager.celestialBodies())
                    if (celestialBody.isActive())
                        celestialBody.display(camera, this, assetManager, spaceship.front());
                    else
                        System.out.println(1);

                //Check if we can update the heat
                if (timingManager.timeSinceLastTimestampHeat() >= spaceship.heatCooldown())
                    spaceship.setHeatTo(spaceship.heat() - 2, timingManager);

                //Sorting the asteroids and then reverse the order, if it's closer it'll be handled first
                //java.util.Collections.sort(gameManager.asteroids());
                java.util.Collections.reverse(gameManager.asteroids());

                //Buffer is used to copy all Laser Bombs that need to be destroyed
                ArrayList<LaserBomb> laserBuffer = new ArrayList<>();

                for (LaserBomb laserBomb : gameManager.laserBombs()) {
                    //Updating the speed
                    laserBomb.update(timingManager.deltaTime() * .1F);

                    //If the Laser Bomb is further than the furthest possible value of asteroid generation it missed all possible asteroids, it can be added to the buffer to get destroyed
                    if (laserBomb.position().z > spaceship.position().z + gameManager.maxZ())
                        laserBuffer.add(laserBomb);

                    //If the Laser Bomb is colliding with any asteroid the score increases, the asteroid gets deactivated and the Laser Bomb gets added to the buffer to get destroyed
                    for (Asteroid asteroid : gameManager.asteroids()) {
                        float radius = 350;
                        switch (asteroid.size()) {
                            case BIG:
                                radius = 300;
                                break;
                            case REGULAR:
                                radius = 250;
                                break;
                            case SMAlL:
                                radius = 200;
                                break;
                        }
                        if (laserBomb.hit(asteroid, radius) && asteroid.isActive()) {
                            asteroid.setHealthTo(asteroid.health() - 1);
                            if (asteroid.health() == 0) {
                                gameManager.increaseScore(50);
                                asteroid.setActiveTo(false);
                            }
                            if (asteroid.size() != Asteroid.Size.SMAlL)
                                laserBuffer.add(laserBomb);
                            break;
                        }
                    }
                }

                //We now send the buffer to the Game Manager to get destroyed
                gameManager.removeLaserBombs(laserBuffer);


                ArrayList<AntiGravitationalMissile> antiGravitationalMissileBuffer = new ArrayList<>();

                for (AntiGravitationalMissile antiGravitationalMissile : gameManager.antiGravitationalMissiles()) {
                    antiGravitationalMissile.update(timingManager.deltaTime() * .1F);
                    if (antiGravitationalMissile.position().z > spaceship.position().z + 500) {
                        antiGravitationalMissile.explode(gameManager.celestialBodies(), resolutionManager);
                        antiGravitationalMissileBuffer.add(antiGravitationalMissile);
                    }
                }
                gameManager.removeAntiGravitationalMissiles(antiGravitationalMissileBuffer);

                resetMatrix();
                hud.drawCockpit(this, assetManager);
                hud.drawHealth(spaceship.health(), this, resolutionManager, assetManager);
                hud.drawHeat(spaceship.heat(), this, resolutionManager, assetManager);
                hud.drawCrosshair(this, assetManager);
                hud.drawScore(gameManager.score(), this, resolutionManager, assetManager);
                if (gameManager.indicators())
                    hud.drawVariables(this, gameManager, resolutionManager);

                for (int i = gameManager.asteroids().size(); i <= gameManager.maxAsteroids(); i++) {
                    int sz = Math.round(random(1, 3));
                    Asteroid.Size size = Asteroid.Size.REGULAR;
                    switch (sz) {
                        case 1:
                            size = Asteroid.Size.SMAlL;
                            break;
                        case 2:
                            size = Asteroid.Size.REGULAR;
                            break;
                        case 3:
                            size = Asteroid.Size.BIG;
                            break;
                    }
                    gameManager.addAsteroid(new Asteroid(gameManager.generateNewCelestialPosition(this, camera), this, resolutionManager, size));
                }

                //If health is equal to or below zero, the game state is changed to Game Over
                if (spaceship.health() <= 0)
                    setState(States.GAME_OVER);

                break;
            case GAME_OVER:
                //Writing game over on the center of the screen
                pushStyle();
            {
                textMode(CENTER);
                textSize(15);
                text("GAME OVER", width / 2, height / 2);
            }
            popStyle();
            break;
        }
    }

    public void keyPressed() {
        if (key == 'a')
            gameManager.setLeftTo(true);
        if (key == 'd')
            gameManager.setRightTo(true);
        if (key == 'w')
            gameManager.setUpTo(true);
        if (key == 's')
            gameManager.setDownTo(true);
        if (key == 'q')
            gameManager.setRotateLeftTo(true);
        if (key == 'e')
            gameManager.setRotateRightTo(true);
        if (key == 'f')
            gameManager.setFuzzyTo(!gameManager.isFuzzy());
        if (key == 'b')
            gameManager.increaseScore(10000);
        if (key == 'z')
            gameManager.setStoppedTo(!gameManager.isStopped());
        if (key == 'i')
            gameManager.setIndicatorsTo(!gameManager.indicators());
        if (key == 'g')
            gameManager.setGodTo(!gameManager.god());
        if (key == 'p')
            saveFrame("screenshots/screenShot-#####.jpg");
        if (key == 'r') {
            if (gameManager.isZoomed())
                zoomOut();
            else
                zoomIn();
        }
    }

    public void keyReleased() {
        if (key == 'a')
            gameManager.setLeftTo(false);
        if (key == 'd')
            gameManager.setRightTo(false);
        if (key == 'w')
            gameManager.setUpTo(false);
        if (key == 's')
            gameManager.setDownTo(false);
        if (key == 'q')
            gameManager.setRotateLeftTo(false);
        if (key == 'e')
            gameManager.setRotateRightTo(false);
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
                }
                break;
            case RUNNING:
                if (mouseButton == RIGHT)
                    createMissile();
                else if (mouseButton == LEFT)
                    createLaser();
                break;
            case GAME_OVER:
                setState(States.MAIN_MENU);
                break;
        }
    }

    //This method creates a missile.
    private void createMissile() {
        gameManager.addAntiGravitationalMissile(new AntiGravitationalMissile(spaceship.position().copy().add(new PVector(0, 0, spaceship.front())), resolutionManager));
    }

    //This method creates a laser. Not much to it
    private void createLaser() {
        if (spaceship.heat() <= 98 || gameManager.god()) {
            //Create Laser
            gameManager.addLaserBomb(new LaserBomb(spaceship.position().copy().add(new PVector(0, 0, spaceship.front())), resolutionManager));

            //Increase the heat
            if (!gameManager.god())
                spaceship.setHeatTo(spaceship.heat() + 2, timingManager);
        }
    }

    //This method handles all the behaviour related to the change that is about to be made
    private void setState(States state) {
        gameManager.setStateTo(state);
        switch (gameManager.state()) {
            case MAIN_MENU:
                cursor();
                menu = new Menu(this, width / 2, 6, 3, 5);
                if (gameManager.celestialBodies() != null)
                    gameManager.clearCelestialBodies();
                else
                    gameManager.initializeCelestialBodies();
                spaceship = null;
                break;
            case RUNNING:
                noCursor();
                menu = null;
                spaceship = new Spaceship(new PVector(width / 2, height / 2, 0), resolutionManager.resolvedOfWidth(10), resolutionManager);
                camera = new Camera(spaceship.position(), 100);
                hud = new HUD();
                gameManager.initializeCelestialBodies();
                gameManager.createCelestials(gameManager.maxAsteroids(), camera, this, resolutionManager);
                gameManager.setZoomedTo(false);
                gameManager.setIndicatorsTo(false);
                gameManager.setFuzzyTo(false);
                gameManager.setStoppedTo(false);
                break;
            case PAUSE:
                cursor();
                break;
            case GAME_OVER:
                cursor();
                spaceship = new Spaceship(new PVector(width / 2, height / 2, 0), resolutionManager.resolvedOfWidth(10), resolutionManager);
                gameManager.clearCelestialBodies();
                break;

        }
    }

    private void zoomIn() {
        camera.setDistanceTo(camera.distance() + 100);
        gameManager.setZoomedTo(true);
    }

    private void zoomOut() {
        camera.setDistanceTo(camera.distance() - 100);
        gameManager.setZoomedTo(false);
    }

    private boolean isBetween(float number, float min, float max) {
        //In this block we just make sure that the minimum and max value are indeed the minimum and the maximum values, respectively
        float buffer = max(min, max);
        min = min(min, max);
        max = buffer;

        return min < number && number < max;
    }

    private void shaking(boolean shake) {
        timingManager.shakingTimestamp();
        gameManager.setShakingTo(shake);
    }
}
