package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObstacleTest {

    private Obstacle testObstacle;

    @BeforeEach
    public void runBefore() {
        testObstacle = new Obstacle();
    }

    @Test
    public void testForMoveDownOnce() {
        double speed = testObstacle.getFallSpeed();
        testObstacle.moveDown();
        assertEquals(testObstacle.getY(), speed);
    }

    @Test
    public void testForMoveDownMultiple() {
        double speed = testObstacle.getFallSpeed();
        for (int i = 0; i < 8; i++) {
            testObstacle.moveDown();
        }
        assertEquals(8 * speed, testObstacle.getY());
    }

    @Test
    public void testForSet() {
        testObstacle.setX(15);
        testObstacle.setY(35);
        assertEquals(35, testObstacle.getY());
        assertEquals(15, testObstacle.getX());
    }


}
