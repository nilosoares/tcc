import java.nio.file.Path;
import java.util.Calendar;

class Q3 extends AbstractQuery {

    public String getName() {
        return "Q3";
    }

    public int getNbOfTests() {
        return 12;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Segment
        String segment = RandomHelper.getRandomSegment();
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Segment) = " + segment);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_SEGMENT__", segment);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_SEGMENT__", segment);

        // Parameter 2 - Date
        Calendar date = RandomHelper.getRandomDate(95, 2, 1, 95, 2, 31);
        LoggerHelper.addLog(this.getName(), "Parameter 2 (Date) = " + DateHelper.jsFormat(date));
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_DATE__", DateHelper.jsFormat(date));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_DATE__", DateHelper.jsFormat(date));

    }

}