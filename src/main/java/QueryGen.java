import java.lang.System;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nilo Soares
 */
public class QueryGen {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        String[] queries = {
                // Alain's queries
                "Q1_Mod",
                "Q3",
                "Q5",
                "Q6_Mod",
                "Q10",
                "Q12",
                "Q14",
                "Q19"

                // Nico's queries
                //"Q1",
                //"Q8",
                //"Q15",
                //"Q20",
                //"Q21",
                //"Q22"
        };
        int nbQueries = queries.length;

        QueryGen qGen = new QueryGen();

        for (int i = 0; i < nbQueries; i++) {
            AbstractQuery query = QueryFactory.createFromName(queries[i]);

            qGen.generateQuery(query, query.getParameters());

            qGen.generateExplain(query, query.getParameters());
        }
    }

    private Path getTemplate(AbstractQuery query, String outputFolder) {
        Path path = null;

        try {
            String templateFilePath = "resources/tpc-h-mongo/templates/" + query.getName() + ".js";

            String destinationFileName = query.getName() + "_" + DateHelper.format("yyyyMMdd_Hms_S");
            String destinationFilePath = "output/" + outputFolder + "/" + destinationFileName + ".js";

            path = FileSystemHelper.copyFile(templateFilePath, destinationFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return path;
    }

    private Path replaceParameters(Path script, QueryParameters parameters) {
        for (Map.Entry parameter : parameters.entrySet()) {
            String parameterKey = parameter.getKey().toString();

            String parameterValue;
            if (parameter.getValue() instanceof Calendar) {
                parameterValue = DateHelper.jsFormat((Calendar) parameter.getValue());
            } else {
                parameterValue = parameter.getValue().toString();
            }

            FileSystemHelper.findAndReplace(script, parameterKey, parameterValue);
        }

        return script;
    }

    public Path getQueryTemplate(AbstractQuery query) {
        return this.getTemplate(query, "mongo_query");
    }

    public Path generateQuery(AbstractQuery query, QueryParameters parameters) {
        // Get an empty template
        Path queryScript = this.getQueryTemplate(query);

        // Replace the mongo method
        FileSystemHelper.findAndReplace(queryScript, "__PARAM_MONGO_METHOD__", "aggregate");

        // Replace parameters
        replaceParameters(queryScript, parameters);

        return queryScript;
    }

    public Path getExplainTemplate(AbstractQuery query) {
        return this.getTemplate(query, "mongo_explain");
    }

    public Path generateExplain(AbstractQuery query, QueryParameters parameters) {
        // Get an empty template
        Path explainScript = this.getExplainTemplate(query);

        // Replace the mongo method
        FileSystemHelper.findAndReplace(explainScript, "__PARAM_MONGO_METHOD__", "explain('allPlansExecution').aggregate");

        // Replace parameters
        replaceParameters(explainScript, parameters);

        return explainScript;
    }

}