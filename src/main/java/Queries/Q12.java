import java.nio.file.Path;
import java.util.Calendar;

class Q12 extends AbstractQuery {

    public String getName() {
        return "Q12";
    }

    public int getNbOfTests() {
        return 7;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();
        Path iTemplate = this.getCreateIndexTemplate();

        // Parameter 1 - Ship Mode 1
        String shipMode1 = RandomHelper.getRandomShipMode();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_SHIP_MODE_1__", shipMode1);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_SHIP_MODE_1__", shipMode1);
        FileSystemHelper.findAndReplace(iTemplate, "__PARAM_SHIP_MODE_1__", shipMode1);
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Ship Mode 1) = " + shipMode1);

        // Parameter 2 - End Date (Date + 1 month)
        while (true) {
            String shipMode2 = RandomHelper.getRandomShipMode();
            if (shipMode1 != shipMode2) {
                FileSystemHelper.findAndReplace(sTemplate, "__PARAM_SHIP_MODE_2__", shipMode2);
                FileSystemHelper.findAndReplace(eTemplate, "__PARAM_SHIP_MODE_2__", shipMode2);
                FileSystemHelper.findAndReplace(iTemplate, "__PARAM_SHIP_MODE_2__", shipMode2);
                LoggerHelper.addLog(this.getName(), "Parameter 2 (Ship Mode 2) = " + shipMode2);
                break;
            }
        }

        // Parameter 3 - Start Date
        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(iTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 3 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 4 - End Date (Date + 1 year)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(iTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 4 (End Date) = " + DateHelper.jsFormat(endDate));
    }

}