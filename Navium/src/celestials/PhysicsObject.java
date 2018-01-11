package celestials;

import processing.core.PVector;

public abstract class PhysicsObject {
    private PVector position, velocity, acceleration;
    private float mass;

    PhysicsObject(PVector position, PVector velocity, PVector acceleration, float mass) {
        this.position = position.copy();
        this.velocity = velocity.copy();
        this.acceleration = acceleration.copy();
        this.mass = mass;
    }

    void updatePhysics(float deltaTime) {
        if(acceleration.dist(new PVector()) > 0)
            System.out.println("2");
        //Here we do something that isn't realistic but it'll look way better than if it was realistic
        velocity.add(PVector.mult(acceleration, deltaTime));
        position.add(PVector.mult(velocity, deltaTime));
    }

    public void applyForce(PVector applied) {
        velocity.add(PVector.div(applied, mass));
        //velocity = applied.copy();
    }

    public PVector position() {
        return this.position;
    }

    public void position(PVector position) {
        this.position = position;
    }
}
