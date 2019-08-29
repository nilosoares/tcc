import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @author Nilo Soares
 */
public class MongoDbGen {

    /**
     *
     */
    private static MongoDbGenHelper helper;

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        helper = new MongoDbGenHelper();

        query1();
    }

    /**
     *
     */
    private static void query1() throws IOException {
        Path template = helper.getTemplate("1");

        // Delta
        Integer delta = helper.getRandomInteger(60, 120);
        helper.findAndReplace(template, "__PARAM_DELTA__", delta.toString());
    }

}