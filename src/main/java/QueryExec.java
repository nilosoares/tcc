import java.util.ArrayList;
import java.util.Map;

import java.nio.file.Path;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.CursorType;
import com.mongodb.DB;

/**
 *
 * @author Nilo Soares
 */
public class QueryExec {

    private final DB mongoDB = ConnectorHelper.getMongoClient().getDB("final");

    /**
     *
     * @param query
     * @param debug
     */
    public void execute(AbstractQuery query, boolean debug) {
        int nbOfTests = debug ? 1 : query.getNbOfTests();

        try {
            LoggerHelper.addLog(query.getName(), "Running " + query.getName());

            // Generate explain and query using random parameters
            QueryGen qGen = new QueryGen();
            QueryParameters parameters = query.getParameters();
            Path queryPath = qGen.generateQuery(query, parameters);
            Path explainPath = qGen.generateExplain(query, parameters);
            String queryScript = FileSystemHelper.getContent(queryPath);
            String explainScript = FileSystemHelper.getContent(explainPath);

            // Log parameters
            LoggerHelper.addLog(query.getName(), "Running queries without indexes...");
            for (Map.Entry<String, String> parameter : parameters.getAllAsStrings().entrySet()) {
                LoggerHelper.addLog(query.getName(), parameter.getKey() + " = " + parameter.getValue());
            }

            // Delete all indexes
            LoggerHelper.addLog(query.getName(), "Removing indexes...");
            clearIndexes();

            // DbStats without indexes
            LoggerHelper.addLog(query.getName(), "db.stats = " + mongoDB.eval("db.deals.stats();").toString());

            // Run explain without indexes
            LoggerHelper.addLog(query.getName(), "Running explain...");
            LoggerHelper.addLog(query.getName(), "Explain (w/o indexes) = " + mongoDB.eval(explainScript).toString());

            // Execute queries without indexes
            LoggerHelper.addLog(query.getName(), "Running queries...");
            for (int i = 1; i <= nbOfTests; i++) {
                clearCache();
                clearProfiler();
                LoggerHelper.addLog(query.getName(), "Result (w/o indexes) = " + mongoDB.eval(queryScript).toString());
                LoggerHelper.addLog(query.getName(), "Execution Time (w/o indexes) = " + getExecutionTime().toString());
            }

            // Create indexes
            IndexGen iGen = new IndexGen();
            QueryIndexes indexes = query.getIndexes();
            LoggerHelper.addLog(query.getName(), "Creating indexes...");
            for (int i = 0; i < indexes.size(); i++) {
                Path indexPath = iGen.generateIndex(indexes.get(i));
                String indexScript = FileSystemHelper.getContent(indexPath);

                clearCache();
                clearProfiler();
                mongoDB.eval(indexScript);
                LoggerHelper.addLog(query.getName(), "Create Index Time (" + indexes.get(i) + ") = " + getExecutionTime().toString());
            }

            // DbStats with indexes
            LoggerHelper.addLog(query.getName(), "db.stats = " + mongoDB.eval("db.deals.stats();").toString());

            // Explain with indexes
            LoggerHelper.addLog(query.getName(), "Running explain...");
            LoggerHelper.addLog(query.getName(), "Explain (w/ indexes) = " + mongoDB.eval(explainScript).toString());

            // Execute queries with indexes
            LoggerHelper.addLog(query.getName(), "Running queries with indexes...");
            for (int i = 1; i <= nbOfTests; i++) {
                clearCache();
                clearProfiler();
                LoggerHelper.addLog(query.getName(), "Result (w/ indexes) = " + mongoDB.eval(queryScript).toString());
                LoggerHelper.addLog(query.getName(), "Execution Time (w/ indexes) = " + getExecutionTime().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     */
    private void clearCache() {
        mongoDB.eval("db.deals.getPlanCache().clear();");
    }

    /**
     *
     */
    private void clearIndexes() {
        mongoDB.eval("db.deals.dropIndexes();");
    }

    /**
     *
     */
    private void clearProfiler() {
        // Disable profiling
        mongoDB.eval("db.setProfilingLevel(0);");

        // Drop the current profile collection
        getProfile().drop();

        // Create a new one
        CreateCollectionOptions options = new CreateCollectionOptions();
        options.capped(true);
        options.sizeInBytes(10 * 1000 * 1000); // 10mbs

        ConnectorHelper.getMongoDatabase("final").createCollection("system.profile", options);

        // Enable profiling
        mongoDB.eval("db.setProfilingLevel(2);");
    }

    /**
     *
     * @return Integer
     */
    private Integer getExecutionTime() {
        // Filters
        Bson filters = Filters.and(
            Filters.ne("ns", "final.system.profile")
        );

        // Get the query details using a tailable cursor
        MongoCursor<Document> cursor = getProfile()
            .find(filters)
            .iterator();

        // Get the execution time of the latest query
        Integer totalExecutionTime = new Integer(0);
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            totalExecutionTime += doc.getInteger("millis");
        }

        return totalExecutionTime;
    }

    /**
     *
     * @return MongoCollection<Document>
     */
    private MongoCollection<Document> getProfile() {
        return ConnectorHelper.getMongoDatabase("final").getCollection("system.profile");
    }

}