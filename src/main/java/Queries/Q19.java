import java.nio.file.Path;
import java.util.ArrayList;

class Q19 extends AbstractQuery {

    public String getName() {
        return "Q19";
    }

    public int getNbOfTests() {
        return 22;
    }

    protected ArrayList<String> getIndexesNames() {
        ArrayList<String> names = new ArrayList();

        names.add("P_4_13_14");
        names.add("C_303");

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Quantity 1
        Integer quantity1 = RandomHelper.getRandomInteger(1, 10);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_QUANTITY_1__", quantity1.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_QUANTITY_1__", quantity1.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Quantity 1) = " + quantity1.toString());

        // Parameter 2 - Quantity 2
        Integer quantity2 = RandomHelper.getRandomInteger(10, 20);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_QUANTITY_2__", quantity2.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_QUANTITY_2__", quantity2.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 2 (Quantity 2) = " + quantity2.toString());

        // Parameter 3 - Quantity 3
        Integer quantity3 = RandomHelper.getRandomInteger(20, 30);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_QUANTITY_3__", quantity3.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_QUANTITY_3__", quantity3.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 3 (Quantity 3) = " + quantity3.toString());

        // Parameter 4, 5 and 6 - Brand 1, 2 and 3
        ArrayList<String> brands = new ArrayList<String>();
        String brand;
        for (int i = 1; i <= 3; i++) {
            int p = i + 3;

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
                    break;
                }
            }

            FileSystemHelper.findAndReplace(sTemplate, "__PARAM_BRAND_" + (Integer.toString(i)) + "__", brand);
            FileSystemHelper.findAndReplace(eTemplate, "__PARAM_BRAND_" + (Integer.toString(i)) + "__", brand);
            LoggerHelper.addLog(this.getName(), "Parameter " + Integer.toString(p) + " (Brand " + (Integer.toString(i)) + ") = " + brand);
        }
    }

}