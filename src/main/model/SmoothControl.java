package model;

import static model.Rocket.SIZE_X;

public class SmoothControl extends Control {

    private int speed = 0;

    public SmoothControl(int x) {
        super(x);
        type = true;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // MODIFIES: this
    // EFFECTS: accelerates negatively
    @Override
    public void leftAction() {
        speed -= steeringLevel * 0.1 + 1;
    }

    // MODIFIES: this
    // EFFECTS: accelerates positively
    @Override
    public void rightAction() {
        speed += steeringLevel * 0.1 + 1;
    }

    // EFFECTS: same as super but also resets the speed when a boundary is hit
    @Override
    public void handleBoundary() {
        if (rocketX < SIZE_X / 2) {
            rocketX = SIZE_X / 2;
            speed = 0;
        } else if (rocketX > LiftOffGame.WIDTH - SIZE_X / 2) {
            rocketX = LiftOffGame.WIDTH - SIZE_X / 2;
            speed = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: handles boundary and moves the rocket horizontally according to speed
    public void move() {
        rocketX += speed;
        handleBoundary();
    }

    // EFFECTS: same as super but also resets speed
    @Override
    public void newDay() {
        super.newDay();
        speed = 0;
    }
}
