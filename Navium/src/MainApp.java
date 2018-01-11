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
                spaceship.update(gameManager, gameManager.up(), gameManager.down(), gameManager.left(), gameManager.right(), gameManager.rotateLeft(), gameManager.rotateRight(), timingManager.deltaTime(), gameManager.stopped());

                //Increasing the player's score
                spaceship.increaseScore(Math.round(timingManager.deltaTime() * .1F));

                //Update camera to the spaceship's position
                camera.update(spaceship.position());
                //If the camera is shaking keep it shaking until shake time is over
                if (gameManager.shaking()) {
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
                        if (betweenX && betweenY && !gameManager.god() && asteroid.active()) {
                            spaceship.health(spaceship.health() - 10);
                            shaking(true);
                        }

                        //Reset position of the asteroid and their active state to true so they can be displayed.
                        asteroid.position(generateNewCelestialPosition());
                        asteroid.active(true);
                    }
                }

                //Displaying all Celestial Bodies, if they're active
                for (CelestialBody celestialBody : gameManager.celestialBodies()) {
                    if (celestialBody.active())
                        celestialBody.display(camera, this, assetManager, spaceship.front(), gameManager.debug());
                }

                //Check if we can update the heat
                if (timingManager.timeSinceLastTimestampHeat() >= spaceship.heatCooldown())
                    spaceship.heat(spaceship.heat() - 2, timingManager);

                //Sorting the asteroids and then reverse the order, if it's closer it'll be handled first
                java.util.Collections.sort(gameManager.asteroids());
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
                    for (Asteroid asteroid : gameManager.asteroids())
                        if (laserBomb.isCollidingWith(resolutionManager, asteroid)) {
                            spaceship.increaseScore(50);
                            asteroid.active(false);
                            laserBuffer.add(laserBomb);
                            break;
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

                //Draw the HUD elements
                HUD.drawHealth(spaceship.health(), this, resolutionManager);
                HUD.drawHeat(spaceship.heat(), this, resolutionManager);
                HUD.drawCrosshair(this, assetManager);
                HUD.drawScore(spaceship.score(), this, resolutionManager);
                if (gameManager.indicators())
                    HUD.drawVariables(this, gameManager, resolutionManager);

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
        if (key == 'p')
            saveFrame("screenshots/screenShot-#####.jpg");
        if (key == 'r') {
            if (gameManager.zoomed())
                zoomOut();
            else
                zoomIn();
        }
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
                }
                break;
            case RUNNING:
                if (mouseButton == RIGHT)
                    gameManager.addAntiGravitationalMissile(new AntiGravitationalMissile(spaceship.position().copy(), resolutionManager));
                else if (mouseButton == LEFT) {
                    if (spaceship.heat() <= 98 || gameManager.god()) {

                        //Create Laser
                        gameManager.addLaserBomb(new LaserBomb(spaceship.position().copy(), resolutionManager));

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

    //This method handles all the behaviour related to the change that is about to be made
    private void setState(States state) {
        gameManager.state(state);
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
                createCelestials(gameManager.maxAsteroids());
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
                gameManager.clearCelestialBodies();
                break;

        }
    }

    private void createCelestials(int number) {
        gameManager.initializeCelestialBodies();

        //Generating Asteroids
        for (int i = 0; i < number; i++)
            gameManager.addAsteroid(new Asteroid(generateNewCelestialPosition(), this, resolutionManager));
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

    private void shaking(boolean shake) {
        timingManager.shakingTimestamp();
        gameManager.shaking(shake);
    }
}
