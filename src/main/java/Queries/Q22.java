import java.nio.file.Path;
import java.util.ArrayList;

class Q22 extends AbstractQuery {

    public String getName() {
        return "Q22";
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
        ArrayList<Integer> countryCodes = new ArrayList<Integer>();

        // Parameter 1 - 7 unique country codes
        for (int i = 1; i <= 7; i++) {
            while (true) {
                Integer countryCode = RandomHelper.getRandomCountryCode();

                if (countryCodes.contains(countryCode)) {
                    continue;
                }

                countryCodes.add(countryCode);
                FileSystemHelper.findAndReplace(sTemplate, "__PARAM_COUNTRY_CODE_" + i + "__", countryCode.toString());
                FileSystemHelper.findAndReplace(eTemplate, "__PARAM_COUNTRY_CODE_" + i + "__", countryCode.toString());
                LoggerHelper.addLog(this.getName(), "Parameter " + i + " (Country Code) = " + countryCode.toString());
                break;
            }
        }
    }

}