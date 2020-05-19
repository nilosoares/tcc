import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q1_Mod extends AbstractQuery {

    public int getNumber() {
        return 1;
    }

    public String getName() {
        return "Q1_Mod";
    }

    public int getNbOfTests() {
        return 13;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        Calendar startDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        Calendar endDate = RandomHelper.getRandomDate(97, 0, 1, 98, 0, 1);
        if (startDate.compareTo(endDate) > 0) {
            Calendar tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        parameters.put("__PARAM_START_DATE__", startDate);

        parameters.put("__PARAM_END_DATE__", endDate);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("P_10");

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

        // Parameter 2 - End Date
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (End Date) = " + DateHelper.jsFormat(endDate));
    }

}