package persistence;

import model.Rocket;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Rocket rocket = new Rocket(1);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testNewRocket() {
        try {
            Rocket rocket = new Rocket(1);
            JsonWriter writer = new JsonWriter("./data/testNewRocket.json");
            writer.open();
            writer.write(rocket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testNewRocket.json");
            rocket = reader.read();
            assertEquals(0, rocket.getSpeedLevel());
            assertEquals(0, rocket.getHealthLevel());
            assertEquals(0, rocket.getSteeringLevel());
            assertEquals(0, rocket.getFuelLevel());
            assertEquals(0, rocket.playerMoney);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAdvancedRocket() {
        try {
            Rocket rocket = new Rocket(1);
            rocket.setSteeringLevel(5);
            rocket.setHealthLevel(3);
            rocket.setFuelLevel(7);
            rocket.setSpeedLevel(1);
            rocket.playerMoney = 8300;
            JsonWriter writer = new JsonWriter("./data/testWriterSecond.json");
            writer.open();
            writer.write(rocket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSecond.json");
            rocket = reader.read();
            assertEquals(1, rocket.getSpeedLevel());
            assertEquals(3, rocket.getHealthLevel());
            assertEquals(5, rocket.getSteeringLevel());
            assertEquals(7, rocket.getFuelLevel());
            assertEquals(8300, rocket.playerMoney);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
