import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bootstrap {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("invalid starting dir");
        }

        Path startingDir = Paths.get(args[0]);
        FileVisitor toJsonFileVisitor = new PropertiesToJsonFileVisitor();
        try {
            Files.walkFileTree(startingDir, toJsonFileVisitor);
        } catch (IOException e) {
            System.err.println("An error has occured during the walking of the file tree : " + e.getMessage());
        }
    }
}
