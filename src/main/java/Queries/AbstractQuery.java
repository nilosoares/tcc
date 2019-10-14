import java.nio.file.Path;

abstract class AbstractQuery {

    private Path copyTemplate(String copyName) {
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

    public Path getCreateIndex() {
        return FileSystemHelper.getPath(this.getCreateIndexFilePath());
    }

    public String getCreateIndexScript() {
        return FileSystemHelper.getContent(this.getCreateIndexFilePath());
    }

    public String getCreateIndexFilePath() {
        return "resources/tpc-h-mongo/indexes/" + this.getName() + ".js";
    }

    public Path getExplain() {
        return FileSystemHelper.getPath(this.getExplainFilePath());
    }

    public String getExplainScript() {
        return FileSystemHelper.getContent(this.getExplainFilePath());
    }

    public String getExplainFilePath() {
        return "resources/tpc-h-mongo/executable-queries/" + this.getName() + "_explain.js";
    }

    protected Path getExplainTemplate() {
        Path path = this.copyTemplate(this.getName() + "_explain");
        FileSystemHelper.findAndReplace(path, "__PARAM_MONGO_METHOD__", "explain('allPlansExecution').aggregate");

        return path;
    }

    public Path getQuery() {
        return FileSystemHelper.getPath(this.getQueryFilePath());
    }

    public String getQueryScript() {
        return FileSystemHelper.getContent(this.getQueryFilePath());
    }

    public String getQueryFilePath() {
        return "resources/tpc-h-mongo/executable-queries/" + this.getName() + ".js";
    }

    protected Path getScriptTemplate() {
        Path path = this.copyTemplate(this.getName());
        FileSystemHelper.findAndReplace(path, "__PARAM_MONGO_METHOD__", "explain('allPlansExecution').aggregate");

        return path;
    }

    public abstract String getName();

    public abstract int getNbOfTests();

    protected abstract void replaceParameters();

}