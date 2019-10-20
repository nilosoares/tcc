import java.nio.file.Path;

class Q1 extends AbstractQuery {

    public String getName() {
        return "Q1";
    }

    public int getNbOfTests() {
        return 13;
    }

    protected void replaceParameters() {
        Path sTemplate = this.getScriptTemplate();
        Path eTemplate = this.getExplainTemplate();
        Path iTemplate = this.getCreateIndexTemplate();

        // Parameter 1 - Delta
        Integer delta = RandomHelper.getRandomInteger(60, 120);
        FileSystemHelper.findAndReplace(sTemplate, "__PARAM_DELTA__", delta.toString());
        FileSystemHelper.findAndReplace(eTemplate, "__PARAM_DELTA__", delta.toString());
        FileSystemHelper.findAndReplace(iTemplate, "__PARAM_DELTA__", delta.toString());
        LoggerHelper.addLog(this.getName(), "Parameter 1 (Delta) = " + delta.toString());
    }

}