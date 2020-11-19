package model;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LiftOffGame {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 400;
    public static final int TICKS_PER_OBSTACLE = 10;
    public static final Color GAME_BACKGROUND = new Color(9, 34, 59);
    public static final String JSON_STORE = "./data/rocket.json";
    public static final int UPGRADE_PRICE = 2000;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private int ticksTillNewObstacle;
    private ArrayList<Obstacle> obstacles;
    private Rocket rocket;
    private boolean isShopOpen = true;
    private String message = "";


    // Constructs a LiftOff Game
    // EFFECTS: creates an empty list for obstacles and creates a rocket at the center
    public LiftOffGame() {
        obstacles = new ArrayList<>();
        rocket = new Rocket(WIDTH / 2);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public LiftOffGame(JsonReader jr, JsonWriter jw) {
        obstacles = new ArrayList<>();
        rocket = new Rocket(WIDTH / 2);
        jsonWriter = jw;
        jsonReader = jr;
    }

    // getter methods
    public Rocket getRocket() {
        return rocket;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public boolean getIsShopOpen() {
        return isShopOpen;
    }

    public String getMessage() {
        return message;
    }

    public int getTicksTillNewObstacle() {
        return ticksTillNewObstacle;
    }

    // setter methods
    public void setShop(boolean b) {
        isShopOpen = b;
    }

    // EFFECTS: if shop is open, passes keyCode to controlShop(), otherwise passes keyCode to controlRocket()
    public void controlKey(int keyCode) {
        if (isShopOpen) {
            controlShop(keyCode);
        } else {
            controlRocket(keyCode);
        }
    }

    // MODIFIES: rocket
    // EFFECTS: moves the rocket left if the left button is pressed or held,
    //          moves the rocket right if the right button pressed or held
    public void controlRocket(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            rocket.moveLeft();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            rocket.moveRight();
        }
    }

    // EFFECTS: calls the necessary functions dependent on the input given,
    //          input output pairs can be seen in the shop screen
    public void controlShop(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            newDay();
        } else if (keyCode == KeyEvent.VK_1) {
            upgradeSpeed();
        } else if (keyCode == KeyEvent.VK_2) {
            upgradeFuel();
        } else if (keyCode == KeyEvent.VK_3) {
            upgradeHealth();
        } else if (keyCode == KeyEvent.VK_4) {
            upgradeSteering();
        } else if (keyCode == KeyEvent.VK_R) {
            rocket = new Rocket(WIDTH / 2);
        } else if (keyCode == KeyEvent.VK_S) {
            saveGame();
        } else if (keyCode == KeyEvent.VK_L) {
            loadGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: upgrades the speed if player has enough money and sets message to the outcome
    public void upgradeSpeed() {
        if (rocket.playerMoney >= UPGRADE_PRICE) {
            rocket.setSpeedLevel(rocket.getSpeedLevel() + 1);
            rocket.playerMoney -= UPGRADE_PRICE;
            message = "Speed upgraded";
        } else {
            message = "Not enough money, need $2000";
        }
    }

    // MODIFIES: this
    // EFFECTS: upgrades the fuel if player has enough money and sets message to the outcome
    public void upgradeFuel() {
        if (rocket.playerMoney >= UPGRADE_PRICE) {
            rocket.setFuelLevel(rocket.getFuelLevel() + 1);
            rocket.playerMoney -= UPGRADE_PRICE;
            message = "Fuel upgraded";
        } else {
            message = "Not enough money, need $2000";
        }
    }

    // MODIFIES: this
    // EFFECTS: upgrades the health if player has enough money and sets message to the outcome
    public void upgradeHealth() {
        if (rocket.playerMoney >= UPGRADE_PRICE) {
            rocket.setHealthLevel(rocket.getHealthLevel() + 1);
            rocket.playerMoney -= UPGRADE_PRICE;
            message = "Health upgraded";
        } else {
            message = "Not enough money, need $2000";
        }
    }

    // MODIFIES: this
    // EFFECTS: upgrades the speed if player has enough money and sets message to the outcome
    public void upgradeSteering() {
        if (rocket.playerMoney >= UPGRADE_PRICE) {
            rocket.setSteeringLevel(rocket.getSteeringLevel() + 1);
            rocket.playerMoney -= UPGRADE_PRICE;
            message = "Steering upgraded";
        } else {
            message = "Not enough money, need $2000";
        }
    }

    // EFFECTS: uses jsonWriter to save the rocket to a JSONObject
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(rocket);
            jsonWriter.close();
            message = "Saved game to: " + jsonWriter.getDestination();
        } catch (FileNotFoundException e) {
            message = "Unable to write to file: " + jsonWriter.getDestination();
        }
    }

    // MODIFIES: rocket
    // EFFECTS: changes rocket (and therefore all the saves) to the one from the save file
    private void loadGame() {
        try {
            rocket = jsonReader.read();
            message = "Successfully loaded from: " + jsonReader.getSource();
        } catch (IOException e) {
            message = "Unable to read from file: " + jsonReader.getSource();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets all parameters to start of day parameters
    //          clears obstacles, resets health and altitude, unpauses the game and closes the shop
    public void newDay() {
        rocket.setHealth(rocket.getHealthLevel() + 1);
        rocket.setAlt(0);
        rocket.setX(WIDTH / 2);
        isShopOpen = false;
        ticksTillNewObstacle = TICKS_PER_OBSTACLE;
        rocket.setFuel(Rocket.INITIAL_FUEL);
        obstacles.clear();
        message = "";
    }

    // MODIFIES: this
    // EFFECTS: moves all the obstacles down by their fallSpeed
    public void moveObstacles() {
        for (Obstacle obstacle: obstacles) {
            obstacle.moveDown();
        }
    }

    // EFFECTS: effects that will happen each tick of the game
    public void update() {
        if (!isShopOpen) {
            moveObstacles();
            rocket.moveUp();
            rocket.useFuel();
            checkCollisions();
            checkDayOver();
            addObstacle();
        }
    }

    // EFFECTS: adds an obstacle every TICKS_PER_OBSTACLE
    public void addObstacle() {
        if (ticksTillNewObstacle == 0) {
            obstacles.add(new Obstacle());
            ticksTillNewObstacle = TICKS_PER_OBSTACLE;
        } else {
            ticksTillNewObstacle--;
        }
    }

    // MODIFIES: this
    // EFFECTS: check if rocket is collided or out of fuel, if any true return to shop screen
    public void checkDayOver() {
        if (rocket.outOfHealth() || rocket.checkFuelEmpty()) {
            if (rocket.outOfHealth()) {
                message = "Ran out of Health!";
            } else {
                message = "Ran out of Fuel!";
            }
            isShopOpen = true;
            rocket.playerMoney += rocket.getAlt() / 10;
        }
    }


    // MODIFIES: this
    // EFFECTS: check if rocket has hist any obstacles, if hit one remove that obstacle and reduce health
    public void checkCollisions() {
        Obstacle toRemove = new Obstacle();
        for (Obstacle o : obstacles) {
            if (rocket.checkCollision(o)) {
                rocket.reduceHealth();
                toRemove = o;
                break;
            }
        }
        obstacles.remove(toRemove);
    }
}
