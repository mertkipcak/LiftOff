package model;

import static model.LiftOffGame.WIDTH;

// Represents an obstacle
public class Obstacle {

    public static int SIDE_LENGTH = 33;

    private int obstacleX;
    private int obstacleY;
    private int side;
    private int fallSpeed;

    // EFFECTS: public constructor for the obstacle class,
    //          creates an obstacle with x value as thee input
    //          and side SIDE_LENGTH. obstacle also falls at a speed between 10 and 30
    public Obstacle() {
        this.side = SIDE_LENGTH;
        this.obstacleX = (int) Math.random() * (WIDTH - 2 * side + 1) + side;
        this.obstacleY = 0;
        this.fallSpeed = (int) Math.random() * 21 + 10;
    }

    // EFFECTS: public constructor for the console version of the game
    //          y and fallspeed is irrelevent in this game so only initializes x and side
    public Obstacle(int x) {
        this.side = SIDE_LENGTH;
        this.obstacleX = x;
    }

    // MODIFIES: this
    // EFFECTS: moves down the obstacle down by fallSpeed
    public void moveDown() {
        obstacleY += fallSpeed;
    }

    public int getX() {
        return obstacleX;
    }

    public int getY() {
        return obstacleY;
    }

    public int getFallSpeed() {
        return fallSpeed;
    }

    public void setX(int x) {
        this.obstacleX = x;
    }

    public void setY(int y) {
        this.obstacleY = y;
    }
}
