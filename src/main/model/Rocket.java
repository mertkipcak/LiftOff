package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

public class Rocket implements Writable {
    public static final int BASE_SPEED = 100;                        // base speed of the rocket (in m/s)
    public static final int CONSUMPTION_PER_SECOND = 10;            // how much unit fuel rocket spends in a second
    public static final int Y_POS = LiftOffGame.HEIGHT - 40;      // y position of the rocket on screen
    public static final int SIZE_Y = 50;                          // height of the rocket
    public static final int SIZE_X = 24;                           // width of the rocket
    public static final int INITIAL_FUEL = 10000;

    public int playerMoney = 0;
    protected int steeringLevel = 0;          // number of steering upgrades done
    private int fuel = INITIAL_FUEL;                 // fuel that is left in the rocket
    private int alt = 0;                    // altitude of the rocket
    private int health = 1;            // remaining health of the rocket
    private int speedLevel = 0;             // number of speed upgrades done
    private int fuelLevel = 0;              // number of fuel upgrades done
    private int healthLevel = 0;            // number of health upgrades done
    private Control control;


    public Rocket(int x) {
        control = new ExactControl(x);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("speedLevel", speedLevel);
        json.put("steeringLevel", steeringLevel);
        json.put("fuelLevel", fuelLevel);
        json.put("healthLevel", healthLevel);
        json.put("playerMoney", playerMoney);
        return json;
    }

    // EFFECTS for all get and set values:  getA returns A
    //                                      setA(a) sets A to a
    public int getX() {
        return control.getRocketX();
    }

    // REQUIRES: 0 < x < LiftOffGame.WIDTH
    public void setX(int x) {
        this.control.setRocketX(x);
    }

    public int getAlt() {
        return alt;
    }

    // REQUIRES: 0 < i
    public void setAlt(int i) {
        alt = i;
    }

    public int getHealth() {
        return health;
    }

    // REQUIRES: 0 <= hp
    public void setHealth(int hp) {
        health = hp;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    // REQUIRES: 0 <= x
    public void setSpeedLevel(int x) {
        speedLevel = x;
    }

    public int getSteeringLevel() {
        return steeringLevel;
    }

    public void setSteeringLevel(int i) {
        steeringLevel = i;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    // REQUIRES: 0 <= x
    public void setFuelLevel(int x) {
        fuelLevel = x;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    // REQUIRES: 0 <= x
    public void setHealthLevel(int x) {
        healthLevel = x;
    }

    public int getFuel() {
        return fuel;
    }

    // REQUIRES: 0 <= f
    public void setFuel(int f) {
        fuel = f;
    }

    public Control getControl() {
        return control;
    }

    // MODIFIES: this
    // EFFECTS: increase the alt of the rocket by BASE_SPEED + f(speedLevel)
    public void moveUp() {
        alt += BASE_SPEED + speedLevel / 2;
    }

    // MODIFIES: this
    // EFFECTS: uses the fuel depending on the fuel upgrade level
    public void useFuel() {
        fuel -= CONSUMPTION_PER_SECOND - fuelLevel;
    }

    // EFFECTS: return true if rocket is touching any obstacles
    public boolean checkCollision(Obstacle o) {
        Rectangle rocketBoundingRect = new Rectangle(getX() - SIZE_X / 2,
                Y_POS - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle obstacleBoundingRect = new Rectangle(o.getX() - Obstacle.SIDE_LENGTH / 2,
                o.getY() - Obstacle.SIDE_LENGTH / 2,
                Obstacle.SIDE_LENGTH,
                Obstacle.SIDE_LENGTH);
        return rocketBoundingRect.intersects(obstacleBoundingRect);
    }

    // EFFECTS: return true if rocket is out of fuel
    public boolean checkFuelEmpty() {
        return fuel <= 0;
    }

    // EFFECTS: return true if rocket is out of health
    public boolean outOfHealth() {
        return health == 0;
    }

    // MODIFIES: this
    // EFFECTS: reduce the health of the rocket by 1
    public void reduceHealth() {
        health--;
    }

    // MODIFIES: this
    // EFFECTS: moves to rocket to the right
    public void moveRight() {
        control.rightAction();
    }

    // MODIFIES: this
    // EFFECTS: moves to rocket to the right
    public void moveLeft() {
        control.leftAction();
    }

    // MODIFIES: control
    // EFFECTS: Switches the type of the control
    public void flipControls() {
        if (control.type) {
            control = new ExactControl(control.getRocketX());
        } else {
            control = new SmoothControl(control.getRocketX());
        }
    }

}
