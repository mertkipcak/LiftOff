
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static model.LiftOffGame.*;
import static model.Rocket.BASE_STEERING_SPEED;
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
    }

    @Test
    void testControlKeyRight() {
        testGame.setShop(false);
        testGame.controlKey(KeyEvent.VK_RIGHT);
        assertEquals(WIDTH / 2 + BASE_STEERING_SPEED, testGame.getRocket().getX());
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
    public void testMoveObstacles() {
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
}

