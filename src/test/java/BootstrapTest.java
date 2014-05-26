import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class BootstrapTest {

    @Test
    public void testConversionOneFile() throws Exception {
        // create temp properties file
        Path tempFile = Files.createTempFile("test", ".properties");
        // add some values
        Properties properties = new Properties();
        for (int i = 0; i < 10; i++) {
            properties.setProperty("key" + i, "value" + i);
        }
        properties.store(Files.newOutputStream(tempFile, StandardOpenOption.CREATE), "no comments");
        // do the conversion in the path
        Files.walkFileTree(tempFile, new PropertiesToJsonFileVisitor());
        // check conversion
        Path jsonFile = getJsonFile(tempFile);
        JsonNode jsonNode = new ObjectMapper()
                .readTree(jsonFile.toFile());

        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            assertEquals(jsonNode.get((String) property.getKey()).asText(), property.getValue());
        }

        FilesManager.delete(tempFile);
    }

    @Test
    public void testConsersionMutipleFiles() throws IOException {
        // create temp properties file
        Path tempDir = Files.createTempDirectory("test");
        // create 10 files
        for (int i = 0; i < 50; i++) {
            Path path = tempDir.resolve("file" + i + ".properties");
            // add some values
            Properties properties = new Properties();
            for (int j = 0; j < 100; j++) {
                properties.setProperty("key" + j, "value" + j);
            }
            properties.store(Files.newOutputStream(path, StandardOpenOption.CREATE), "no comments");
        }
        // do the conversion in the path
        Files.walkFileTree(tempDir, new PropertiesToJsonFileVisitor());

        FilesManager.delete(tempDir);
    }

    private Path getJsonFile(Path oldFile) {
        return oldFile.getParent().resolve(oldFile.getFileName().toString().split("\\.")[0] + ".json");
    }

}