import java.util.Calendar;

class Q14 extends AbstractQuery {

    public int getNumber() {
        return 14;
    }

    public String getName() {
        return "Q14";
    }

    public int getNbOfTests() {
        return 9;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 97, 11, 1);
        startDate.set(Calendar.DATE, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 1);
        parameters.put("__PARAM_END_DATE__", endDate);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        names.add("P_10");

        return names;
    }

}