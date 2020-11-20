package model;

import static model.Rocket.SIZE_X;

// superclass for all the types of control schemes
public abstract class Control {

    protected int rocketX;
    protected int steeringLevel = 0;
    public boolean type;

    public Control(int x) {
        rocketX = x;
    }

    public int getRocketX() {
        return rocketX;
    }

    public void setRocketX(int rocketX) {
        this.rocketX = rocketX;
    }

    public abstract void leftAction();

    public abstract void rightAction();


    // MODIFIES: this
    // EFFECTS: sets the x vale of the rocket to edge values (0 and LiftOffGame.WIDTH)
    //          if it tries to move past them
    public void handleBoundary() {
        if (rocketX < SIZE_X / 2) {
            rocketX = SIZE_X / 2;
        } else if (rocketX > LiftOffGame.WIDTH - SIZE_X / 2) {
            rocketX = LiftOffGame.WIDTH - SIZE_X / 2;
        }
    }

    // EFFECTS: if a certain control scheme moves the rocket at each tick it uses this method
    public abstract void move();

    // MODIFIES: this
    // EFFECTS: resets the x value of the rocket
    public void newDay() {
        rocketX = LiftOffGame.WIDTH / 2;
    }
}
