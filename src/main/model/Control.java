package model;

import static model.Rocket.SIZE_X;

public abstract class Control {

    protected int rocketX;
    protected int steeringLevel = 0;

    public Control(int x) {
        rocketX = x;
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

    public abstract void move();
}
