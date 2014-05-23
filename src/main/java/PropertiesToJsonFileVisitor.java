import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

public class PropertiesToJsonFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // load properties file
        Properties properties = new Properties();
        properties.load(Files.newInputStream(file));
        // create new file
        String[] nameAndExtension = file.getFileName().toString().split("\\.");
        if (!nameAndExtension[1].equals("properties")) {
            System.err.println(file + "isn't a properties file");
        }

        Path newFile = file.getParent()
                .resolve(nameAndExtension[0] + ".json");
        OutputStream newFileOutputStream = Files.newOutputStream(newFile);
        // convert to JSON
        new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(newFileOutputStream, properties);

        return FileVisitResult.CONTINUE;
    }
}
