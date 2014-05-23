import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class BootstrapTest {

    @Test
    public void testConversionOneFile() throws Exception {
        // create temp properties file
        Path tempFile = Files.createTempFile("test", ".properties");
        // add some values
        Properties properties = new Properties();
        for (int i = 0; i < 10; i ++) {
            properties.setProperty("key"+i, "value"+i);
        }
        properties.store(Files.newOutputStream(tempFile, StandardOpenOption.CREATE), "no comments");
        // do the conversion in the path
        Files.walkFileTree(tempFile, new PropertiesToJsonFileVisitor());

        FilesManager.delete(tempFile);

        //TODO check json result
    }

    @Test
    public void testConsersionMutipleFiles() throws IOException {
        // create temp properties file
        Path tempDir = Files.createTempDirectory("test");
        // create 10 files
        for (int i = 0; i < 10; i ++) {
            Path path = tempDir.resolve("file" + i + ".properties");
            // add some values
            Properties properties = new Properties();
            for (int j = 0; j < 10; j ++) {
                properties.setProperty("key"+j, "value"+j);
            }
            properties.store(Files.newOutputStream(path, StandardOpenOption.CREATE), "no comments");
        }
        // do the conversion in the path
        Files.walkFileTree(tempDir, new PropertiesToJsonFileVisitor());

        FilesManager.delete(tempDir);
    }

}