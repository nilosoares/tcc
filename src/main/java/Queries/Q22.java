import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Q22 extends AbstractQuery {

    public int getNumber() {
        return 22;
    }

    public String getName() {
        return "Q22";
    }

    public int getNbOfTests() {
        return 5;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        ArrayList<Integer> countryCodes = new ArrayList<Integer>();

        for (int i = 1; i <= 7; i++) {
            while (true) {
                Integer countryCode = RandomHelper.getRandomCountryCode();

                if (countryCodes.contains(countryCode)) {
                    continue;
                }

                countryCodes.add(countryCode);

                parameters.put("__PARAM_COUNTRY_CODE_" + i + "__", countryCode);

                break;
            }
        }

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        return names;
    }

}