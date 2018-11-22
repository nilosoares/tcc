import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CastHelper {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Object autoCast(String entry) {
        try {
            int asInt = Integer.valueOf(entry);
            return asInt;
        } catch (NumberFormatException e) {
            try {
                double asDouble = Double.valueOf(entry);
                return asDouble;
            } catch (Exception e1) {
                try {
                    // it's a phone number that should not be parsed into a date
                    if (entry.matches("[0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]")) {
                        return entry;
                    } else {
                        Date date = (Date) sdf.parse(entry);
                        return date;
                    }
                } catch (ParseException e2) {
                    return entry;
                }
            }
        }
    }

}