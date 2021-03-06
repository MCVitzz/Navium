package utilities;

import celestials.*;
import game.Camera;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class GameManager {
    private boolean up, down, left, right, rotateLeft, rotateRight, zoomed, fuzzy, stopped, indicators, god, shaking;
    private int maxAsteroids, score, lastIncreaseMaxAsteroids, lastIncreasePowerUps, hitCooldown;
    private float maxX, minX, maxY, minY, maxZ, minZ;
    private States state;
    private ArrayList<CelestialBody> celestialBodies;
    private ArrayList<LaserBomb> laserBombs;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<AntiGravitationalMissile> antiGravitationalMissiles;

    public GameManager(ResolutionManager resolutionManager) {
        this.zoomed = this.fuzzy = this.god = this.indicators = this.stopped = false;
        this.maxAsteroids = Math.round(resolutionManager.resolvedOf(1000));
        this.hitCooldown = 300;
        this.maxX = resolutionManager.resolvedOf(10000);
        this.minX = resolutionManager.resolvedOf(-10000);
        this.maxY = resolutionManager.resolvedOf(10000);
        this.minY = resolutionManager.resolvedOf(-10000);
        this.maxZ = resolutionManager.resolvedOf(15000);
        this.minZ = resolutionManager.resolvedOf(1500);
        this.score = this.lastIncreaseMaxAsteroids = this.lastIncreasePowerUps = 0;
    }

    public void addPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
        this.celestialBodies.add(powerUp);
    }

    public void addAntiGravitationalMissile(AntiGravitationalMissile antiGravitationalMissile) {
        this.antiGravitationalMissiles.add(antiGravitationalMissile);
        this.celestialBodies.add(antiGravitationalMissile);
    }

    public void addLaserBomb(LaserBomb laserBomb) {
        this.laserBombs.add(laserBomb);
        this.celestialBodies.add(laserBomb);
    }

    public void addAsteroid(Asteroid asteroid) {
        this.asteroids.add(asteroid);
        this.celestialBodies.add(asteroid);
    }

    public void removeLaserBombs(ArrayList<LaserBomb> laserBombs) {
        this.laserBombs.removeAll(laserBombs);
        this.celestialBodies.removeAll(laserBombs);
    }

    public void removePowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps.removeAll(powerUps);
        this.celestialBodies.removeAll(powerUps);
    }

    public void removeAntiGravitationalMissiles(ArrayList<AntiGravitationalMissile> antiGravitationalMissiles) {
        this.antiGravitationalMissiles.removeAll(antiGravitationalMissiles);
        this.celestialBodies.removeAll(antiGravitationalMissiles);
    }

    public void initializeCelestialBodies() {
        celestialBodies = new ArrayList<>();
        powerUps = new ArrayList<>();
        laserBombs = new ArrayList<>();
        antiGravitationalMissiles = new ArrayList<>();
        asteroids = new ArrayList<>();
    }

    public ArrayList<Asteroid> asteroids() {
        return this.asteroids;
    }

    public ArrayList<LaserBomb> laserBombs() {
        return this.laserBombs;
    }

    public ArrayList<PowerUp> powerUps() {
        return this.powerUps;
    }

    public ArrayList<AntiGravitationalMissile> antiGravitationalMissiles() {
        return antiGravitationalMissiles;
    }

    public void clearCelestialBodies() {
        laserBombs.clear();
        antiGravitationalMissiles.clear();
        asteroids.clear();
        celestialBodies.clear();
    }

    public PVector generateNewCelestialPosition(PApplet applet, Camera camera) {
        float x = applet.random(minX(), maxX());
        float y = applet.random(minY(), maxY());
        float z = applet.random(minZ(), maxZ()) + camera.position().z;
        return new PVector(x, y, z);
    }

    public void createCelestials(int number, Camera camera, PApplet applet, ResolutionManager resolutionManager) {
        //Generating Asteroids
        for (int i = 0; i < number; i++)
            addAsteroid(new Asteroid(generateNewCelestialPosition(applet, camera), applet, resolutionManager, Asteroid.Size.REGULAR));
    }

    public void increaseScore(int increase) {
        this.score += increase;
    }

    public int score() {
        return score;
    }

    public void setStateTo(States state) {
        this.state = state;
    }

    public States state() {
        return this.state;
    }

    public boolean up() {
        return this.up;
    }

    public boolean down() {
        return this.down;
    }

    public boolean right() {
        return this.right;
    }

    public boolean left() {
        return this.left;
    }

    public boolean isZoomed() {
        return this.zoomed;
    }

    public boolean isFuzzy() {
        return this.fuzzy;
    }

    public ArrayList<CelestialBody> celestialBodies() {
        return this.celestialBodies;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public boolean indicators() {
        return this.indicators;
    }

    public boolean god() {
        return this.god;
    }

    public boolean isShaking() {
        return this.shaking;
    }

    public boolean rotateLeft() {
        return this.rotateLeft;
    }

    public boolean rotateRight() {
        return this.rotateRight;
    }

    public void setUpTo(boolean up) {
        this.up = up;
    }

    public void setDownTo(boolean down) {
        this.down = down;
    }

    public void setRightTo(boolean right) {
        this.right = right;
    }

    public void setLeftTo(boolean left) {
        this.left = left;
    }

    public void setZoomedTo(boolean zoomed) {
        this.zoomed = zoomed;
    }

    public void setFuzzyTo(boolean fuzzy) {
        this.fuzzy = fuzzy;
    }

    public void setStoppedTo(boolean stopped) {
        this.stopped = stopped;
    }

    public void setIndicatorsTo(boolean indicators) {
        this.indicators = indicators;
    }

    public void setGodTo(boolean god) {
        this.god = god;
    }

    public void setShakingTo(boolean shaking) {
        this.shaking = shaking;
    }

    public void setRotateLeftTo(boolean rotateLeft) {
        this.rotateLeft = rotateLeft;
    }

    public void setRotateRightTo(boolean rotateRight) {
        this.rotateRight = rotateRight;
    }

    public float maxX() {
        return this.maxX;
    }

    public float maxY() {
        return this.maxY;
    }

    public float minX() {
        return this.minX;
    }

    public float minY() {
        return this.minY;
    }

    public float maxZ() {
        return this.maxZ;
    }

    private float minZ() {
        return this.minZ;
    }

    public int maxAsteroids() {
        return this.maxAsteroids;
    }

    public void setMaxAsteroidsTo(int maxAsteroids) {
        this.maxAsteroids = maxAsteroids;
    }

    public int lastIncreaseMaxAsteroids() {
        return  this.lastIncreaseMaxAsteroids;
    }

    public int lastIncreasePowerUps() {
        return  this.lastIncreasePowerUps;
    }

    public void setLastIncreaseMaxAsteroidsTo(int lastIncreaseMaxAsteroids) {
        this.lastIncreaseMaxAsteroids = lastIncreaseMaxAsteroids;
    }

    public int hitCooldown() {
        return this.hitCooldown;
    }

}