/**
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.LiftOffGame.TICKS_PER_OBSTACLE;
import static org.junit.jupiter.api.Assertions.*;

public class LiftOffGameTest {

    private model.LiftOffGame testGame;

    // @BeforeEach
    public void runBefore() {
        testGame = new model.LiftOffGame();
    }

    // @Test
    public void testNewDay() {
        testGame.newDay();
        Rocket rocket = testGame.getRocket();
        ArrayList<Obstacle> obstacles = testGame.getObstacles();
        assertEquals(rocket.getHealth(), rocket.getHealthLevel() + 1);
        assertEquals(rocket.getAlt(), 0);
        assertFalse(testGame.getPaused());
        assertFalse(testGame.getIsShopOpen());
        assertEquals(TICKS_PER_OBSTACLE, testGame.getTicksTillNewObstacle());
        assertEquals(100, rocket.getFuel());
        assertTrue(obstacles.isEmpty());
    }

    // @Test
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

 */