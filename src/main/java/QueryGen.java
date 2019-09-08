import java.lang.System;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONObject;

/**
 *
 * @author Nilo Soares
 */
public class QueryGen {

    public static final String TEMPLATE_PATH = "resources/tpc-h-mongo/template-queries/";
    public static final String QUERIES_PATH = "resources/tpc-h-mongo/executable-queries/";

    /**
     *
     * @param queryNumber
     */
    private Path getTemplate(String queryNumber) {
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
    public void query1() {
        Path destPath = getTemplate("Q1");

        // Delta
        Integer delta = RandomHelper.getRandomInteger(60, 120);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_DELTA__", delta.toString());
        LoggerHelper.info("Q1 / Parameter 1 (Delta) = " + delta.toString());
    }

    /**
     *
     */
    public void query8() {
        Path destPath = getTemplate("Q8");

        // Country and Region
        JSONObject country = RandomHelper.getRandomCountry();
        String countryName = (String) country.get("nation_name");
        String regionName = (String) country.get("region_name");
        FileSystemHelper.findAndReplace(destPath, "__PARAM_COUNTRY__", countryName);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_REGION__", regionName);
        LoggerHelper.info("Q8 / Parameter 1 (Country) = " + countryName);
        LoggerHelper.info("Q8 / Parameter 2 (Region) = " + regionName);

        // Type
        String type = RandomHelper.getRandomType();
        FileSystemHelper.findAndReplace(destPath, "__PARAM_TYPE__", type);
        LoggerHelper.info("Q8 / Parameter 3 (Type) = " + type);
    }

    /**
     *
     */
    public void query15() {
        Path destPath = getTemplate("Q15");

        // Start Date
        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 97, 9, 1);
        startDate.set(Calendar.DATE, 1);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_START_DATE__", DateHelper.format(startDate));
        LoggerHelper.info("Q15 / Parameter 1 (Date) = " + DateHelper.format(startDate));

        // End Date (Date + 3 months)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 3);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_END_DATE__", DateHelper.format(endDate));
    }

    /**
     *
     */
    public void query20() {
        Path destPath = getTemplate("Q20");

        // Date
        Integer year = RandomHelper.getRandomInteger(1993, 1997);
        Calendar startDate = DateHelper.getInstance(year-1900, 0, 1);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_START_DATE__", DateHelper.format(startDate));
        LoggerHelper.info("Q20 / Parameter 1 (Date) = " + DateHelper.format(startDate));

        // End Date (Date + 1 year)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(destPath, "__PARAM_END_DATE__", DateHelper.format(endDate));

        // Colors
        String color = RandomHelper.getRandomColor();
        FileSystemHelper.findAndReplace(destPath, "__PARAM_COLOR__", color);
        LoggerHelper.info("Q20 / Parameter 2 (Color) = " + color);

        // Country
        String country = RandomHelper.getRandomCountryName();
        FileSystemHelper.findAndReplace(destPath, "__PARAM_COUNTRY__", country);
        LoggerHelper.info("Q20 / Parameter 3 (Country) = " + country);
    }

    /**
     *
     */
    public void query21() {
        Path destPath = getTemplate("Q21");

        // Country
        String country = RandomHelper.getRandomCountryName();
        FileSystemHelper.findAndReplace(destPath, "__PARAM_COUNTRY__", country);
        LoggerHelper.info("Q21 / Parameter 1 (Country) = " + country);
    }

    /**
     *
     */
    public void query22() {
        Path destPath = getTemplate("Q22");
        ArrayList<Integer> countryCodes = new ArrayList<Integer>();

        // 7 unique country codes
        for (int i = 1; i <= 7; i++) {
            while (true) {
                Integer countryCode = RandomHelper.getRandomCountryCode();

                if (countryCodes.contains(countryCode)) {
                    continue;
                }

                countryCodes.add(countryCode);
                FileSystemHelper.findAndReplace(destPath, "__PARAM_COUNTRY_CODE_" + i + "__", countryCode.toString());
                LoggerHelper.info("Q22 / Parameter " + i + " (Country Code) = " + countryCode.toString());
                break;
            }
        }
    }

}