package model;

import java.awt.*;

import static model.LiftOffGame.WIDTH;

// Represents an obstacle
public class Obstacle {

    public static int SIDE_LENGTH = 33;
    public static Color COLOR = new Color(111, 111, 111);

    private int obstacleX;
    private int obstacleY;
    private int fallSpeed;

    // EFFECTS: public constructor for the obstacle class,
    //          creates an obstacle with x value as thee input
    //          and side SIDE_LENGTH. obstacle also falls at a speed between 10 and 30
    public Obstacle() {
        this.obstacleX = (int) (Math.random() * (WIDTH - 2 * SIDE_LENGTH + 1) + SIDE_LENGTH);
        this.obstacleY = 0;
        this.fallSpeed = (int) (Math.random() * 6 + 10);
    }

    // REQUIRES: 0 < x < LiftOffConsoleGame.WIDTH
    // EFFECTS: public constructor for the console version of the game
    //          y and fallSpeed is irrelevant in this game so only initializes x and side
    public Obstacle(int x, int y) {
        this.obstacleX = x;
        this.obstacleY = y;
    }

    // MODIFIES: this
    // EFFECTS: moves down the obstacle down by fallSpeed
    public void moveDown() {
        obstacleY += fallSpeed;
    }

    // EFFECTS: return obstacleX
    public int getX() {
        return obstacleX;
    }

    // EFFECTS: return obstacleY
    public int getY() {
        return obstacleY;
    }

    // EFFECTS: return fallSpeed
    public int getFallSpeed() {
        return fallSpeed;
    }

    // REQUIRES: 0 < x < LiftOffConsoleGame.WIDTH
    // EFFECTS: set obstacleX to x
    public void setX(int x) {
        this.obstacleX = x;
    }

    // REQUIRES: 0 < x < LiftOffConsoleGame.HEIGHT
    // EFFECTS: set obstacleY to y
    public void setY(int y) {
        this.obstacleY = y;
    }
}
