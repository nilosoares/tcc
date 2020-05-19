import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q6_Mod extends AbstractQuery {

    public int getNumber() {
        return 6;
    }

    public String getName() {
        return "Q6_Mod";
    }

    public int getNbOfTests() {
        return 14;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        Calendar startDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        Calendar endDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        if (startDate.compareTo(endDate) > 0) {
            Calendar tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        parameters.put("__PARAM_START_DATE__", startDate);

        parameters.put("__PARAM_END_DATE__", endDate);

        Integer quantity = RandomHelper.getRandomInteger(2, 21);
        parameters.put("__PARAM_QUANTITY__", quantity);

        Double discount = RandomHelper.getRandomInteger(10, 19) / (new Double(1000));
        parameters.put("__PARAM_DISCOUNT__", discount);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("P_4_6_10");

        return names;
    }

}