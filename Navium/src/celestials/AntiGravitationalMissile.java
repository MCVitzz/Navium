package celestials;

import processing.core.PGraphics;
import processing.core.PVector;
import utilities.AssetManager;
import utilities.ResolutionManager;

import java.util.ArrayList;

public class AntiGravitationalMissile extends CelestialBody {

    public AntiGravitationalMissile(PVector position, ResolutionManager resolutionManager) {
        super(position.copy(), new PVector(0, 0, resolutionManager.resolvedOfWidth(30)), new PVector(), Float.MIN_VALUE);
    }

    public void update(float deltaTime) {
        updatePhysics(deltaTime);
    }

    public void draw(PGraphics g, AssetManager manager) {
        g.imageMode(g.CENTER);
        g.image(manager.antiGravitationalPellet.image(), 0, 0);
    }

    public void explode(ArrayList<CelestialBody> celestialBodies, ResolutionManager resolutionManager) {
        for (CelestialBody celestialBody : celestialBodies) {
            float distance = celestialBody.position().dist(position());
            if (distance <= resolutionManager.resolvedOfWidth(1300)) {
                PVector force = PVector.sub(position(), celestialBody.position());
                force = PVector.mult(force, resolutionManager.resolvedOfWidth(.2F) - (distance * .1F));
                force.z = 0;
                celestialBody.applyForce(force);
            }
        }
    }
}
