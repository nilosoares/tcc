import java.nio.file.Path;
import java.util.ArrayList;

class Q21 extends AbstractQuery {

    public int getNumber() {
        return 21;
    }

    public String getName() {
        return "Q21";
    }

    public int getNbOfTests() {
        return 5;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        String country = RandomHelper.getRandomCountryName();
        parameters.put("__PARAM_COUNTRY__", country);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        return names;
    }

}