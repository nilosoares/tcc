import java.nio.file.Path;
import java.util.ArrayList;
import org.json.simple.JSONObject;

class Q8 extends AbstractQuery {

    public String getName() {
        return "Q8";
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
        JSONObject country = RandomHelper.getRandomCountry();

        // Parameter 1 - Start Date
        String countryName = (String) country.get("nation_name");
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_COUNTRY__", countryName);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_COUNTRY__", countryName);
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Country) = " + countryName);

        // Parameter 2 - Region
        String regionName = (String) country.get("region_name");
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_REGION__", regionName);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_REGION__", regionName);
        LoggerHelper.addLog(this.getName(), "Parameter 2 (Region) = " + regionName);

        // Parameter 3 - Type
        String type = RandomHelper.getRandomType();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_TYPE__", type);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_TYPE__", type);
        LoggerHelper.addLog(this.getName(), "Parameter 3 (Type) = " + type);

    }

}