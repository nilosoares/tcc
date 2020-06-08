class Q1 extends AbstractQuery {

    public int getNumber() {
        return 1;
    }

    public String getName() {
        return "Q1";
    }

    public int getNbOfTests() {
        return 13;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        Integer delta = RandomHelper.getRandomInteger(60, 120);
        parameters.put("__PARAM_DELTA__", delta);

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        //names.add("P_10");
        names.add("P_10_1");
        names.add("P_10_2");
        names.add("P_10_3");
        names.add("P_10_4");
        names.add("P_10_5");
        names.add("P_10_6");
        names.add("P_10_7");
        names.add("P_10_8");
        names.add("P_10_9");
        names.add("P_10_10");
        names.add("P_10_11");
        names.add("P_10_12");
        names.add("P_10_13");
        names.add("P_10_14");
        names.add("P_10_15");
        names.add("P_10_16");
        names.add("P_10_17");
        names.add("P_10_18");
        names.add("P_10_19");
        names.add("P_10_20");
        names.add("P_10_21");
        names.add("P_10_22");
        names.add("P_10_23");
        names.add("P_10_24");
        names.add("P_10_25");
        names.add("P_10_26");
        names.add("P_10_27");
        names.add("P_10_28");
        names.add("P_10_29");
        names.add("P_10_30");
        names.add("P_10_31");
        names.add("P_10_32");
        names.add("P_10_33");
        names.add("P_10_34");
        names.add("P_10_35");
        names.add("P_10_36");
        names.add("P_10_37");
        names.add("P_10_38");
        names.add("P_10_39");

        return names;
    }

}