package ui;


import model.LiftOffConsoleGame;

import java.util.Scanner;

// console application for the LiftOff game
public class LiftOffConsoleApp {

    private LiftOffConsoleGame game;
    private Scanner input;

    // EFFECTS: runs the LiftOff game application
    public LiftOffConsoleApp() {

        runGame();
    }

    public void runGame() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayShop();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processShopCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    public void displayShop() {
        System.out.println("\nSelect from:");
        System.out.println("\tPlayer money: " + game.getRocket().playerMoney);
        System.out.println("\ts -> Start Game");
        System.out.println("\t1 -> upgrade rocket speed");
        System.out.println("\t2 -> upgrade rocket fuel");
        System.out.println("\t3 -> upgrade rocket health");
        System.out.println("\tq -> quit");
    }

    private void processShopCommand(String command) {
        if (command.equals("s")) {
            startGame();
        } else if (command.equals("1")) {
            if (game.upgradeSpeed()) {
                System.out.println("Bought Upgrade!");
            } else {
                System.out.println("Not enough money to buy upgrade");
            }
        } else if (command.equals("2")) {
            if (game.upgradeFuel()) {
                System.out.println("Bought Upgrade!");
            } else {
                System.out.println("Not enough money to buy upgrade");
            }
        } else if (command.equals("3")) {
            if (game.upgradeHealth()) {
                System.out.println("Bought Upgrade!");
            } else {
                System.out.println("Not enough money to buy upgrade");
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    public void init() {
        game = new LiftOffConsoleGame();
        game.newGame();
        input = new Scanner(System.in);
    }

    public void startGame() {
        int command;

        game.newDay();
        boolean dayGoesOn = true;

        while (dayGoesOn) {
            game.updateObstacles();
            displayGameInterface();
            command = input.nextInt();
            processGameCommand(command);
            game.update();
            if (game.checkDayEnd()) {
                dayGoesOn = false;
            }
        }
    }

    public void processGameCommand(int command) {
        int newCommand;
        if ((0 < command) && (command < 100)) {
            game.moveRocket(command);
        } else {
            System.out.println("Invalid input, try again!");
            newCommand = input.nextInt();
            processGameCommand(newCommand);
        }
    }

    public void displayGameInterface() {
        System.out.println("Ship altitude: " + game.getRocket().getAlt());
        System.out.println("Ship fuel: " + game.getRocket().getFuel());
        System.out.println("Ship health: " + game.getRocket().getHealth());
        System.out.println("There are obstacles at:");
        System.out.println("\tx= " + game.getObstacles().get(0).getX());
        System.out.println("\tx= " + game.getObstacles().get(1).getX());
        System.out.println("\tx= " + game.getObstacles().get(2).getX());
        System.out.println("enter x value to move rocket:");
    }
}
