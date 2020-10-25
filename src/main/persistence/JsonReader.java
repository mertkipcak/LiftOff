package persistence;

import model.Rocket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Rocket read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return createRocket(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: this
    // EFFECTS: creates a rocket and calls adjustValues on it
    private Rocket createRocket(JSONObject jsonObject) {
        Rocket rocket = new Rocket(0);
        adjustValues(rocket, jsonObject);
        return rocket;
    }

    // MODIFIES: rocket
    // EFFECTS: sets the values of the rocket to the values in JSON object
    private void adjustValues(Rocket rocket, JSONObject jsonObject) {
        int steeringLevel = jsonObject.getInt("steeringLevel");
        int fuelLevel = jsonObject.getInt("fuelLevel");
        int speedLevel = jsonObject.getInt("speedLevel");
        int healthLevel = jsonObject.getInt("healthLevel");
        int playerMoney = jsonObject.getInt("playerMoney");
        rocket.setSteeringLevel(steeringLevel);
        rocket.setSpeedLevel(speedLevel);
        rocket.setHealthLevel(healthLevel);
        rocket.playerMoney = playerMoney;
        rocket.setFuelLevel(fuelLevel);
    }
}
