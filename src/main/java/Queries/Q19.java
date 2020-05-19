import java.nio.file.Path;
import java.util.ArrayList;

class Q19 extends AbstractQuery {

    public int getNumber() {
        return 19;
    }

    public String getName() {
        return "Q19";
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

        Integer quantity3 = RandomHelper.getRandomInteger(20, 30);
        parameters.put("__PARAM_QUANTITY_3__", quantity3);

        ArrayList<String> brands = new ArrayList<String>();
        String brand;
        for (int i = 1; i <= 3; i++) {
            while (true) {
                Integer m = RandomHelper.getRandomInteger(1, 5);
                Integer n;

                while (true) {
                    n = RandomHelper.getRandomInteger(1, 5);
                    if (!m.equals(n)) {
                        break;
                    }
                }

                brand = "Brand#" + m.toString() + n.toString();
                if (!brands.contains(brand)) {
                    brands.add(brand);

                    parameters.put("__PARAM_BRAND_" + (Integer.toString(i)) + "__", brand);

                    break;
                }
            }
        }

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("P_4_13_14");
        names.add("C_303");

        return names;
    }

}