import com.mongodb.DB;
import com.mongodb.MongoClient;

public class ConnectorHelper {

    public DB connectMongo(String host, int port, String name) {
        MongoClient mongoClient = new MongoClient(host, port);
        return mongoClient.getDB(name);
    }

}