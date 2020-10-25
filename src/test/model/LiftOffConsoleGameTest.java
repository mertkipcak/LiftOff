package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static model.LiftOffConsoleGame.WIDTH;
import static org.junit.jupiter.api.Assertions.*;

public class LiftOffConsoleGameTest {

    private LiftOffConsoleGame testGame;
    private Rocket rocket;
    private LinkedList<Obstacle> obstacles;

    @BeforeEach
    public void runBefore() {
        testGame = new LiftOffConsoleGame();
        rocket = testGame.getRocket();
        obstacles = testGame.getObstacles();
    }

    @Test
    public void testNewDay() {
        rocket.playerMoney = 1500;
        rocket.setSpeedLevel(5);
        rocket.setX(87);
        rocket.setAlt(9000);
        rocket.setHealthLevel(5);
        rocket.setHealth(2);
        rocket.setFuel(20);
        obstacles.add( new Obstacle() );
        testGame.newDay();
        assertEquals(1500, rocket.playerMoney);
        assertEquals(5, rocket.getSpeedLevel());
        assertEquals(87, rocket.getX());
        assertEquals(0, rocket.getAlt());
        assertEquals(6, rocket.getHealth());
        assertEquals(100, rocket.getFuel());
        assertTrue(obstacles.isEmpty());
    }

    @Test
    public void testNewGame() {
        rocket.playerMoney = 1500;
        rocket.setSpeedLevel(5);
        rocket.setX(87);
        obstacles.add( new Obstacle() );
        testGame.newGame();
        assertNotEquals(rocket, testGame.getRocket());
        assertEquals(0, testGame.getRocket().playerMoney);
        assertEquals(0, testGame.getRocket().getSpeedLevel());
        assertTrue(obstacles.isEmpty());
    }

    @Test
    public void testUpdateObstacles() {
        testGame.updateObstacles();
        Obstacle obstacle0 = obstacles.get(0);
        Obstacle obstacle1 = obstacles.get(1);
        Obstacle obstacle2 = obstacles.get(2);
        assertTrue((obstacle0.getX() <= 84) && (obstacle0.getX() >= 16));
        assertTrue((obstacle1.getX() <= 84) && (obstacle1.getX() >= 16));
        assertTrue((obstacle2.getX() <= 84) && (obstacle2.getX() >= 16));
    }

    @Test
    public void testCheckCollision() {
        obstacles.add( new Obstacle(40));
        obstacles.add( new Obstacle(10));
        testGame.moveRocket(40);
        assertTrue(testGame.checkCollisions());
        testGame.moveRocket(25);
        assertTrue(testGame.checkCollisions());
        testGame.moveRocket(56);
        assertTrue(testGame.checkCollisions());
        testGame.moveRocket(57);
        assertFalse(testGame.checkCollisions());
        testGame.moveRocket(90);
        assertFalse(testGame.checkCollisions());
    }

    @Test
    public void testCheckDayEnd() {
        rocket.setHealth(1);
        rocket.setFuel(1);
        assertFalse(testGame.checkDayEnd());
    }

    @Test
    public void testCheckDayEndTwo() {
        rocket.setHealth(0);
        rocket.setFuel(1);
        assertTrue(testGame.checkDayEnd());
    }

    @Test
    public void testCheckDayEndThree() {
        rocket.setHealth(1);
        rocket.setFuel(0);
        assertTrue(testGame.checkDayEnd());
    }

    @Test
    public void testCheckDayEndFour() {
        rocket.setHealth(0);
        rocket.setFuel(0);
        assertTrue(testGame.checkDayEnd());
    }

    @Test
    public void testCheckDayEndFive() {
        rocket.setHealth(0);
        rocket.moveUp();
        testGame.checkDayEnd();
        assertEquals(rocket.playerMoney, rocket.BASE_SPEED);
    }

    @Test
    public void testUpdateOne() {
        testGame.update();
        assertEquals(Rocket.BASE_SPEED, rocket.getAlt());
        assertEquals(100 - Rocket.CONSUMPTION_PER_SECOND, rocket.getFuel());
    }

    @Test
    public void testUpdateTwo() {
        rocket.setHealth(2);
        obstacles.add(new Obstacle(WIDTH / 2));
        testGame.update();
        assertEquals(Rocket.BASE_SPEED, rocket.getAlt());
        assertEquals(100 - Rocket.CONSUMPTION_PER_SECOND, rocket.getFuel());
        assertEquals(1, rocket.getHealth());
    }

    @Test
    public void testUpgradeSpeedOne() {
        rocket.playerMoney = 1;
        assertFalse(testGame.upgradeSpeed());
    }

    @Test
    public void testUpgradeSpeedTwo() {
        rocket.playerMoney = LiftOffConsoleGame.UPGRADE_PRICE;
        assertTrue(testGame.upgradeSpeed());
        assertEquals(0, rocket.playerMoney);
        assertEquals(1, rocket.getSpeedLevel());
    }

    @Test
    public void testUpgradeFuelOne() {
        rocket.playerMoney = 1;
        assertFalse(testGame.upgradeFuel());
    }

    @Test
    public void testUpgradeFuelTwo() {
        rocket.playerMoney = LiftOffConsoleGame.UPGRADE_PRICE;
        assertTrue(testGame.upgradeFuel());
        assertEquals(0, rocket.playerMoney);
        assertEquals(1, rocket.getFuelLevel());
    }

    @Test
    public void testUpgradeHealthOne() {
        rocket.playerMoney = 1;
        assertFalse(testGame.upgradeHealth());
    }

    @Test
    public void testUpgradeHealthTwo() {
        rocket.playerMoney = LiftOffConsoleGame.UPGRADE_PRICE;
        assertTrue(testGame.upgradeHealth());
        assertEquals(0, rocket.playerMoney);
        assertEquals(1, rocket.getHealthLevel());
    }

    @Test
    public void testSetRocket() {
        Rocket newRocket = new Rocket(5);
        newRocket.setSteeringLevel(5);
        newRocket.setHealthLevel(3);
        newRocket.setFuelLevel(7);
        newRocket.setSpeedLevel(1);
        newRocket.playerMoney = 8300;
        testGame.setRocket(newRocket);
        assertEquals(1, newRocket.getSpeedLevel());
        assertEquals(3, newRocket.getHealthLevel());
        assertEquals(5, newRocket.getSteeringLevel());
        assertEquals(7, newRocket.getFuelLevel());
        assertEquals(8300, newRocket.playerMoney);
    }

}
