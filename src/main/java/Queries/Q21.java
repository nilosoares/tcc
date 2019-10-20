import java.nio.file.Path;

class Q21 extends AbstractQuery {

    public String getName() {
        return "Q21";
    }

    public int getNbOfTests() {
        return 5;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();
        Path iTemplate = this.getCreateIndexTemplate();

        // Parameter 1 - Country
        String country = RandomHelper.getRandomCountryName();
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_COUNTRY__", country);
        FileSystemHelper.findAndReplace(iTemplate, "__PARAM_COUNTRY__", country);
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Country) = " + country);
    }

}