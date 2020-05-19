import java.util.Calendar;

class Q6_Mod extends AbstractQuery {

    public int getNumber() {
        return 6;
    }

    public String getName() {
        return "Q6_Mod";
    }

    public int getNbOfTests() {
        return 14;
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

        Integer quantity = RandomHelper.getRandomInteger(2, 21);
        parameters.put("__PARAM_QUANTITY__", quantity);

        Double discount = RandomHelper.getRandomInteger(10, 19) / (new Double(1000));
        parameters.put("__PARAM_DISCOUNT__", discount);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        names.add("P_4_6_10");

        return names;
    }

}