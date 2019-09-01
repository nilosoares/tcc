import java.util.Calendar;
import java.lang.StringBuilder;

/**
 *
 * @author Nilo Soares
 */
public class DateHelper {

    /**
     *
     * @param int y
     * @param int m
     * @param int d
     * @return Calendar
     */
    public static Calendar getInstance(int y, int m, int d) {
        Calendar date = Calendar.getInstance();
        date.set(y, m, d);

        return date;
    }

    /**
     *
     * @param Calendar date
     * @return String
     */
    public static String format(Calendar date) {
        StringBuilder builder = new StringBuilder("");

        return builder
            .append("\"")
            .append(1900 + date.get(Calendar.YEAR))
            .append("-")
            .append(date.get(Calendar.MONTH) + 1)
            .append("-")
            .append(date.get(Calendar.DATE))
            .append("\"")
            .toString();
    }

}