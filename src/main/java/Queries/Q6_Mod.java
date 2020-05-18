import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    public Map<Integer, Object> getParameters() {
        Map<Integer, Object> parameters = new HashMap<Integer, Object>();

        Calendar startDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        Calendar endDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        if (startDate.compareTo(endDate) > 0) {
            Calendar tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        parameters.put(1, startDate);

        parameters.put(2, endDate);

        Integer quantity = RandomHelper.getRandomInteger(2, 21);
        parameters.put(3, quantity);

        Double discount = RandomHelper.getRandomInteger(10, 19) / (new Double(1000));
        parameters.put(4, discount);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("P_4_6_10");

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        Calendar startDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        Calendar endDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        if (startDate.compareTo(endDate) > 0) {
            Calendar tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        // Parameter 1 - Start Date
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 2 - End Date (Date + 1 year)
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (End Date) = " + DateHelper.jsFormat(endDate));

        // Parameter 3 - Quantity
        Integer quantity = RandomHelper.getRandomInteger(2, 21);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_QUANTITY__", quantity.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_QUANTITY__", quantity.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 3 (Quantity) = " + quantity.toString());

        // Parameter 3 - Discount
        Double discount = RandomHelper.getRandomInteger(10, 19) / (new Double(1000));
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_DISCOUNT__", discount.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_DISCOUNT__", discount.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 4 (Dicount) = " + discount.toString());

    }

}