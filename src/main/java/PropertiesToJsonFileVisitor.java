import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Properties;

public class PropertiesToJsonFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // load properties file
        Properties properties = new Properties();
        properties.load(Files.newInputStream(file));
        // create new file
        Path newFile = file.getParent().resolve(file.getFileName().toString().split("\\.")[0]);
        OutputStream newFileOutputStream = Files.newOutputStream(newFile);
        // iterate and convert to JSON
        JsonFactory f = new JsonFactory();
        JsonGenerator g = f.createJsonGenerator(newFileOutputStream);

        g.writeStartObject();
        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            g.writeStringField((String) property.getKey(), (String) property.getValue());
        }
        g.writeEndObject();
        g.close();

        return FileVisitResult.CONTINUE;
    }
}
