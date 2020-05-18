import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q3 extends AbstractQuery {

    public int getNumber() {
        return 3;
    }

    public String getName() {
        return "Q3";
    }

    public int getNbOfTests() {
        return 12;
    }

    public Map<Integer, Object> getParameters() {
        Map<Integer, Object> parameters = new HashMap<Integer, Object>();

        String segment = RandomHelper.getRandomSegment();
        parameters.put(1, segment);

        Calendar date = RandomHelper.getRandomDate(95, 2, 1, 95, 2, 31);
        parameters.put(2, date);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("C_10");

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Segment
        String segment = RandomHelper.getRandomSegment();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_SEGMENT__", segment);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_SEGMENT__", segment);
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Segment) = " + segment);

        // Parameter 2 - Date
        Calendar date = RandomHelper.getRandomDate(95, 2, 1, 95, 2, 31);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_DATE__", DateHelper.jsFormat(date));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_DATE__", DateHelper.jsFormat(date));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (Date) = " + DateHelper.jsFormat(date));

    }

}