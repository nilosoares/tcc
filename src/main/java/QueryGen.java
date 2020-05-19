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

            qGen.generateQuery(query);
        }
    }

    private Path getTemplate(AbstractQuery query, String folder) {
        Path path = null;

        try {
            String templateFilePath = "resources/tpc-h-mongo/templates/" + query.getName() + ".js";

            String destinationFileName = query.getName() + "_" + DateHelper.format("yyyyMMdd_Hms_S");
            String destinationFilePath = "output/" + folder + "/" + destinationFileName + ".js";

            path = FileSystemHelper.copyFile(templateFilePath, destinationFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return path;
    }

    public Path generateQuery(AbstractQuery query) {
        // Get an empty template
        Path queryScript = this.getTemplate(query, "mongo_query");
        Path explainScript = this.getTemplate(query, "mongo_explain");

        // Replace the mongo method
        FileSystemHelper.findAndReplace(queryScript, "__PARAM_MONGO_METHOD__", "aggregate");
        FileSystemHelper.findAndReplace(explainScript, "__PARAM_MONGO_METHOD__", "explain('allPlansExecution').aggregate");

        // Replace parameters
        Map<String, Object> parameters = query.getParameters();
        for (Map.Entry parameter : parameters.entrySet()) {
            String parameterKey = parameter.getKey().toString();

            String parameterValue;
            if (parameter.getValue() instanceof Calendar) {
                parameterValue = DateHelper.jsFormat((Calendar) parameter.getValue());
            } else {
                parameterValue = parameter.getValue().toString();
            }

            FileSystemHelper.findAndReplace(queryScript, parameterKey, parameterValue);
            FileSystemHelper.findAndReplace(explainScript, parameterKey, parameterValue);
        }

        return queryScript;
    }

}