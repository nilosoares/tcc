import java.nio.file.Path;
import java.util.Map;

/**
 * @author Nilo Soares
 */
public class QueryGen extends AbstractGen {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        String[] queries = {
                // Alain's queries
                "Q1_Mod",
                "Q3_Mod",
                "Q5_Mod",
                "Q6_Mod",
                "Q10_Mod",
                "Q12_Mod",
                "Q14_Mod",
                "Q19_Mod"

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
            QueryParameters parameters = query.getParameters();

            qGen.generateQuery(query, parameters);
            qGen.generateExplain(query, parameters);
        }
    }

    public Path generateQuery(AbstractQuery query, QueryParameters parameters) {
        // Get an empty template
        Path queryPath = this.getTemplate(query.getName(), "query_templates", "mongo_query");

        // Replace the mongo method
        FileSystemHelper.findAndReplace(queryPath, "__PARAM_MONGO_METHOD__", "aggregate");

        // Replace parameters
        for (Map.Entry<String, String> parameter : parameters.getAllAsStrings().entrySet()) {
            FileSystemHelper.findAndReplace(queryPath, parameter.getKey(), parameter.getValue());
        }

        return queryPath;
    }

    public Path generateExplain(AbstractQuery query, QueryParameters parameters) {
        // Get an empty template
        Path explainPath = this.getTemplate(query.getName(), "query_templates", "mongo_explain");

        // Replace the mongo method
        FileSystemHelper.findAndReplace(explainPath, "__PARAM_MONGO_METHOD__", "explain('allPlansExecution').aggregate");

        // Replace parameters
        for (Map.Entry<String, String> parameter : parameters.getAllAsStrings().entrySet()) {
            FileSystemHelper.findAndReplace(explainPath, parameter.getKey(), parameter.getValue());
        }

        return explainPath;
    }

}