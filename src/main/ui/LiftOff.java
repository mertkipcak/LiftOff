package ui;

import model.LiftOffGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LiftOff extends JFrame {

    private static final int INTERVAL = 20;
    private final LiftOffGame game;
    private final GamePanel gp;


    // Constructs the whole game
    // EFFECTS: sets up a window in which Lift Off game will be played
    public LiftOff() {
        super("Lift Off");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new LiftOffGame();
        gp = new GamePanel(game);
        add(gp);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }

    public static void main(String[] args) {
        new LiftOff();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, ae -> {
            game.update();
            gp.repaint();
        });

        t.start();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.controlKey(e.getKeyCode());
        }
    }
}
