import processing.core.PVector;

public class Camera {

    private float distance;
    private PVector position;

    public Camera(float x, float y, float z, float distance) {
        this.position = new PVector(x, y, z);
        this.distance = distance;
    }

    public PVector transformationOf(PVector original) {
        float x = ((position.x - original.x) / (position.z - original.z)) * distance;
        float y = ((position.y - original.y) / (position.z - original.z)) * distance;
        return new PVector(x, y);
    }

    public float getScaleOf(float radius, float z) {
        return /*.05F;*/(distance / z - position.z) * radius;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
