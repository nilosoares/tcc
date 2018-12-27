import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CastHelper {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final static String phonePattern = "[0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]";

    /**
     *
     * @param entry
     * @return
     */
    public static Object autoCast(String entry) {
        try {
            return castInt(entry);
        } catch (NumberFormatException e1) {
            try {
                return castDouble(entry);
            } catch (Exception e2) {
                // it's a phone number that should not be parsed into a date
                if (entry.matches(phonePattern)) {
                    return entry;
                }

                try {
                    return castDate(entry);
                } catch (ParseException e) {
                    return entry;
                }
            }
        }
    }

    /**
     *
     * @param entry
     * @return
     */
    public static Integer castInt(String entry) {
        return Integer.valueOf(entry);
    }

    /**
     *
     * @param entry
     * @return
     */
    public static Double castDouble(String entry) {
        return Double.valueOf(entry);
    }

    /**
     *
     * @param entry
     * @return
     * @throws ParseException
     */
    public static Date castDate(String entry) throws ParseException {
        return (Date) sdf.parse(entry);
    }

}