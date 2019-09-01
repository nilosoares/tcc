import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.System;

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
     * @param Integer min
     * @param Integer max
     * @return
     */
    public static Integer getRandomInteger(Integer min, Integer max) {
        Random random = new Random();
        Integer randomInt = random.nextInt(max - min + 1) + min;

        return randomInt;
    }

}