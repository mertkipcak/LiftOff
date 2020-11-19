package model;

public class ExactControl extends Control {

    public static final int BASE_STEERING_SPEED = 10;
    public static final int STEERING_INCREASE_PER_LEVEL = 5;

    public ExactControl(int x) {
        super(x);
        type = false;
    }

    // MODIFIES: this
    // EFFECTS: moves to rocket to the right
    public void rightAction() {
        rocketX += steeringLevel * STEERING_INCREASE_PER_LEVEL + BASE_STEERING_SPEED;
        handleBoundary();
    }

    @Override
    public void move() {
        // this method does nothing
    }

    // MODIFIES: this
    // EFFECTS: moves the rocket to the left
    public void leftAction() {
        rocketX -= steeringLevel * STEERING_INCREASE_PER_LEVEL + BASE_STEERING_SPEED;
        handleBoundary();
    }

}
