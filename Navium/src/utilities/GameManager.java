package utilities;

public class GameManager {
    private boolean up, down, left, right, rotateLeft, rotateRight, zoomed, fuzzy, debug, stopped, indicators, god, shaking;
    private int asteroids;
    private float maxX, minX, maxY, minY, maxZ, minZ;
    private States state;

    public GameManager(ResolutionManager resolutionManager) {

        this.zoomed = this.fuzzy = this.debug = this.god = this.indicators = this.stopped = false;
        this.asteroids = Math.round(resolutionManager.resolvedOfWidth(1000));
        this.maxX = resolutionManager.resolvedOfWidth(10000);
        this.minX = resolutionManager.resolvedOfWidth(-10000);
        this.maxY = resolutionManager.resolvedOfHeight(10000);
        this.minY = resolutionManager.resolvedOfHeight(-10000);
        this.maxZ = resolutionManager.resolvedOfWidth(15000);
        this.minZ = resolutionManager.resolvedOfWidth(1500);
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

    public int asteroids() {
        return this.asteroids;
    }

}