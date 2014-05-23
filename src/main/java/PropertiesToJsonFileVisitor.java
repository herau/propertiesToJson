import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Properties;

/**
 * Created by n27 on 23/05/14.
 */
public class PropertiesToJsonFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // load properties file
        Properties properties = new Properties();
        properties.load(Files.newInputStream(file));
        // iterate and convert to JSON
        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            System.out.println();
        }
        
        return FileVisitResult.CONTINUE;
    }
}
