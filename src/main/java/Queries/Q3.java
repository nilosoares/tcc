import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q3 extends AbstractQuery {

    public int getNumber() {
        return 3;
    }

    public String getName() {
        return "Q3";
    }

    public int getNbOfTests() {
        return 12;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        String segment = RandomHelper.getRandomSegment();
        parameters.put("__PARAM_SEGMENT__", segment);

        Calendar date = RandomHelper.getRandomDate(95, 2, 1, 95, 2, 31);
        parameters.put("__PARAM_DATE__", date);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("C_10");

        return names;
    }

}