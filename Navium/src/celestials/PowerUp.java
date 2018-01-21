package celestials;

import game.Spaceship;
import processing.core.PGraphics;
import processing.core.PVector;
import utilities.AssetManager;

public class PowerUp extends CelestialBody {
    private Effect effect;

    public PowerUp(PVector position, Effect effect) {
        super(position.copy(), new PVector(), new PVector(), Float.MIN_VALUE);
        this.effect = effect;
    }

    public void update(float deltaTime) {
        updatePhysics(deltaTime);
    }

    public void draw(PGraphics g, AssetManager assetManager) {
        g.imageMode(g.CENTER);
        effect.draw(g, assetManager);
    }

    public void apply(Spaceship spaceship) {
        effect.apply(spaceship);
    }

    //We need an interface here because we can't declare an abstract class in the class' scope

    public interface Effect {
        void apply(Spaceship ship);
        void draw(PGraphics g, AssetManager assetManager);
    }

    public static class HealthEffect implements Effect {
        public void apply(Spaceship spaceship){
            spaceship.setHealthTo(spaceship.health() + 10);
        }

        public void draw(PGraphics g, AssetManager assetManager) {
            g.image(assetManager.healthPowerUp, 0, 0);
        }
    }

    public static class MissileEffect implements Effect {
        public void apply(Spaceship spaceship){
            spaceship.setMissilesTo(spaceship.missiles() + 1);
        }

        public void draw(PGraphics g, AssetManager assetManager) {
            g.image(assetManager.missilePowerUp, 0, 0);
        }
    }
}
