import java.util.Date;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.CursorType;

/**
 * @author Nilo Soares
 */
public class Monitor {

    private static MongoDatabase mongoDatabase;

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        // connect to MongoDB
        mongoDatabase = ConnectorHelper.getMongoDatabase("final");
        MongoCollection<Document> collection = mongoDatabase.getCollection("system.profile");

        // Filters
        Bson filters = Filters.and(
            Filters.ne("ns", "final.system.profile"),
            Filters.gt("ts", new Date())
        );

        // Get the query details using a tailable cursor
        MongoCursor<Document> cursor = collection.find(filters)
            .cursorType(CursorType.TailableAwait)
            .noCursorTimeout(true)
            .iterator();

        // Get the last docs
        while (cursor.hasNext()) {
            Document doc = cursor.next();

            // ...
        }
    }

}