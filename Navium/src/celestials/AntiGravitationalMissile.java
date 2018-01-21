package celestials;

import processing.core.PGraphics;
import processing.core.PVector;
import utilities.AssetManager;
import utilities.ResolutionManager;

import java.util.ArrayList;

public class AntiGravitationalMissile extends CelestialBody {

    private boolean exploded;
    private int missileExplosionCooldown = 300;
    private float missileExplosionTimestamp;

    public AntiGravitationalMissile(PVector position, ResolutionManager resolutionManager) {
        super(position.copy(), new PVector(0, 0, resolutionManager.resolvedOf(50)), new PVector(), Float.MIN_VALUE);
        exploded = false;
    }

    public void update(float deltaTime) {
        if (!hasExploded())
            updatePhysics(deltaTime);
    }

    public void draw(PGraphics g, AssetManager manager) {
        if(!hasExploded()) {
            g.imageMode(g.CENTER);
            g.image(manager.antiGravitationalPellet.image(), 0, 0);
        }
        else {
            g.ellipseMode(g.CENTER);
            g.ellipse( 0, 0, 100, 100);
        }
    }

    public void detonate(ArrayList<CelestialBody> celestialBodies, ResolutionManager resolutionManager) {
        //for every celestial body in a certain radius
        for (CelestialBody celestialBody : celestialBodies) {
            float distance = celestialBody.position().dist(position());
            if (distance <= resolutionManager.resolvedOf(1300)) {
                //Creating the force that will be applied, it's basically creating a Vector that points from the missile to the asteroid
                PVector force = PVector.sub(position(), celestialBody.position());
                //Than it's toned down according to the distance from the asteroid to the missile
                force = PVector.mult(force, resolutionManager.resolvedOf(.2F) - (distance * .1F));
                //We cancel out the z component so that the perspective doesn't get to weird
                force.z = 0;
                //Than we tone it down even more
                force.mult(.009F);
                celestialBody.applyForce(force);
                exploded = true;
            }
        }
    }

    public boolean hasExploded() {
        return this.exploded;
    }

    public float missileExplosionTimestamp() {
        return missileExplosionTimestamp;
    }

    public void markMissileExplosionTimestamp(float millis) {
        missileExplosionTimestamp = millis;
    }

    public int missileExplosionCooldown() {
        return missileExplosionCooldown;
    }
}
