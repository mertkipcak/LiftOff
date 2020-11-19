package model;

public class SmoothControl extends Control {

    private int speed = 0;

    public SmoothControl(int x) {
        super(x);
    }

    @Override
    public void leftAction() {
        speed -= steeringLevel * 0.1 + 1;
    }

    @Override
    public void rightAction() {
        speed -= steeringLevel * 0.1 + 1;
    }

    public void move() {
        rocketX += speed;
    }
}
