import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;

class Q5 extends AbstractQuery {

    public int getNumber() {
        return 5;
    }

    public String getName() {
        return "Q5";
    }

    public int getNbOfTests() {
        return 10;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("C_104");

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Segment
        String region = RandomHelper.getRandomRegion();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_REGION__", region);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_REGION__", region);
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Region) = " + region);

        // Parameter 2 - Start Date
        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 3 - End Date (Date + 1 year)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 3 (End Date) = " + DateHelper.jsFormat(endDate));

    }

}