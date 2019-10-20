import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;

class Q20 extends AbstractQuery {

    public String getName() {
        return "Q20";
    }

    public int getNbOfTests() {
        return 5;
    }

    protected ArrayList<String> getIndexesNames() {
        ArrayList<String> names = new ArrayList();

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Date
        Integer year = RandomHelper.getRandomInteger(1993, 1997);
        Calendar startDate = DateHelper.getInstance(year-1900, 0, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 2 - End Date (Date + 1 year)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 2 (End Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 3 - Colors
        String color = RandomHelper.getRandomColor();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_COLOR__", color);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_COLOR__", color);
        LoggerHelper.addLog(this.getName(), "Parameter 3 (Color) = " + color);

        // Parameter 4 - Country
        String country = RandomHelper.getRandomCountryName();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_COUNTRY__", country);
        LoggerHelper.addLog(this.getName(), "Parameter 4 (Country) = " + country);
    }

}