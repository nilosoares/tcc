import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;

class Q10 extends AbstractQuery {

    public String getName() {
        return "Q10";
    }

    public int getNbOfTests() {
        return 13;
    }

    protected ArrayList<String> getIndexesNames() {
        ArrayList<String> names = new ArrayList();

        names.add("P_8");
        names.add("C_104");

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Start Date
        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 95, 9, 1);
        startDate.set(Calendar.DATE, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 2 - End Date (Date + 3 months)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 3);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (End Date) = " + DateHelper.jsFormat(endDate));

    }

}