import java.util.ArrayList;

class Q19_Mod extends AbstractQuery {

    public int getNumber() {
        return 19;
    }

    public String getName() {
        return "Q19_Mod";
    }

    public int getNbOfTests() {
        return 22;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        Integer quantity1 = RandomHelper.getRandomInteger(1, 10);
        parameters.put("__PARAM_QUANTITY_1__", quantity1);

        Integer quantity2 = RandomHelper.getRandomInteger(10, 20);
        parameters.put("__PARAM_QUANTITY_2__", quantity2);

        parameters.put("__PARAM_BRAND_1__", "Brand#14");

        parameters.put("__PARAM_BRAND_2__", "Brand#14");

        return parameters;
    }

    protected QueryIndexes getIndexes() {
        QueryIndexes names = new QueryIndexes();

        //names.add("P_4_13_14");
        names.add("P_4_13_14_1");
        names.add("P_4_13_14_2");
        names.add("P_4_13_14_3");
        names.add("P_4_13_14_4");
        names.add("P_4_13_14_5");
        names.add("P_4_13_14_6");
        names.add("P_4_13_14_7");
        names.add("P_4_13_14_8");
        names.add("P_4_13_14_9");
        names.add("P_4_13_14_10");
        names.add("P_4_13_14_11");
        names.add("P_4_13_14_12");
        names.add("P_4_13_14_13");
        names.add("P_4_13_14_14");
        names.add("P_4_13_14_15");
        names.add("P_4_13_14_16");
        names.add("C_303");

        return names;
    }

}