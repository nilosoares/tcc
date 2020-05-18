import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

class Q1 extends AbstractQuery {

    public int getNumber() {
        return 1;
    }

    public String getName() {
        return "Q1";
    }

    public int getNbOfTests() {
        return 13;
    }

    public Map<Integer, Object> getParameters() {
        Map<Integer, Object> parameters = new HashMap<Integer, Object>();

        Integer delta = RandomHelper.getRandomInteger(60, 120);
        parameters.put(1, delta);

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

        // Parameter 1 - Delta
        Integer delta = RandomHelper.getRandomInteger(60, 120);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_DELTA__", delta.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_DELTA__", delta.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Delta) = " + delta.toString());
    }

}