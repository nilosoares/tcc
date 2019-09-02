import java.lang.System;

import java.nio.file.Path;

import java.util.Calendar;

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
    public static void main(String[] args) {
        query1();
        query15();
        query20();
    }

    /**
     *
     * @param queryNumber
     */
    private static Path getNewTemplate(String queryNumber) {
        Path destPath = null;

        try {
            String fileName = queryNumber + ".js";

            destPath = FileSystemHelper.copyFile(
                TEMPLATE_PATH + fileName,
                QUERIES_PATH + fileName
            );

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return destPath;
    }

    /**
     *
     */
    private static void query1() {
        Path destPath = getNewTemplate("Q1");

        // Delta
        Integer delta = RandomHelper.getRandomInteger(60, 120);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_DELTA__", delta.toString());
    }

    /**
     *
     */
    private static void query15() {
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

    /**
     *
     */
    private static void query20() {
        Path destPath = getNewTemplate("Q20");

        // Date
        Integer year = RandomHelper.getRandomInteger(1993, 1997);
        Calendar startDate = DateHelper.getInstance(year-1900, 0, 1);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_START_DATE__", DateHelper.format(startDate));

        // End Date
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_END_DATE__", DateHelper.format(endDate));

        // Colors
        String color = RandomHelper.getRandomColor();
        FileSystemHelper.findAndReplace(destPath, "__PARAM_COLOR__", color);

        // Country
        String country = RandomHelper.getRandomCountry();
        FileSystemHelper.findAndReplace(destPath, "__PARAM_COUNTRY__", country);
    }

}