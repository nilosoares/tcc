import java.util.Calendar;

class Q3_Mod extends AbstractQuery {

    public int getNumber() {
        return 3;
    }

    public String getName() {
        return "Q3_Mod";
    }

    public int getNbOfTests() {
        return 12;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        parameters.put("__PARAM_SEGMENT__", "AUTOMOBILE");

        Calendar date = RandomHelper.getRandomDate(95, 2, 1, 95, 2, 31);
        parameters.put("__PARAM_DATE__", date);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        names.add("C_10");

        return names;
    }

}