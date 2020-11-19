package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.ExactControl.BASE_STEERING_SPEED;
import static model.ExactControl.STEERING_INCREASE_PER_LEVEL;
import static org.junit.jupiter.api.Assertions.*;

class ExactControlTest {

    private ExactControl testControl;

    @BeforeEach
    void runBefore() {
        testControl = new ExactControl(100);
    }

    @Test
    void rightAction() {
        testControl.rightAction();
        assertEquals(100 + testControl.steeringLevel * STEERING_INCREASE_PER_LEVEL + BASE_STEERING_SPEED,
                testControl.getRocketX());
    }

    @Test
    void leftAction() {
        testControl.leftAction();
        assertEquals(100 - testControl.steeringLevel * STEERING_INCREASE_PER_LEVEL - BASE_STEERING_SPEED,
                testControl.getRocketX());

    }
}