import java.util.Calendar;

class Q1_Mod extends AbstractQuery {

    public int getNumber() {
        return 1;
    }

    public String getName() {
        return "Q1_Mod";
    }

    public int getNbOfTests() {
        return 13;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        Calendar startDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        Calendar endDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        if (startDate.compareTo(endDate) > 0) {
            Calendar tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        parameters.put("__PARAM_START_DATE__", startDate);

        parameters.put("__PARAM_END_DATE__", endDate);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        names.add("P_10");

        return names;
    }

}