import java.util.Calendar;

class Q12_Mod extends AbstractQuery {

    public int getNumber() {
        return 12;
    }

    public String getName() {
        return "Q12_Mod";
    }

    public int getNbOfTests() {
        return 7;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        parameters.put("__PARAM_SHIP_MODE_1__", "REG AIR");

        parameters.put("__PARAM_SHIP_MODE_2__", "AIR");

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

        names.add("C_12_14");

        return names;
    }

}