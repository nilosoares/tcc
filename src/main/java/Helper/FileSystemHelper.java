import java.lang.System;

import java.io.FileReader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
    public static Path copyFile(String from, String dest) {
        Path fromPath = null;
        Path destPath = null;

        try {
            fromPath = Paths.get(from);
            destPath = Paths.get(dest);

            Files.deleteIfExists(destPath);
            Files.copy(fromPath, destPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return destPath;
    }

    /**
     *
     * @param Path path
     * @param String find
     * @param String replace
     */
    public static void findAndReplace(Path path, String find, String replace) {
        try {
            Charset charset = StandardCharsets.UTF_8;
            String content = getContent(path);
            content = content.replaceAll(find, replace);
            Files.write(path, content.getBytes(charset));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @param Path path
     */
    public static String getContent(Path path) {
        String content = "";

        try {
            Charset charset = StandardCharsets.UTF_8;
            content = new String(Files.readAllBytes(path), charset);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return content;
    }

    /**
     *
     * @param String file
     * @return JSONArray
     */
    public static JSONArray readJSONArray(String file) {
        Object obj = null;

        try {
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            obj = jsonParser.parse(reader);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return (JSONArray) obj;
    }

}