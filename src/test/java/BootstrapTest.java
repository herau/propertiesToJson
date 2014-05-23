import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class BootstrapTest {

    @Test
    public void testConversion() throws Exception {
        Path tempFile = Files.createTempFile("test", ".properties");

        Properties properties = new Properties();
        properties.setProperty("key", "value");

        properties.store(Files.newOutputStream(tempFile, StandardOpenOption.CREATE), "no comments");

        Files.walkFileTree(tempFile, new PropertiesToJsonFileVisitor());

        FilesManager.delete(tempFile);
    }
}