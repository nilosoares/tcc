import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class Q12 extends AbstractQuery {

    public int getNumber() {
        return 12;
    }

    public String getName() {
        return "Q12";
    }

    public int getNbOfTests() {
        return 7;
    }

    public Map<Integer, Object> getParameters() {
        Map<Integer, Object> parameters = new HashMap<Integer, Object>();

        String shipMode1 = RandomHelper.getRandomShipMode();
        parameters.put(1, shipMode1);

        while (true) {
            String shipMode2 = RandomHelper.getRandomShipMode();
            if (!shipMode1.equals(shipMode2)) {
                parameters.put(2, shipMode2);
                break;
            }
        }

        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        parameters.put(3, startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        parameters.put(4, startDate);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("C_12_14");

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Ship Mode 1
        String shipMode1 = RandomHelper.getRandomShipMode();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_SHIP_MODE_1__", shipMode1);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_SHIP_MODE_1__", shipMode1);
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Ship Mode 1) = " + shipMode1);

        // Parameter 2 - End Date (Date + 1 month)
        while (true) {
            String shipMode2 = RandomHelper.getRandomShipMode();
            if (!shipMode1.equals(shipMode2)) {
                FileSystemHelper.findAndReplace(sTemplate, "__PARAM_SHIP_MODE_2__", shipMode2);
                FileSystemHelper.findAndReplace(eTemplate, "__PARAM_SHIP_MODE_2__", shipMode2);
                LoggerHelper.addLog(this.getName(), "Parameter 2 (Ship Mode 2) = " + shipMode2);
                break;
            }
        }

        // Parameter 3 - Start Date
        int year = (int) RandomHelper.getRandomInteger(93, 97);
        Calendar startDate = DateHelper.getInstance(year, 0, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_START_DATE__", DateHelper.jsFormat(startDate));
        LoggerHelper.addLog(this.getName(), "Parameter 3 (Start Date) = " + DateHelper.jsFormat(startDate));

        // Parameter 4 - End Date (Date + 1 year)
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.YEAR, 1);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_END_DATE__", DateHelper.jsFormat(endDate));
        LoggerHelper.addLog(this.getName(), "Parameter 4 (End Date) = " + DateHelper.jsFormat(endDate));
    }

}