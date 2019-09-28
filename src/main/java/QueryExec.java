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

    private final DB mongoDB = ConnectorHelper.getMongoClient().getDB("final");
    private String queryNumber;
    private QueryGen queryGen;

    /**
     *
     * @param String queryNumber ("Q1", "Q8", "Q15", "Q20", "Q21", "Q22")
     */
    public QueryExec(String queryNumber) {
        this.queryNumber = queryNumber;
    }

    /**
     *
     * @param int nbOfTests
     */
    public void execute(int nbOfTests) {
        try {
            // Build the query using random parameters
            QueryGen queryGen = new QueryGen(queryNumber);
            queryGen.generate();
            String queryScript = queryGen.getExecutableQuery();
            String explainScript = queryGen.getExplainQuery();
            String createIndexScript = queryGen.getCreateIndexQuery();

            // Explain without indexes
            LoggerHelper.addLog(queryNumber, "Explain (w/o indexes) = " + mongoDB.eval(explainScript).toString());

            // Execute queries without indexes
            for (int i = 1; i <= nbOfTests; i++) {
                clearCache();
                clearProfiler();
                LoggerHelper.addLog(queryNumber, "Result (w/o indexes) = " + mongoDB.eval(queryScript).toString());
                LoggerHelper.addLog(queryNumber, "Execution Time (w/o indexes) (in millis) = " + getExecutionTime().toString());
            }

            // Create indexes
            mongoDB.eval(createIndexScript);

            // Explain with indexes
            LoggerHelper.addLog(queryNumber, "Explain (w/ indexes) = " + mongoDB.eval(explainScript).toString());

            // Execute queries without indexes
            for (int i = 1; i <= nbOfTests; i++) {
                clearCache();
                clearProfiler();
                LoggerHelper.addLog(queryNumber, "Result (w/ indexes) = " + mongoDB.eval(queryScript).toString());
                LoggerHelper.addLog(queryNumber, "Execution Time (w/ indexes) (in millis) = " + getExecutionTime().toString());
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
        mongoDB.eval("db.customers.getPlanCache().clear();");
        mongoDB.eval("db.deals.getPlanCache().clear();");
    }

    /**
     *
     */
    private void clearIndexes() {
        mongoDB.eval("db.customers.dropIndexes();");
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