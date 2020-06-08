import java.util.Calendar;

class Q5_Mod extends AbstractQuery {

    public int getNumber() {
        return 5;
    }

    public String getName() {
        return "Q5_Mod";
    }

    public int getNbOfTests() {
        return 10;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        parameters.put("__PARAM_REGION__", "AFRICA");

        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        parameters.put("__PARAM_END_DATE__", endDate);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        names.add("C_104");

        return names;
    }

}