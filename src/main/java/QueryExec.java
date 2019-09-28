import java.lang.Class;
import java.lang.reflect.Method;

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

    private static final DB mongoDB = ConnectorHelper.getMongoClient().getDB("final");

    /**
     *
     * @param queryNumber ("Q1", "Q8", "Q15", "Q20", "Q21", "Q22")
     */
    public static void query(String queryNumber) {
        try {
            // Build the query using random parameters
            QueryGen queryGen = new QueryGen();
            queryGen.generate(queryNumber);
            String queryScript = queryGen.getExecutableQuery(queryNumber);
            String explainScript = queryGen.getExplainQuery(queryNumber);
            String createIndexScript = queryGen.getCreateIndexQuery(queryNumber);

            // Execute queries with and without indexes
            execWithouIndexes(queryNumber, explainScript, queryScript);
            execWithIndexes(queryNumber, createIndexScript, explainScript, queryScript);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     */
    private static void clearCache() {
        mongoDB.eval("db.customers.getPlanCache().clear();");
        mongoDB.eval("db.deals.getPlanCache().clear();");
    }

    /**
     *
     */
    private static void clearIndexes() {
        mongoDB.eval("db.customers.dropIndexes();");
        mongoDB.eval("db.deals.dropIndexes();");
    }

    /**
     *
     */
    private static void clearProfiler() {
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
     * @param queryNumber
     * @param explainScript
     * @param queryScript
     */
    private static void execWithouIndexes(
        String queryNumber,
        String explainScript,
        String queryScript
    ) {
        // Clear cache
        clearCache();

        // Clear indexes
        clearIndexes();

        // Explain
        LoggerHelper.addLog(queryNumber, "Explain (w/o indexes) = " + mongoDB.eval(explainScript).toString());

        // Exec without indexes
        clearProfiler();
        LoggerHelper.addLog(queryNumber, "Result (w/o indexes) = " + mongoDB.eval(queryScript).toString());
        LoggerHelper.addLog(queryNumber, "Execution Time (w/o indexes) (in millis) = " + getExecutionTime().toString());
    }

    /**
     *
     * @param queryNumber
     * @param createIndexScript
     * @param queryScript
     */
    private static void execWithIndexes(
        String queryNumber,
        String createIndexScript,
        String explainScript,
        String queryScript
    ) {
        // Clear cache
        clearCache();

        // Create indexes
        mongoDB.eval(createIndexScript);

        // Explain
        LoggerHelper.addLog(queryNumber, "Explain (w/ indexes) = " + mongoDB.eval(explainScript).toString());

        // Exec with indexes
        clearProfiler();
        LoggerHelper.addLog(queryNumber, "Result (w/ indexes) = " + mongoDB.eval(queryScript).toString());
        LoggerHelper.addLog(queryNumber, "Execution Time (w/ indexes) (in millis) = " + getExecutionTime().toString());
    }

    /**
     *
     * @return Integer
     */
    private static Integer getExecutionTime() {
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
    private static MongoCollection<Document> getProfile() {
        return ConnectorHelper.getMongoDatabase("final").getCollection("system.profile");
    }

}