import java.nio.file.Path;
import java.util.ArrayList;

abstract class AbstractQuery {

    private Path copyTemplateQuery(String copyName) {
        Path destPath = null;

        try {
            String templateFilePath = "resources/tpc-h-mongo/template-queries/" + this.getName() + ".js";
            String destFilePath = "resources/tpc-h-mongo/executable-queries/" + copyName + ".js";
            destPath = FileSystemHelper.copyFile(templateFilePath, destFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return destPath;
    }

    public ArrayList<String> getCreateIndexScripts() {
        ArrayList<String> names = this.getIndexesNames();
        ArrayList<String> scripts = new ArrayList();

        for (int i = 0; i < names.size(); i++) {
            String filePath = "resources/tpc-h-mongo/executable-indexes/" + names.get(i) + ".js";
            scripts.add(FileSystemHelper.getContent(filePath));
        }

        return scripts;
    }

    public String getExplainScript() {
        return FileSystemHelper.getContent(this.getExplainFilePath());
    }

    private String getExplainFilePath() {
        return "resources/tpc-h-mongo/executable-queries/" + this.getName() + "_explain.js";
    }

    protected Path getExplainTemplate() {
        Path path = this.copyTemplateQuery(this.getName() + "_explain");
        FileSystemHelper.findAndReplace(path, "__PARAM_MONGO_METHOD__", "explain('allPlansExecution').aggregate");

        return path;
    }

    public String getQueryScript() {
        return FileSystemHelper.getContent(this.getQueryFilePath());
    }

    private String getQueryFilePath() {
        return "resources/tpc-h-mongo/executable-queries/" + this.getName() + ".js";
    }

    protected Path getScriptTemplate() {
        Path path = this.copyTemplateQuery(this.getName());
        FileSystemHelper.findAndReplace(path, "__PARAM_MONGO_METHOD__", "aggregate");

        return path;
    }

    public abstract int getNumber();

    public abstract String getName();

    public abstract int getNbOfTests();

    protected abstract ArrayList<String> getIndexesNames();

    protected abstract void replaceParameters();

}