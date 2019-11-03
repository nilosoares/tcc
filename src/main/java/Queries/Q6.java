import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;

class Q6 extends AbstractQuery {

    public String getName() {
        return "Q6";
    }

    public int getNbOfTests() {
        return 14;
    }

    protected ArrayList<String> getIndexesNames() {
        ArrayList<String> names = new ArrayList();

        names.add("P_4_6_10");

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Start Date
        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 2 - End Date (Date + 1 year)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (End Date) = " + DateHelper.jsFormat(endDate));

        // Parameter 3 - Quantity
        Integer quantity = RandomHelper.getRandomInteger(24, 25);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_QUANTITY__", quantity.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_QUANTITY__", quantity.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 3 (Quantity) = " + quantity.toString());

        // Parameter 3 - Discount
        Double discount = RandomHelper.getRandomInteger(2, 9) / (new Double(100));
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_DISCOUNT__", discount.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_DISCOUNT__", discount.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 4 (Dicount) = " + discount.toString());

    }

}