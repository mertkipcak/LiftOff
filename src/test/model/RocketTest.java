package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Rocket.*;
import static org.junit.jupiter.api.Assertions.*;

class RocketTest {

    private Rocket testRocket;

    @BeforeEach
    public void runBefore() {
        testRocket = new Rocket(LiftOffConsoleGame.WIDTH / 2);
    }

    @Test
    public void testToJson() {
        JSONObject object = testRocket.toJson();
        assertEquals(testRocket.getSteeringLevel(), object.get("steeringLevel"));
        assertEquals(testRocket.getSpeedLevel(), object.get("speedLevel"));
        assertEquals(testRocket.getHealthLevel(), object.get("healthLevel"));
        assertEquals(testRocket.getFuelLevel(), object.get("fuelLevel"));
        assertEquals(testRocket.playerMoney, object.get("playerMoney"));
    }

    @Test
    public void testMoveUpOne() {
        testRocket.moveUp();
        assertEquals(BASE_SPEED + testRocket.getSpeedLevel() / 2, testRocket.getAlt());
    }

    @Test
    public void testMoveUpTwo() {
        for(int i = 0; i < 10; i++) {
            testRocket.moveUp();
        }
        assertEquals(10 * (BASE_SPEED + testRocket.getSpeedLevel() / 2), testRocket.getAlt());
    }

    @Test
    public void testMoveRightOne() {
        testRocket.moveRight();
        assertEquals(testRocket.getSteeringLevel() * STEERING_INCREASE_PER_LEVEL +
                        BASE_STEERING_SPEED +
                        LiftOffConsoleGame.WIDTH / 2,
                testRocket.getX());
    }

    @Test
    public void testMoveRightTwo() {
        testRocket.setX(LiftOffConsoleGame.WIDTH - BASE_STEERING_SPEED);
        testRocket.moveRight();
        assertEquals(LiftOffConsoleGame.WIDTH, testRocket.getX());
    }

    @Test
    public void testMoveRightThree() {
        testRocket.setX(LiftOffConsoleGame.WIDTH - BASE_STEERING_SPEED / 2);
        testRocket.moveRight();
        assertEquals(LiftOffConsoleGame.WIDTH, testRocket.getX());
    }

    @Test
    public void testMoveLeftOne() {
        testRocket.moveLeft();
        assertEquals(LiftOffConsoleGame.WIDTH / 2 -
                        (testRocket.getSteeringLevel() * STEERING_INCREASE_PER_LEVEL + BASE_STEERING_SPEED),
                testRocket.getX());
    }

    @Test
    public void testMoveLeftTwo() {
        testRocket.setX(BASE_STEERING_SPEED);
        testRocket.moveLeft();
        assertEquals(0, testRocket.getX());
    }

    @Test
    public void testMoveLeftThree() {
        testRocket.setX( BASE_STEERING_SPEED / 2);
        testRocket.moveLeft();
        assertEquals(0, testRocket.getX());
    }

    @Test
    public void testUseFuel() {
        int initialFuel = testRocket.getFuel();
        testRocket.useFuel();
        assertEquals(initialFuel - CONSUMPTION_PER_SECOND + testRocket.getFuelLevel(), testRocket.getFuel());
    }

    // TODO: rewrite the tests of this f again when
    //       you have better understanding of java coordinate system.
    // @Test
    /*
    public void testCheckCollision() {
        Obstacle o = new Obstacle();
        o.setY(Y_POS);
        o.setX(testRocket.getX());
        assertTrue(testRocket.checkCollision(o));
    }
     */

    @Test
    public void testCheckCollisionTwo() {
        testRocket.setX(40);
        assertTrue(testRocket.checkCollisionTwo( new Obstacle(40)));
        testRocket.setX(55);
        assertTrue(testRocket.checkCollisionTwo( new Obstacle(40)));
        testRocket.setX(56);
        assertTrue(testRocket.checkCollisionTwo( new Obstacle(40)));
        testRocket.setX(57);
        assertFalse(testRocket.checkCollisionTwo( new Obstacle(40)));
        testRocket.setX(80);
        assertFalse(testRocket.checkCollisionTwo( new Obstacle(40)));
        testRocket.setX(23);
        assertFalse(testRocket.checkCollisionTwo( new Obstacle(40)));
        testRocket.setX(24);
        assertTrue(testRocket.checkCollisionTwo( new Obstacle(40)));
    }

    @Test
    public void testCheckFuelEmpty() {
        assertFalse(testRocket.checkFuelEmpty());
        testRocket.setFuel(0);
        assertTrue(testRocket.checkFuelEmpty());
        testRocket.setFuel(1);
        assertFalse(testRocket.checkFuelEmpty());
        testRocket.useFuel();
        assertTrue(testRocket.checkFuelEmpty());
    }

    @Test
    public void testOutOfHealth() {
        testRocket.setHealth(1 + testRocket.getHealthLevel());
        assertFalse(testRocket.outOfHealth());
        testRocket.setHealth(1);
        assertFalse(testRocket.outOfHealth());
        testRocket.reduceHealth();
        assertTrue(testRocket.outOfHealth());
    }

    @Test
    public void testSetAlt() {
        testRocket.setAlt(150);
        assertEquals(150, testRocket.getAlt());
    }

    @Test
    public void testSetSteeringLevel() {
        testRocket.setSteeringLevel(3);
        assertEquals(3, testRocket.getSteeringLevel());
    }


}