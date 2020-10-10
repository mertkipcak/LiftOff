package model;

import java.util.ArrayList;

public class LiftOffGame {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 100;
    public static final int TICKS_PER_OBSTACLE = 10;

    private int ticksTillNewObstacle;
    private ArrayList<Obstacle> obstacles;
    private Rocket rocket;
    private boolean isShopOpen = false;
    private boolean paused;


    // Constructs a LiftOff Game
    // EFFECTS: creates an empty list for obstacles and creates a rocket at the center
    public LiftOffGame() {
        obstacles = new ArrayList<Obstacle>();
        rocket = new Rocket(WIDTH / 2);
    }

    public Rocket getRocket() {
        return rocket;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public boolean getPaused() {
        return paused;
    }

    public boolean getIsShopOpen() {
        return isShopOpen;
    }

    public int getTicksTillNewObstacle() {
        return ticksTillNewObstacle;
    }

    // MODIFIES: this
    // EFFECTS: sets all parameters to start of day parameters
    //          clears obstacles, resets health and altitude, unpauses the game and closes the shop
    public void newDay() {
        rocket.setHealth(rocket.getHealthLevel() + 1);
        rocket.setAlt(0);
        paused = false;
        isShopOpen = false;
        ticksTillNewObstacle = TICKS_PER_OBSTACLE;
        rocket.setFuel(100);
        obstacles.clear();
    }

    // MODIFIES: this
    // EFFECTS: moves all the obstacles down by their fallSpeed
    public void moveObstacles() {
        for (Obstacle obstacle: obstacles) {
            obstacle.moveDown();
        }
    }

    // EFFECTS: effects that will happen each tick of the game
    public void tick() {
        moveObstacles();
        moveRocket();
        checkCollisions();
        checkDayOver();
        addObstacle();
        checkForPause();
    }

    // EFFECTS: adds an obstacle every
    public void addObstacle() {
        if (ticksTillNewObstacle == 0) {
            obstacles.add(new Obstacle());
            ticksTillNewObstacle = TICKS_PER_OBSTACLE;
        } else {
            ticksTillNewObstacle--;
        }
    }


    // check if a certain key is pressed and toggle paused if it is pressed to pause the game;
    public void checkForPause() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: move the rocket to the right if right key is pressed, left if left key is pressed
    public void moveRocket() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: check if rocket is collided or out of fuel, if any true return to shop screen
    public void checkDayOver() {
        if (rocket.outOfHealth() || rocket.checkFuelEmpty()) {
            isShopOpen = true;
            paused = true;
            rocket.playerMoney += rocket.getAlt() / 10;
        }
    }


    // MODIFIES: this
    // EFFECTS: check if rocket has hist any obstacles, if hit one remove that obstacle and reduce health
    public void checkCollisions() {
        for (Obstacle o : obstacles) {
            if (rocket.checkCollision(o)) {
                obstacles.remove(o);
                rocket.reduceHealth();
            }
        }
    }


}
