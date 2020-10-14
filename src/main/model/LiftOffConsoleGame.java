package model;

import java.util.LinkedList;
import java.util.Random;

// console version of the game for the phase 1 of the project
public class LiftOffConsoleGame {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 200;
    public static final int UPGRADE_PRICE = 2000;


    private static final Random random = new Random();

    private Rocket rocket;
    private LinkedList<Obstacle> obstacles;

    public LiftOffConsoleGame() {
        rocket = new Rocket(WIDTH / 2);
        obstacles = new LinkedList<Obstacle>();
    }

    // EFFECTS: returns rocket
    public Rocket getRocket() {
        return rocket;
    }

    // EFFECTS: returns obstacles
    public LinkedList<Obstacle> getObstacles() {
        return obstacles;
    }

    // MODIFIES: this
    // EFFECTS: create a new rocket and clear obstacles.
    public void newGame() {
        rocket = new Rocket(WIDTH / 2);
        obstacles.clear();
    }

    // MODIFIES: this
    // EFFECTS: clear obstacles, set altitude of the rocket to 0 and reset the health of the rocket
    public void newDay() {
        obstacles.clear();
        rocket.setAlt(0);
        rocket.setHealth(rocket.getHealthLevel() + 1);
        rocket.setFuel(100);
    }

    // MODIFIES: this
    // EFFECTS: clears all obstacles and adds 3 new obstacles
    public void updateObstacles() {
        obstacles.clear();
        obstacles.add(new Obstacle(random.nextInt(WIDTH - 32) + 16));
        obstacles.add(new Obstacle(random.nextInt(WIDTH - 32) + 16));
        obstacles.add(new Obstacle(random.nextInt(WIDTH - 32) + 16));
    }

    // EFFECTS: check if the x value of the rocket intersects with any obstacles. return true if any intersects.
    //          else return false
    public boolean checkCollisions() {
        boolean result = false;
        for (Obstacle o : obstacles) {
            result = result || rocket.checkCollisionTwo(o);
        }
        return result;
    }

    // EFFECTS: return true if rocket is out of fuel, else return false,
    //          no tests implemented as it just uses a method already tested in rocket
    public boolean checkFuelEmpty() {
        return rocket.checkFuelEmpty();
    }

    // EFFECTS: return true, open shop and add the money if the day has ended, else return false
    public boolean checkDayEnd() {
        if (rocket.outOfHealth() || checkFuelEmpty()) {
            rocket.playerMoney += getRocket().getAlt();
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: 0 < x < MAX_X
    // MODIFIES: this
    // EFFECTS: set rocket x to x
    //          no tests implemented as it just uses a method already tested in rocket
    public void moveRocket(int x) {
        rocket.setX(x);
    }

    // MODIFIES: this
    // EFFECTS: update the state of the game during the day
    public void update() {
        if (checkCollisions()) {
            rocket.reduceHealth();
        }
        rocket.useFuel();
        rocket.moveUp();
    }

    // MODIFIES: this
    // EFFECTS: upgrades the speed if player has enough money and returns true, else returns false
    public boolean upgradeSpeed() {
        if (rocket.playerMoney >= UPGRADE_PRICE) {
            rocket.setSpeedLevel(rocket.getSpeedLevel() + 1);
            rocket.playerMoney -= UPGRADE_PRICE;
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: upgrades the fuel if player has enough money and returns true, else returns false
    public boolean upgradeFuel() {
        if (rocket.playerMoney >= UPGRADE_PRICE) {
            rocket.setFuelLevel(rocket.getFuelLevel() + 1);
            rocket.playerMoney -= UPGRADE_PRICE;
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: upgrades the health if player has enough money and returns true, else returns false
    public boolean upgradeHealth() {
        if (rocket.playerMoney >= UPGRADE_PRICE) {
            rocket.setHealthLevel(rocket.getHealthLevel() + 1);
            rocket.playerMoney -= UPGRADE_PRICE;
            return true;
        } else {
            return false;
        }
    }

}
