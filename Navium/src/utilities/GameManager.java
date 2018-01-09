package utilities;

public class GameManager {
    private int asteroids;
    private float maxX, minX, maxY, minY, maxZ, minZ;

    public GameManager(ResolutionManager resolutionManager) {

        this.asteroids = Math.round(resolutionManager.getResolvedOfWidth(1000));
        this.maxX = resolutionManager.getResolvedOfWidth(10000);
        this.minX = resolutionManager.getResolvedOfWidth(-10000);
        this.maxY = resolutionManager.getResolvedOfHeight(10000);
        this.minY = resolutionManager.getResolvedOfHeight(-10000);
        this.maxZ = resolutionManager.getResolvedOfWidth(15000);
        this.minZ = resolutionManager.getResolvedOfWidth(1500);
    }

    public float maxX() {
        return maxX;
    }

    public float maxY() {
        return maxY;
    }

    public float minX() {
        return minX;
    }

    public float minY() {
        return minY;
    }

    public float maxZ() {
        return maxZ;
    }

    public float minZ() {
        return minZ;
    }

    public int asteroids() {
        return asteroids;
    }

}
