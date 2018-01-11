package utilities;

import celestials.AntiGravitationalMissile;
import celestials.Asteroid;
import celestials.CelestialBody;
import celestials.LaserBomb;

import java.util.ArrayList;

public class GameManager {
    private boolean up, down, left, right, rotateLeft, rotateRight, zoomed, fuzzy, debug, stopped, indicators, god, shaking;
    private int maxAsteroids;
    private float maxX, minX, maxY, minY, maxZ, minZ;
    private States state;
    private ArrayList<CelestialBody> celestialBodies;
    private ArrayList<LaserBomb> laserBombs;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<AntiGravitationalMissile> antiGravitationalMissiles;

    public GameManager(ResolutionManager resolutionManager) {
        this.zoomed = this.fuzzy = this.debug = this.god = this.indicators = this.stopped = false;
        this.maxAsteroids = Math.round(resolutionManager.resolvedOfWidth(2000));
        this.maxX = resolutionManager.resolvedOfWidth(10000);
        this.minX = resolutionManager.resolvedOfWidth(-10000);
        this.maxY = resolutionManager.resolvedOfHeight(10000);
        this.minY = resolutionManager.resolvedOfHeight(-10000);
        this.maxZ = resolutionManager.resolvedOfWidth(15000);
        this.minZ = resolutionManager.resolvedOfWidth(1500);
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

    public void removeAntiGravitationalMissiles(ArrayList<AntiGravitationalMissile> antiGravitationalMissiles) {
        this.antiGravitationalMissiles.removeAll(antiGravitationalMissiles);
        this.celestialBodies.removeAll(antiGravitationalMissiles);
    }

    public void initializeCelestialBodies() {
        celestialBodies = new ArrayList<>();
        laserBombs = new ArrayList<>();
        antiGravitationalMissiles = new ArrayList<>();
        asteroids = new ArrayList<>();
    }

    public ArrayList<Asteroid> asteroids() {
        return asteroids;
    }

    public ArrayList<LaserBomb> laserBombs() {
        return laserBombs;
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

    public void state(States state) {
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

    public boolean zoomed() {
        return this.zoomed;
    }

    public boolean fuzzy() {
        return this.fuzzy;
    }

    public boolean debug() {
        return this.debug;
    }

    public ArrayList<CelestialBody> celestialBodies() {
        return this.celestialBodies;
    }

    public boolean stopped() {
        return this.stopped;
    }

    public boolean indicators() {
        return this.indicators;
    }

    public boolean god() {
        return this.god;
    }

    public boolean shaking() {
        return this.shaking;
    }

    public boolean rotateLeft() {
        return this.rotateLeft;
    }

    public boolean rotateRight() {
        return this.rotateRight;
    }

    public void up(boolean up) {
        this.up = up;
    }

    public void down(boolean down) {
        this.down = down;
    }

    public void right(boolean right) {
        this.right = right;
    }

    public void left(boolean left) {
        this.left = left;
    }

    public void zoomed(boolean zoomed) {
        this.zoomed = zoomed;
    }

    public void fuzzy(boolean fuzzy) {
        this.fuzzy = fuzzy;
    }

    public void debug(boolean debug) {
        this.debug = debug;
    }

    public void stopped(boolean stopped) {
        this.stopped = stopped;
    }

    public void indicators(boolean indicators) {
        this.indicators = indicators;
    }

    public void god(boolean god) {
        this.god = god;
    }

    public void shaking(boolean shaking) {
        this.shaking = shaking;
    }

    public void rotateLeft(boolean rotateLeft) {
        this.rotateLeft = rotateLeft;
    }

    public void rotateRight(boolean rotateRight) {
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

    public float minZ() {
        return this.minZ;
    }

    public int maxAsteroids() {
        return this.maxAsteroids;
    }

}