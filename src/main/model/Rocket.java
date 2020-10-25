package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents the rocket (and also any progress in the game like money and upgrades)
public class Rocket implements Writable {
    public static final int BASE_SPEED = 100;                        // base speed of the rocket (in m/s)
    public static final int CONSUMPTION_PER_SECOND = 10;            // how much unit fuel rocket spends in a second
    // public static final int Y_POS = LiftOffGame.HEIGHT - 20;      // y position of the rocket on screen
    // public static final int SIZE_Y = 18;                          // height of the rocket
    // public static final int SIZE_X = 6;                           // width of the rocket
    public static final int BASE_STEERING_SPEED = 10;
    public static final int STEERING_INCREASE_PER_LEVEL = 5;


    private int fuel = 100;                 // fuel that is left in the rocket
    private int alt = 0;                    // altitude of the rocket
    private int rocketX;            // x position of the rocket on the screen
    private int health = 1;            // remaining health of the rocket
    private int speedLevel = 0;             // number of speed upgrades done
    private int steeringLevel = 0;          // number of steering upgrades done
    private int fuelLevel = 0;              // number of fuel upgrades done
    private int healthLevel = 0;            // number of health upgrades done

    public int playerMoney = 0;


    /*
     * REQUIRES: accountName has a non-zero length
     * EFFECTS: playerName is set to name
     */
    public Rocket(int x) {
        rocketX = x;
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
        return rocketX;
    }

    // REQUIRES: 0 < x < LiftOffConsoleGame.WIDTH
    public void setX(int x) {
        this.rocketX = x;
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




    // MODIFIES: this
    // EFFECTS: increase the alt of the rocket by BASE_SPEED + f(speedLevel)
    public void moveUp() {
        alt += BASE_SPEED + speedLevel / 2;
    }

    // MODIFIES: this
    // EFFECTS: moves to rocket to the right
    public void moveRight() {
        rocketX += steeringLevel * STEERING_INCREASE_PER_LEVEL + BASE_STEERING_SPEED;
        handleBoundary();
    }

    // MODIFIES: this
    // EFFECTS: moves to rocket to the right
    public void moveLeft() {
        rocketX -= steeringLevel * STEERING_INCREASE_PER_LEVEL + BASE_STEERING_SPEED;
        handleBoundary();
    }

    // MODIFIES: this
    // EFFECTS: sets the x vale of the rocket to edge values (0 and LiftOffConsoleGame.WIDTH)
    //          if it tries to move past them
    public void handleBoundary() {
        if (rocketX < 0) {
            rocketX = 0;
        } else if (rocketX > LiftOffConsoleGame.WIDTH) {
            rocketX = LiftOffConsoleGame.WIDTH;
        }
    }

    // MODIFIES: this
    // EFFECTS: uses the fuel depending on the fuel upgrade level
    public void useFuel() {
        fuel -= CONSUMPTION_PER_SECOND - fuelLevel;
    }

    /*

    this method will be used in later phases so commenting it out for now as I need to learn more about
    the coordinate system to implement and write tests for this method.

    // EFFECTS: return true if rocket is touching any obstacles
    public boolean checkCollision(Obstacle o) {
        Rectangle rocketBoundingRect = new Rectangle((int) getX() - SIZE_X / 2,
                (int) Y_POS - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle obstacleBoundingRect = new Rectangle((int) o.getX() - Obstacle.SIDE_LENGTH,
                (int)o.getX() - Obstacle.SIDE_LENGTH,
                Obstacle.SIDE_LENGTH,
                Obstacle.SIDE_LENGTH);
        return rocketBoundingRect.intersects(obstacleBoundingRect);
    }
    */

    public boolean checkCollisionTwo(Obstacle o) {
        int lowerEdge = o.getX() - (Obstacle.SIDE_LENGTH - 1) / 2;
        int upperEdge = o.getX() + (Obstacle.SIDE_LENGTH - 1) / 2;
        return (rocketX <= upperEdge && rocketX >= lowerEdge);
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

}
