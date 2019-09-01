import java.lang.System;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Nilo Soares
 */
public class MongoDbGen {

    public static final String TEMPLATE_PATH = "resources/tpc-h-mongo/template-queries/";
    public static final String QUERIES_PATH = "resources/tpc-h-mongo/executable-queries/";

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        query1();
        query15();
    }

    /**
     *
     * @param queryNumber
     * @throws IOException
     */
    private static Path getNewTemplate(String queryNumber) throws IOException {
        String fileName = queryNumber + ".js";

        Path destPath = FileSystemHelper.copyFile(
            TEMPLATE_PATH + fileName,
            QUERIES_PATH + fileName
        );

        return destPath;
    }

    /**
     *
     * @throws IOException
     */
    private static void query1() throws IOException {
        Path destPath = getNewTemplate("Q1");

        // Delta
        Integer delta = RandomHelper.getRandomInteger(60, 120);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_DELTA__", delta.toString());
    }

    /**
     *
     * @throws IOException
     */
    private static void query15() throws IOException {
        Path destPath = getNewTemplate("Q15");

        // Start Date
        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 97, 9, 1);
        startDate.set(Calendar.DATE, 1);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_START_DATE__", DateHelper.format(startDate));

        // End Date
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 3);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_END_DATE__", DateHelper.format(endDate));
    }

}