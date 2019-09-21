import java.util.Calendar;
import java.lang.StringBuilder;
import java.text.SimpleDateFormat;

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
    public static String jsFormat(Calendar date) {
        StringBuilder builder = new StringBuilder("");

        return builder
            .append(1900 + date.get(Calendar.YEAR))
            .append(", ")
            .append(date.get(Calendar.MONTH))
            .append(", ")
            .append(date.get(Calendar.DATE))
            .toString();
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Calendar date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date.getTime());
    }

    /**
     *
     * @param format
     * @return
     */
    public static String format(String format) {
        return format(Calendar.getInstance(), format);
    }

}