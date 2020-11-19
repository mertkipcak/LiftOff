package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmoothControlTest {

    private SmoothControl testControl;

    @BeforeEach
    void runBefore() {
        testControl = new SmoothControl(100);
    }

    @Test
    void leftAction() {
        testControl.leftAction();
        assertEquals(-1, testControl.getSpeed());
    }

    @Test
    void rightAction() {
        testControl.rightAction();
        assertEquals(1, testControl.getSpeed());
    }

    @Test
    void handleBoundary1() {
        testControl.setRocketX(-1);
        testControl.setSpeed(-20);
        testControl.handleBoundary();
        assertEquals(testControl.rocketX, Rocket.SIZE_X / 2);
        assertEquals(testControl.getSpeed(), 0);
    }

    @Test
    void handleBoundary2() {
        testControl.setRocketX(LiftOffGame.WIDTH);
        testControl.setSpeed(-20);
        testControl.handleBoundary();
        assertEquals(testControl.rocketX, LiftOffGame.WIDTH - (Rocket.SIZE_X / 2));
        assertEquals(testControl.getSpeed(), 0);
    }

    @Test
    void handleBoundary3() {
        testControl.setRocketX(Rocket.SIZE_X + 1);
        testControl.setSpeed(-20);
        testControl.handleBoundary();
        assertEquals(testControl.rocketX, Rocket.SIZE_X + 1);
        assertEquals(testControl.getSpeed(), -20);
    }

    @Test
    void move() {
        testControl.setSpeed(5);
        testControl.move();
        assertEquals(105,testControl.rocketX);
        testControl.setSpeed(-10);
        testControl.move();
        assertEquals(95,testControl.rocketX);
    }

    @Test
    void newDay() {
        testControl.setSpeed(10);
        testControl.newDay();
        assertEquals(0, testControl.getSpeed());
    }
}