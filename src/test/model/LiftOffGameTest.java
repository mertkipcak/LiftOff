
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static model.LiftOffGame.*;
import static model.ExactControl.BASE_STEERING_SPEED;
import static model.Rocket.INITIAL_FUEL;
import static org.junit.jupiter.api.Assertions.*;

public class LiftOffGameTest {

    private model.LiftOffGame testGame;
    private Rocket rocket;
    private ArrayList<Obstacle> obstacles;

    @BeforeEach
    public void runBefore() {
        testGame = new model.LiftOffGame();
        rocket = testGame.getRocket();
        obstacles = testGame.getObstacles();
    }

    @Test
    void testControlKeyLeft() {
        testGame.setShop(false);
        testGame.controlKey(KeyEvent.VK_LEFT);
        assertEquals(WIDTH / 2 - BASE_STEERING_SPEED, testGame.getRocket().getX());
        testGame.controlKey(KeyEvent.VK_KP_LEFT);
        assertEquals(WIDTH / 2 - 2 * BASE_STEERING_SPEED, testGame.getRocket().getX());
    }

    @Test
    void testControlKeyRight() {
        testGame.setShop(false);
        testGame.controlKey(KeyEvent.VK_RIGHT);
        assertEquals(WIDTH / 2 + BASE_STEERING_SPEED, testGame.getRocket().getX());
        testGame.controlKey(KeyEvent.VK_KP_RIGHT);
        assertEquals(WIDTH / 2 + 2 * BASE_STEERING_SPEED, testGame.getRocket().getX());
        testGame.controlRocket(1);
        assertEquals(WIDTH / 2 + 2 * BASE_STEERING_SPEED, testGame.getRocket().getX());
    }

