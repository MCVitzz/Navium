package celestials;

import processing.core.PVector;

public abstract class PhysicsObject {
    private PVector position, prevPosition, velocity, acceleration;
    private float mass;

    PhysicsObject(PVector position, PVector velocity, PVector acceleration, float mass) {
        this.position = position.copy();
        this.velocity = velocity.copy();
        this.prevPosition = position.copy();
        this.acceleration = acceleration.copy();
        this.mass = mass;
    }

    void updatePhysics(float deltaTime) {
        if(acceleration.dist(new PVector()) > 0)
            System.out.println("2");
        prevPosition = position().copy();
        //Here we do something that isn't realistic but it'll look way better than if it was realistic
        velocity.add(PVector.mult(acceleration, deltaTime));
        position.add(PVector.mult(velocity, deltaTime));
    }

    public void applyForce(PVector applied) {
        velocity.add(PVector.div(applied, mass));
        //velocity = applied.copy();
    }

    public void setForceTo(PVector force) {
        this.velocity = force;
    }

    public boolean hit(PhysicsObject other, float radius) {
        boolean semiCylinder = (sqr((other.position().x - this.prevPosition.x)) + sqr((other.position().y - this.prevPosition.y))) <= (sqr(radius));
        boolean zLimitation = this.prevPosition.z <= other.position().z && other.position().z < this.position().z;
        return  semiCylinder && zLimitation;
    }

    public PVector position() {
        return this.position;
    }

    public void setPositionTo(PVector position) {
        this.position = position;
    }

    private float sqr(float number) {
        return number * number;
    }
}
