import java.util.Calendar;

class Q6 extends AbstractQuery {

    public int getNumber() {
        return 6;
    }

    public String getName() {
        return "Q6";
    }

    public int getNbOfTests() {
        return 14;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        parameters.put("__PARAM_END_DATE__", endDate);

        Integer quantity = RandomHelper.getRandomInteger(24, 25);
        parameters.put("__PARAM_QUANTITY__", quantity);

        Double discount = RandomHelper.getRandomInteger(2, 9) / (new Double(100));
        parameters.put("__PARAM_DISCOUNT__", discount);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        //names.add("P_4_6_10");
        names.add("P_4_6_10_1");
        names.add("P_4_6_10_2");
        names.add("P_4_6_10_3");
        names.add("P_4_6_10_4");
        names.add("P_4_6_10_5");
        names.add("P_4_6_10_6");
        names.add("P_4_6_10_7");
        names.add("P_4_6_10_8");
        names.add("P_4_6_10_9");
        names.add("P_4_6_10_10");
        names.add("P_4_6_10_11");
        names.add("P_4_6_10_12");
        names.add("P_4_6_10_13");
        names.add("P_4_6_10_14");
        names.add("P_4_6_10_15");
        names.add("P_4_6_10_16");
        names.add("P_4_6_10_17");
        names.add("P_4_6_10_18");
        names.add("P_4_6_10_19");

        return names;
    }

}