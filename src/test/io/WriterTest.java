package io;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JsonProvider;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {

    private static final File TEST_FILE = new File("data/testReaderWriter.json");
    private static final String CORRECT = "{\n  \"testProperty\": \"testValue\"\n}";

    private Writer writer;
    private Reader reader;

    @BeforeEach
    public void runBefore() {
        writer = new Writer(TEST_FILE);
        reader = new Reader(TEST_FILE);
    }

    @Test
    public void testWriteBytes() {
        try {
            writer.writeBytes(CORRECT.getBytes());
            assertEquals(CORRECT, new String(reader.readBytes()));
        } catch (IOException e) {
            fail("Write error");
        }
    }

    @Test
    public void testWriteJson() {
        JsonObject correctJson = new JsonObject();
        correctJson.addProperty("testProperty", "testValue");
        try {
            writer.writeJson(correctJson);
            assertEquals(CORRECT, JsonProvider.getGson().toJson(reader.readJson()));
        } catch (IOException e) {
            fail("Write error");
        }
    }

}
