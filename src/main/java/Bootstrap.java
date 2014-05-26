import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bootstrap {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("invalid arguments");
        }

        for (String path : args) {
            Path startingDir = Paths.get(path);
            if (Files.notExists(startingDir)) {
                System.err.println("no existing path : " + startingDir);
                continue;
            }

            try {
                Files.walkFileTree(startingDir, new PropertiesToJsonFileVisitor());
            } catch (IOException e) {
                System.err.println("An error has occured during the walking of the file tree : " + e.getMessage());
            }
        }
    }
}
