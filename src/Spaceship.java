import processing.core.PApplet;
import processing.core.PVector;

public class Spaceship {
    private PVector position;
    private Camera camera;
    private int celestials;

    Spaceship(float x, float y, float z, PApplet applet) {
        position = new PVector(x, y, z);
        camera = new Camera(x, y, z, applet.width / 1250);
        celestials = 1;
    }

    public void setPositon(float x, float y, float z) {
        setPosition(new PVector(x, y, z));
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    int getCelestials() {
        return celestials;
    }

    public void setCelestials(int celestials) {
        this.celestials = celestials;
    }

    Camera getCamera() {
        return camera;
    }
}
