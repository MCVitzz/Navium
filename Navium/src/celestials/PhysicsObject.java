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

    //Just updating the velocity and position, setting the previous position to the current position, before updating it with our velocity
    void updatePhysics(float deltaTime) {
        prevPosition = position().copy();
        velocity.add(PVector.mult(acceleration, deltaTime));
        position.add(PVector.mult(velocity, deltaTime));
    }

    // F = m * a simple application
    public void applyForce(PVector applied) {
        acceleration.add(applied.mult(mass()));
    }

    //Using the cylinder's cartesian formula to define a hit box
    public boolean hit(PhysicsObject other, float radius) {
        boolean semiCylinder = (sqr((other.position().x - this.prevPosition.x)) + sqr((other.position().y - this.prevPosition.y))) <= (sqr(radius));
        boolean zLimitation = this.prevPosition.z <= other.position().z && other.position().z < this.position().z;
        return semiCylinder && zLimitation;
    }

    public float mass() {
        return this.mass;
    }

    public void resetPhysics() {
        velocity = new PVector();
        acceleration = new PVector();
    }

    public PVector position() {
        return this.position;
    }

    public void setPositionTo(PVector position) {
        this.position = position;
    }

    public void dragBy(float drag) {
        velocity.mult(drag);
    }

    private float sqr(float number) {
        return number * number;
    }
}
