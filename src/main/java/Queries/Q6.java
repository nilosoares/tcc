import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q6 extends AbstractQuery {

    public int getNumber() {
        return 6;
    }

    public String getName() {
        return "Q6";
    }

    public int getNbOfTests() {
        return 14;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        parameters.put("__PARAM_END_DATE__", endDate);

        Integer quantity = RandomHelper.getRandomInteger(24, 25);
        parameters.put("__PARAM_QUANTITY__", quantity);

        Double discount = RandomHelper.getRandomInteger(2, 9) / (new Double(100));
        parameters.put("__PARAM_DISCOUNT__", discount);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("P_4_6_10");

        return names;
    }

}