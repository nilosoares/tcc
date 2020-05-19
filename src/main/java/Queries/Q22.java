import java.nio.file.Path;
import java.util.ArrayList;

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

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

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