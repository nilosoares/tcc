import org.json.simple.JSONObject;

class Q8 extends AbstractQuery {

    public int getNumber() {
        return 8;
    }

    public String getName() {
        return "Q8";
    }

    public int getNbOfTests() {
        return 5;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        JSONObject country = RandomHelper.getRandomCountry();

        String countryName = (String) country.get("nation_name");
        parameters.put("__PARAM_COUNTRY__", countryName);

        String regionName = (String) country.get("region_name");
        parameters.put("__PARAM_REGION__", regionName);

        String type = RandomHelper.getRandomType();
        parameters.put("__PARAM_TYPE__", type);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        return names;
    }

}