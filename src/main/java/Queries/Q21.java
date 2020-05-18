import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Q21 extends AbstractQuery {

    public int getNumber() {
        return 21;
    }

    public String getName() {
        return "Q21";
    }

    public int getNbOfTests() {
        return 5;
    }

    public Map<Integer, Object> getParameters() {
        Map<Integer, Object> parameters = new HashMap<Integer, Object>();

        String country = RandomHelper.getRandomCountryName();
        parameters.put(1, country);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        return names;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();

        // Parameter 1 - Country
        String country = RandomHelper.getRandomCountryName();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_COUNTRY__", country);
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Country) = " + country);
    }

}