import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q20 extends AbstractQuery {

    public int getNumber() {
        return 20;
    }

    public String getName() {
        return "Q20";
    }

    public int getNbOfTests() {
        return 5;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        Integer year = RandomHelper.getRandomInteger(1993, 1997);
        Calendar startDate = DateHelper.getInstance(year-1900, 0, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        parameters.put("__PARAM_END_DATE__", endDate);

        String color = RandomHelper.getRandomColor();
        parameters.put("__PARAM_COLOR__", color);

        String country = RandomHelper.getRandomCountryName();
        parameters.put("__PARAM_COUNTRY__", country);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        return names;
    }

}