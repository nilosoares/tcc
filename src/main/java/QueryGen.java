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
     * @param template
     * @param copyName
     * @return
     */
    private Path copyTemplate(String template, String copyName) {
        Path destPath = null;

        try {
            String templateFileName = template + ".js";
            String copyFileName = copyName + ".js";

            destPath = FileSystemHelper.copyFile(
                TEMPLATE_PATH + templateFileName,
                QUERIES_PATH + copyFileName
            );

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return destPath;
    }

    /**
     *
     * @param queryNumber
     * @return
     */
    private Path getQueryPath(String queryNumber) {
        Path path = copyTemplate(queryNumber, queryNumber);

        FileSystemHelper.findAndReplace(path, "__PARAM_MONGO_METHOD__", "aggregate");

        return path;
    }

    /**
     *
     * @param queryNumber
     * @return
     */
    private Path getExplainPath(String queryNumber) {
        Path path = copyTemplate(queryNumber, queryNumber + "_explain");

        FileSystemHelper.findAndReplace(path, "__PARAM_MONGO_METHOD__", "explain().aggregate");

        return path;
    }

    /**
     *
     * @param queryNumber
     * @return
     */
    private Path getCreateIndexPath(String queryNumber) {
        Path path = copyTemplate(queryNumber + "_indexes", queryNumber + "_indexes");

        return path;
    }

    /**
     *
     * @param queryNumber
     * @return
     */
    public String getExecutableQuery(String queryNumber) {
        String filePath = QUERIES_PATH + queryNumber + ".js";

        return FileSystemHelper.getContent(filePath);
    }

    /**
     *
     * @param queryNumber
     * @return
     */
    public String getExplainQuery(String queryNumber) {
        String filePath = QUERIES_PATH + queryNumber + "_explain.js";

        return FileSystemHelper.getContent(filePath);
    }

    /**
     *
     * @param queryNumber
     * @return
     */
    public String getCreateIndexQuery(String queryNumber) {
        String filePath = QUERIES_PATH + queryNumber + "_indexes.js";

        return FileSystemHelper.getContent(filePath);
    }

    /**
     *
     * @param queryNumber
     */
    public void generate(String queryNumber) {
        try {
            this.getClass().getMethod(queryNumber).invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     */
    public void Q1() {
        Path qPath = getQueryPath("Q1");
        Path ePath = getExplainPath("Q1");
        Path iPath = getCreateIndexPath("Q1");

        // Parameter 1 - Delta
        Integer delta = RandomHelper.getRandomInteger(60, 120);
        LoggerHelper.addLog("Q1", "Parameter 1 (Delta) = " + delta.toString());

        // Replace the parameters
        FileSystemHelper.findAndReplace(qPath, "__PARAM_DELTA__", delta.toString());
        FileSystemHelper.findAndReplace(ePath, "__PARAM_DELTA__", delta.toString());
        FileSystemHelper.findAndReplace(iPath, "__PARAM_DELTA__", delta.toString());
    }

    /**
     *
     */
    public void Q8() {
        Path qPath = getQueryPath("Q8");
        Path ePath = getExplainPath("Q8");
        Path iPath = getCreateIndexPath("Q8");
        JSONObject country = RandomHelper.getRandomCountry();

        // Parameter 1 - Country
        String countryName = (String) country.get("nation_name");
        FileSystemHelper.findAndReplace(qPath, "__PARAM_COUNTRY__", countryName);
        FileSystemHelper.findAndReplace(ePath, "__PARAM_COUNTRY__", countryName);
        FileSystemHelper.findAndReplace(iPath, "__PARAM_COUNTRY__", countryName);
        LoggerHelper.addLog("Q8", "Parameter 1 (Country) = " + countryName);

        // Parameter 2 - Region
        String regionName = (String) country.get("region_name");
        FileSystemHelper.findAndReplace(qPath, "__PARAM_REGION__", regionName);
        FileSystemHelper.findAndReplace(ePath, "__PARAM_REGION__", regionName);
        FileSystemHelper.findAndReplace(iPath, "__PARAM_REGION__", regionName);
        LoggerHelper.addLog("Q8", "Parameter 2 (Region) = " + regionName);

        // Parameter 3 - Type
        String type = RandomHelper.getRandomType();
        FileSystemHelper.findAndReplace(qPath, "__PARAM_TYPE__", type);
        FileSystemHelper.findAndReplace(ePath, "__PARAM_TYPE__", type);
        FileSystemHelper.findAndReplace(iPath, "__PARAM_TYPE__", type);
        LoggerHelper.addLog("Q8", "Parameter 3 (Type) = " + type);
    }

    /**
     *
     */
    public void Q15() {
        Path qPath = getQueryPath("Q15");
        Path ePath = getExplainPath("Q15");
        Path iPath = getCreateIndexPath("Q15");

        // Parameter 1 - Start Date
        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 97, 9, 1);
        startDate.set(Calendar.DATE, 1);
        FileSystemHelper.findAndReplace(qPath, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(ePath, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(iPath, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog("Q15", "Parameter 1 (Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 2 - End Date (Date + 3 months)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 3);
        FileSystemHelper.findAndReplace(qPath, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(ePath, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(iPath, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
    }

    /**
     *
     */
    public void Q20() {
        Path qPath = getQueryPath("Q20");
        Path ePath = getExplainPath("Q20");
        Path iPath = getCreateIndexPath("Q20");

        // Parameter 1 - Date
        Integer year = RandomHelper.getRandomInteger(1993, 1997);
        Calendar startDate = DateHelper.getInstance(year-1900, 0, 1);
        FileSystemHelper.findAndReplace(qPath, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(ePath, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(iPath, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog("Q20", "Parameter 1 (Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 2 - End Date (Date + 1 year)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(qPath, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(ePath, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(iPath, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));

        // Parameter 3 - Colors
        String color = RandomHelper.getRandomColor();
        FileSystemHelper.findAndReplace(qPath, "__PARAM_COLOR__", color);
        FileSystemHelper.findAndReplace(ePath, "__PARAM_COLOR__", color);
        FileSystemHelper.findAndReplace(iPath, "__PARAM_COLOR__", color);
        LoggerHelper.addLog("Q20", "Parameter 2 (Color) = " + color);

        // Parameter 4 - Country
        String country = RandomHelper.getRandomCountryName();
        FileSystemHelper.findAndReplace(qPath, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(ePath, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(iPath, "__PARAM_COUNTRY__", country);
        LoggerHelper.addLog("Q20", "Parameter 3 (Country) = " + country);
    }

    /**
     *
     */
    public void Q21() {
        Path qPath = getQueryPath("Q21");
        Path ePath = getExplainPath("Q21");
        Path iPath = getExplainPath("Q21");

        // Parameter 1 - Country
        String country = RandomHelper.getRandomCountryName();
        FileSystemHelper.findAndReplace(qPath, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(ePath, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(iPath, "__PARAM_COUNTRY__", country);
        LoggerHelper.addLog("Q21", "Parameter 1 (Country) = " + country);
    }

    /**
     *
     */
    public void Q22() {
        Path qPath = getQueryPath("Q22");
        Path ePath = getExplainPath("Q22");
        Path iPath = getCreateIndexPath("Q22");
        ArrayList<Integer> countryCodes = new ArrayList<Integer>();

        // Parameter 1 - 7 unique country codes
        for (int i = 1; i <= 7; i++) {
            while (true) {
                Integer countryCode = RandomHelper.getRandomCountryCode();

                if (countryCodes.contains(countryCode)) {
                    continue;
                }

                countryCodes.add(countryCode);
                FileSystemHelper.findAndReplace(qPath, "__PARAM_COUNTRY_CODE_" + i + "__", countryCode.toString());
                FileSystemHelper.findAndReplace(ePath, "__PARAM_COUNTRY_CODE_" + i + "__", countryCode.toString());
                FileSystemHelper.findAndReplace(iPath, "__PARAM_COUNTRY_CODE_" + i + "__", countryCode.toString());
                LoggerHelper.addLog("Q22", "Parameter " + i + " (Country Code) = " + countryCode.toString());
                break;
            }
        }
    }

}