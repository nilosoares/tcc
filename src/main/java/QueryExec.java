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
     * @return MongoCollection<Document>
     */
    private static MongoCollection<Document> getProfile() {
        return ConnectorHelper.getMongoDatabase("final").getCollection("system.profile");
    }

    /**
     *
     * @return Integer
     */
    private static Integer getLatestExecutionTime() {
        // Filters
        Bson filters = Filters.and(
            Filters.ne("ns", "final.system.profile")
        );

        // Get the query details using a tailable cursor
        MongoCursor<Document> cursor = getProfile()
            .find(filters)
            .limit(1)
            .cursorType(CursorType.TailableAwait)
            .noCursorTimeout(true)
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
     * @param queryNumber ("1", "8", "15", "20", "21", "22")
     */
    public static void query(String queryNumber) {
        // Clear the profile
        clearProfiler();

        try {
            // Build the query using random parameters
            QueryGen queryGen = new QueryGen();
            Path path = (Path) queryGen.getClass()
                    .getMethod("query" + queryNumber)
                    .invoke(queryGen);

            // Get the content of the query
            String script = FileSystemHelper.getContent(path);

            // Run the query
            mongoDB.eval(script);

            // Log the time
            Integer executionTime = getLatestExecutionTime();
            LoggerHelper.info("Q" + queryNumber + " / Execution Time (in millis) = " + executionTime.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}