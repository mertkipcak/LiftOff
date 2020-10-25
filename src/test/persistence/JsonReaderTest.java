package persistence;

import model.Rocket;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Rocket rocket = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNewRocket() {
        JsonReader reader = new JsonReader("./data/testNewRocket.json");
        try {
            Rocket rocket = reader.read();
            assertEquals(0, rocket.getSpeedLevel());
            assertEquals(0, rocket.getHealthLevel());
            assertEquals(0, rocket.getSteeringLevel());
            assertEquals(0, rocket.getFuelLevel());
            assertEquals(0, rocket.playerMoney);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testAdvancedRocket() {
        JsonReader reader = new JsonReader("./data/testAdvancedRocket.json");
        try {
            Rocket rocket = reader.read();
            assertEquals(5, rocket.getSpeedLevel());
            assertEquals(6, rocket.getHealthLevel());
            assertEquals(0, rocket.getSteeringLevel());
            assertEquals(9, rocket.getFuelLevel());
            assertEquals(9500, rocket.playerMoney);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
