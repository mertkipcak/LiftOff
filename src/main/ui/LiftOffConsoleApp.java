package ui;

import model.LiftOffConsoleGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// console application for the LiftOff game
public class LiftOffConsoleApp {
    private static final String JSON_STORE = "./data/rocket.json";
    private LiftOffConsoleGame game;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the LiftOff game application
    public LiftOffConsoleApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGame();
    }

    // EFFECTS: starts the game and processes the actions for the shop menu
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
            } else if (command.equals("r")) {
                game.newGame();
            } else if (command.equals("k")) {
                saveRocket();
            } else if (command.equals("l")) {
                loadRocket();
            } else {
                processShopCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays the menu on the console
    public void displayShop() {
        System.out.println("\nSelect from:");
        System.out.println("\tPlayer money: " + game.getRocket().playerMoney);
        System.out.println("\ts -> Start Game");
        System.out.println("\t1 -> upgrade rocket speed");
        System.out.println("\t2 -> upgrade rocket fuel");
        System.out.println("\t3 -> upgrade rocket health");
        System.out.println("\tr -> reset");
        System.out.println("\tk -> save");
        System.out.println("\tl -> load");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: processes the command entered during the shop interface
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

    // EFFECTS: initializes the game and resets everything.
    public void init() {
        game = new LiftOffConsoleGame();
        game.newGame();
        input = new Scanner(System.in);
    }

    // EFFECTS: If the command given through the method is not a number between 0 and 100 prints re-enter the command
    //          otherwise handles the actions during the game stage.
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

    // EFFECTS: If the inout is not between 0 and 100 then throws NumberOutOfRangeException
    //          otherwise processes the commands during the game stage.
    public void processGameCommand(int command) {
        if ((0 < command) && (command < 100)) {
            game.moveRocket(command);
        } else {
            System.out.println("Please input a number between 0 and 100");
        }

    }

    // EFFECTS: displays the menu for the game stage of LiftOff.
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

    // EFFECTS: writes the current attributes of the Rocket to the save file as a JSONObject
    private void saveRocket() {
        try {
            jsonWriter.open();
            jsonWriter.write(game.getRocket());
            jsonWriter.close();
            System.out.println("Saved game to:" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: changes the current rocket with the one saved in the json file.
    private void loadRocket() {
        try {
            game.setRocket(jsonReader.read());
            System.out.println("Successfully loaded from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
