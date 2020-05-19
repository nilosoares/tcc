import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q12 extends AbstractQuery {

    public int getNumber() {
        return 12;
    }

    public String getName() {
        return "Q12";
    }

    public int getNbOfTests() {
        return 7;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        String shipMode1 = RandomHelper.getRandomShipMode();
        parameters.put("__PARAM_SHIP_MODE_1__", shipMode1);

        while (true) {
            String shipMode2 = RandomHelper.getRandomShipMode();
            if (!shipMode1.equals(shipMode2)) {
                parameters.put("__PARAM_SHIP_MODE_2__", shipMode2);
                break;
            }
        }

        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        parameters.put("__PARAM_END_DATE__", endDate);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("C_12_14");

        return names;
    }

}