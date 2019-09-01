import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 *
 * @author Nilo Soares
 */
public class FileSystemHelper {

    /**
     *
     * @param from
     * @param dest
     * @return
     * @throws IOException
     */
    public static Path copyFile(String from, String dest) throws IOException {
        Path fromPath = Paths.get(from);
        Path destPath = Paths.get(dest);

        Files.deleteIfExists(destPath);
        Files.copy(fromPath, destPath);

        return destPath;
    }

    /**
     *
     * @param path
     * @param find
     * @param replace
     * @throws IOException
     */
    public static void findAndReplace(Path path, String find, String replace) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path), charset);

        content = content.replaceAll(find, replace);

        Files.write(path, content.getBytes(charset));
    }

}