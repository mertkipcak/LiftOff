package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

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
        obstacles.add(new Obstacle(LiftOffConsoleGame.MAX_X / 2));
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

}
