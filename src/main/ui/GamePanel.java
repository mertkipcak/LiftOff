package ui;

import model.LiftOffGame;
import model.Obstacle;
import model.Rocket;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static model.Rocket.INITIAL_FUEL;

/*
 * the panel which the game is rendered
 */
public class GamePanel extends JPanel {

    private static final Color TEXT_COLOR = new Color(255, 255, 255);

    private LiftOffGame game;
    private Image backgroundImage;
    private BufferedImage rocketImage;

    private static final int STRING_DISTANCE_VERTICAL = 20;


    // Constructs a game panel
    // EFFECTS: sets size and background colour of panel
    //          updates this with the game to be displayed
    public GamePanel(LiftOffGame g) {
        setPreferredSize(new Dimension(LiftOffGame.WIDTH, LiftOffGame.HEIGHT));
        setBackground(LiftOffGame.GAME_BACKGROUND);
        try {
            backgroundImage = ImageIO.read(new File("./textures/starryBackground.jpg"));
            rocketImage = ImageIO.read(new File("./textures/BasicRocket.jpg"));
        } catch (IOException ex) {
            System.out.println("Wasn't able to get textures");
        }
        this.game = g;
    }

    // EFFECTS: draws the shop if isShopOpen else draws the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
        if (game.getIsShopOpen()) {
            drawShop(g);
        } else {
            drawGame(g);
        }
    }


    // Draws the game
    // MODIFIES: g
    // EFFECTS: Draws the game onto g
    private void drawGame(Graphics g) {
        drawRocket(g);
        drawObstacles(g);
        drawData(g);
    }

    private void drawRocket(Graphics g) {
        Rocket r = game.getRocket();
        g.drawImage(rocketImage, r.getX() - Rocket.SIZE_X / 2, Rocket.Y_POS - Rocket.SIZE_Y / 2, null);
    }

    private void drawObstacles(Graphics g) {
        for (Obstacle o: game.getObstacles()) {
            drawObstacle(g, o);
        }
    }

    private void drawObstacle(Graphics g, Obstacle o) {
        Color savedCol = g.getColor();
        g.setColor(Obstacle.COLOR);
        int side = Obstacle.SIDE_LENGTH;
        g.fillOval(o.getX() - side / 2, o.getY() - side / 2, side, side);
        g.setColor(savedCol);
    }

    private void drawData(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(TEXT_COLOR);
        g.drawString("Fuel: %" + game.getRocket().getFuel() / (INITIAL_FUEL / 100), 10, 20);
        g.drawString("Altitude: " + game.getRocket().getAlt(), 10, 20 + STRING_DISTANCE_VERTICAL);
        g.drawString("Health: " + game.getRocket().getHealth(), 10, 20 + 2 * STRING_DISTANCE_VERTICAL);
        g.setColor(savedCol);
    }

    // EFFECTS: draws the shop
    private void drawShop(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(TEXT_COLOR);
        drawShopLeftSide(g);
        g.drawString(game.getMessage(), 20, LiftOffGame.HEIGHT * 5 / 6);
        drawShopRightSide(g);
        g.setColor(savedCol);
    }

    // EFFECTS: Draws the right side of the shop
    private void drawShopRightSide(Graphics g) {
        g.drawString("Money: $" + game.getRocket().playerMoney, LiftOffGame.WIDTH / 2, 40);
        g.drawString("Speed upgrades: " + game.getRocket().getSpeedLevel(),
                LiftOffGame.WIDTH / 2,
                40 + STRING_DISTANCE_VERTICAL);
        g.drawString("Fuel upgrades: " + game.getRocket().getFuelLevel(),
                LiftOffGame.WIDTH / 2,
                40 + 2 * STRING_DISTANCE_VERTICAL);
        g.drawString("Health upgrades: " + game.getRocket().getHealthLevel(),
                LiftOffGame.WIDTH / 2,
                40 + 3 * STRING_DISTANCE_VERTICAL);
        g.drawString("Steering upgrades: " + game.getRocket().getSteeringLevel(),
                LiftOffGame.WIDTH / 2,
                40 + 4 * STRING_DISTANCE_VERTICAL);
    }


    // EFFECTS: draws the left side of the shop
    private void drawShopLeftSide(Graphics g) {
        g.drawString("Space: Start a new day", 20, 40);
        g.drawString("1: Upgrade rocket speed", 20, 40 + STRING_DISTANCE_VERTICAL);
        g.drawString("2: Upgrade rocket fuel", 20, 40 + 2 * STRING_DISTANCE_VERTICAL);
        g.drawString("3: Upgrade rocket health", 20, 40 + 3 * STRING_DISTANCE_VERTICAL);
        g.drawString("4: Upgrade rocket steering", 20, 40 + 4 * STRING_DISTANCE_VERTICAL);
        g.drawString("R: Reset game", 20, 40 + 5 * STRING_DISTANCE_VERTICAL);
        g.drawString("S: save game", 20, 40 + 6 * STRING_DISTANCE_VERTICAL);
        g.drawString("L: Load game", 20, 40 + 7 * STRING_DISTANCE_VERTICAL);
    }

}
