import processing.core.PVector;

public class Camera {

    private float distance;
    private PVector position;

    public Camera(float x, float y, float z, float distance) {
        this.position = new PVector(x, y, z);
        this.distance = distance;
    }

    public PVector transformationOf(PVector original) {
        float x = (distance / (this.position.z - original.z)) * (this.position.x - original.x);
        float y = (distance / (this.position.z - original.z)) * (this.position.y - original.y);
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
