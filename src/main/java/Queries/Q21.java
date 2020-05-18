import java.nio.file.Path;
import java.util.ArrayList;

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

    protected ArrayList<String> getIndexesNames() {
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