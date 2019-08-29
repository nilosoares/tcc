import java.util.Random;
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
public class MongoDbGenHelper {

    /**
     *
     * @param path
     * @param find
     * @param replace
     * @throws IOException
     */
    public void findAndReplace(Path path, String find, String replace) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path), charset);

        content = content.replace(find, replace);

        Files.write(path, content.getBytes(charset));
    }

    /**
     *
     * @param queryNumber
     * @return
     * @throws IOException
     */
    public Path getTemplate(String queryNumber) throws IOException {
        String fileName = "Q" + queryNumber + ".js";
        Path templatePath = Paths.get("resources/tpc-h-mongo/queries/" + fileName);
        Path executablePath = Paths.get("resources/tpc-h-mongo/executable_queries/" + fileName);

        Files.deleteIfExists(executablePath);
        Files.copy(templatePath, executablePath);

        return executablePath;
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    public Integer getRandomInteger(Integer min, Integer max) {
        Random random = new Random();
        Integer integer = random.nextInt(max - min + 1) + min;

        return integer;
    }

}