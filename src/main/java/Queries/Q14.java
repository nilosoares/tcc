import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q14 extends AbstractQuery {

    public int getNumber() {
        return 14;
    }

    public String getName() {
        return "Q14";
    }

    public int getNbOfTests() {
        return 9;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 97, 11, 1);
        startDate.set(Calendar.DATE, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 1);
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

        // Parameter 1 - Start Date
        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 97, 11, 1);
        startDate.set(Calendar.DATE, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 2 - End Date (Date + 1 month)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (End Date) = " + DateHelper.jsFormat(endDate));
    }

}