    @Test
    void testControlKeyNotShop() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_RIGHT);
        assertEquals(WIDTH / 2, testGame.getRocket().getX());
    }

    @Test
    void testControlKeySpace() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_SPACE);
        assertEquals(rocket.getHealth(), rocket.getHealthLevel() + 1);
        assertEquals(rocket.getAlt(), 0);
        assertEquals(rocket.getX(), WIDTH / 2);
        assertFalse(testGame.getIsShopOpen());
        assertEquals(TICKS_PER_OBSTACLE, testGame.getTicksTillNewObstacle());
        assertEquals(INITIAL_FUEL, rocket.getFuel());
        assertTrue(obstacles.isEmpty());
    }

    @Test
    void testControlKey1() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_1);
        assertEquals("Not enough money, need $2000", testGame.getMessage());
        rocket.playerMoney = UPGRADE_PRICE + 42;
        testGame.controlKey(KeyEvent.VK_1);
        assertEquals(1, rocket.getSpeedLevel());
        assertEquals(42, rocket.playerMoney);
    }

    @Test
    void testControlKey2() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_2);
        assertEquals("Not enough money, need $2000", testGame.getMessage());
        rocket.playerMoney = UPGRADE_PRICE + 42;
        testGame.controlKey(KeyEvent.VK_2);
        assertEquals(1, rocket.getFuelLevel());
        assertEquals(42, rocket.playerMoney);
    }

    @Test
    void testControlKey3() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_3);
        assertEquals("Not enough money, need $2000", testGame.getMessage());
        rocket.playerMoney = UPGRADE_PRICE + 42;
        testGame.controlKey(KeyEvent.VK_3);
        assertEquals(1, rocket.getHealthLevel());
        assertEquals(42, rocket.playerMoney);
    }

    @Test
    void testControlKey4() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_4);
        assertEquals("Not enough money, need $2000", testGame.getMessage());
        rocket.playerMoney = UPGRADE_PRICE + 42;
        testGame.controlKey(KeyEvent.VK_4);
        assertEquals(1, rocket.getSteeringLevel());
        assertEquals(42, rocket.playerMoney);
    }

    @Test
    void testControlKeyR() {
        testGame.setShop(true);
        rocket.setSteeringLevel(5);
        rocket.setHealthLevel(3);
        rocket.playerMoney = 42;
        testGame.controlKey(KeyEvent.VK_R);
        assertEquals(0, testGame.getRocket().playerMoney);
        assertEquals(0, testGame.getRocket().getSteeringLevel());
        assertEquals(0, testGame.getRocket().getHealthLevel());
    }

    @Test
    void testControlKeyS() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_S);
        assertEquals("Saved game to: " + JSON_STORE, testGame.getMessage());
    }

    @Test
    void testControlKeyL() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_L);
        assertEquals("Successfully loaded from: " + JSON_STORE, testGame.getMessage());
    }

    @Test
    void testControlKeyC() {
        testGame.setShop(true);
        testGame.controlKey(KeyEvent.VK_C);
        assertTrue(rocket.getControl().type);
    }

    @Test
    void testLoadFailed() {
        JsonReader reader = new JsonReader("./dati/noSuchFile.json");
        JsonWriter writer = new JsonWriter("./dati/noSuchFile.json");
        testGame = new LiftOffGame(reader, writer);
        testGame.controlKey(KeyEvent.VK_L);
        assertEquals("Unable to read from file: " + "./dati/noSuchFile.json", testGame.getMessage());
    }

    @Test
    void testSaveFailed() {
        JsonReader reader = new JsonReader("./dati/noSuchFile.json");
        JsonWriter writer = new JsonWriter("./dati/noSuchFile.json");
        testGame = new LiftOffGame(reader, writer);
        testGame.controlKey(KeyEvent.VK_S);
        assertEquals("Unable to write to file: " + "./dati/noSuchFile.json", testGame.getMessage());
    }

    @Test
    void testMoveObstacles() {
        ArrayList<Obstacle> obstacles = testGame.getObstacles();
        Obstacle obstacle1 = new Obstacle();
        Obstacle obstacle2 = new Obstacle();
        obstacles.add(obstacle1);
        obstacles.add(obstacle2);
        obstacle1.setY(50);
        obstacle2.setY(90);
        testGame.moveObstacles();
        assertEquals(obstacle1.getY(),50 + obstacle1.getFallSpeed());
        assertEquals(obstacle2.getY(),90 + obstacle2.getFallSpeed());
    }

    @Test
    void testUpdate() {
        testGame.setShop(true);
        testGame.update();
        assertEquals(Rocket.INITIAL_FUEL, rocket.getFuel());
        testGame.setShop(false);
        testGame.update();
        assertEquals(Rocket.INITIAL_FUEL - Rocket.CONSUMPTION_PER_SECOND, rocket.getFuel());
    }

    @Test
    void testAddObstacle() {
        int currentNumberOfObstacles = testGame.getObstacles().size();
        testGame.setTicksTillNewObstacle(0);
        testGame.addObstacle();
        assertEquals(currentNumberOfObstacles + 1, testGame.getObstacles().size());
        assertEquals(TICKS_PER_OBSTACLE, testGame.getTicksTillNewObstacle());
        testGame.addObstacle();
        assertEquals(TICKS_PER_OBSTACLE - 1, testGame.getTicksTillNewObstacle());
    }

    @Test
    void testCheckDayOver1() {
        rocket.setAlt(10);
        testGame.setShop(false);
        rocket.setFuel(0);
        rocket.setHealth(0);
        testGame.checkDayOver();
        assertEquals("Ran out of Health!", testGame.getMessage());
        assertTrue(testGame.getIsShopOpen());
        assertEquals(1, rocket.playerMoney);
    }

    @Test
    void testCheckDayOver2() {
        rocket.setAlt(10);
        testGame.setShop(false);
        rocket.setFuel(0);
        rocket.setHealth(1);
        testGame.checkDayOver();
        assertEquals("Ran out of Fuel!", testGame.getMessage());
        assertTrue(testGame.getIsShopOpen());
        assertEquals(1, rocket.playerMoney);
    }

    @Test
    void testCheckDayOver3() {
        rocket.setAlt(10);
        testGame.setShop(false);
        rocket.setFuel(1);
        rocket.setHealth(0);
        testGame.checkDayOver();
        assertEquals("Ran out of Health!", testGame.getMessage());
        assertTrue(testGame.getIsShopOpen());
        assertEquals(1, rocket.playerMoney);
    }

    @Test
    void testCheckDayOver4() {
        rocket.setAlt(10);
        testGame.setShop(false);
        rocket.setFuel(1);
        rocket.setHealth(1);
        testGame.checkDayOver();
        assertEquals("", testGame.getMessage());
        assertFalse(testGame.getIsShopOpen());
        assertEquals(0, rocket.playerMoney);
    }

    @Test
    void testCheckCollisions() {
        rocket.setHealth(3);
        Obstacle o1 = new Obstacle(rocket.getX(), Rocket.Y_POS);
        Obstacle o2 = new Obstacle(0,0);
        Obstacle o3 = new Obstacle(1, 1);
        Obstacle o4 = new Obstacle(2,2);
        obstacles.add(o1);
        obstacles.add(o2);
        obstacles.add(o3);
        obstacles.add(o4);
        testGame.checkCollisions();
        assertFalse(obstacles.contains(o1));
        assertEquals(2, rocket.getHealth());
        testGame.checkCollisions();
        assertTrue(obstacles.contains(o2));
        assertTrue(obstacles.contains(o3));
        assertTrue(obstacles.contains(o4));
    }
}

