import java.io.FileReader;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

import java.lang.System;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nilo Soares
 */
public class RandomHelper {

    /**
     *
     * @param Calendar date1
     * @param Calendar date2
     * @return
     */
    public static Calendar getRandomDate(Calendar date1, Calendar date2) {
        long time = ThreadLocalRandom.current().nextLong(date1.getTimeInMillis(), date2.getTimeInMillis() + 1L);

        Calendar randomDate = Calendar.getInstance();
        randomDate.setTimeInMillis(time);

        return randomDate;
    }

    /**
     *
     * @param int y1
     * @param int m1
     * @param int d1
     * @param int y2
     * @param int m2
     * @param int d2
     * @return
     */
    public static Calendar getRandomDate(int y1, int m1, int d1, int y2, int m2, int d2) {
        Calendar date1 = DateHelper.getInstance(y1, m1, d1);
        Calendar date2 = DateHelper.getInstance(y2, m2, d2);

        return getRandomDate(date1, date2);
    }

    /**
     *
     * @return String
     */
    public static String getRandomColor() {
        String file = "resources/tpc-h-mongo/parameters/colors.json";
        JSONArray colors = FileSystemHelper.readJSONArray(file);

        int randomIndex = RandomHelper.getRandomInteger(0, colors.size() - 1);

        return (String) colors.get(randomIndex);
    }

    /**
     *
     * @return JSONObject
     */
    public static JSONObject getRandomCountry() {
        // Read the JSON files
        String countriesFile = "resources/tpc-h-mongo/parameters/countries.json";
        String regionsFile = "resources/tpc-h-mongo/parameters/regions.json";
        JSONArray countries = FileSystemHelper.readJSONArray(countriesFile);
        JSONArray regions = FileSystemHelper.readJSONArray(regionsFile);

        // Get random country
        int randomIndex = RandomHelper.getRandomInteger(0, countries.size() - 1);
        JSONObject country = (JSONObject) countries.get(randomIndex);

        // Get the region name
        int regionKey = ((Long) country.get("region_key")).intValue();
        String regionName = (String) regions.get(regionKey);

        // Store the region name
        country.put("region_name", regionName);

        return country;
    }

    /**
     *
     * @return String
     */
    public static String getRandomCountryName() {
        JSONObject country = getRandomCountry();

        return (String) country.get("nation_name");
    }

    /**
     *
     * @return String
     */
    public static Integer getRandomCountryCode() {
        JSONObject country = getRandomCountry();

        int nationKey = ((Long) country.get("nation_key")).intValue();
        int countryCode = nationKey + 10;

        return new Integer(countryCode);
    }

    /**
     *
     * @param int min
     * @param int max
     * @return
     */
    public static int getRandomInteger(int min, int max) {
        Integer randomInt = getRandomInteger(new Integer(min), new Integer(max));

        return randomInt.intValue();
    }

    /**
     *
     * @param Integer min
     * @param Integer max
     * @return
     */
    public static Integer getRandomInteger(Integer min, Integer max) {
        Random random = new Random();
        Integer randomInt = random.nextInt(max - min + 1) + min;

        return randomInt;
    }

    /**
     *
     * @return String
     */
    public static String getRandomRegion() {
        String file = "resources/tpc-h-mongo/parameters/regions.json";
        JSONArray regions = FileSystemHelper.readJSONArray(file);

        int randomIndex = RandomHelper.getRandomInteger(0, regions.size() - 1);

        return (String) regions.get(randomIndex);
    }

    /**
     *
     * @return
     */
    public static String getRandomSegment() {
        String file = "resources/tpc-h-mongo/parameters/segments.json";
        JSONArray segments = FileSystemHelper.readJSONArray(file);

        int randomIndex = RandomHelper.getRandomInteger(0, segments.size() - 1);

        return (String) segments.get(randomIndex);
    }

    /**
     *
     * @return String
     */
    public static String getRandomType() {
        String file = "resources/tpc-h-mongo/parameters/types.json";
        JSONArray types = FileSystemHelper.readJSONArray(file);

        int randomIndex = RandomHelper.getRandomInteger(0, types.size() - 1);

        return (String) types.get(randomIndex);
    }

}