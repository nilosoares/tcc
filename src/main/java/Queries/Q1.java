import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

class Q1 extends AbstractQuery {

    public int getNumber() {
        return 1;
    }

    public String getName() {
        return "Q1";
    }

    public int getNbOfTests() {
        return 13;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        Integer delta = RandomHelper.getRandomInteger(60, 120);
        parameters.put("__PARAM_DELTA__", delta);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("P_10");

        return names;
    }

}