import io.github.cdimascio.dotenv.Dotenv;

import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorHelper {

    private static final ConnectorHelper INSTANCE = new ConnectorHelper();

    private Dotenv dotenv = null;

    private MongoClient mongoClient = null;

    private Connection pgDatabase = null;

    /**
     *
     */
    private ConnectorHelper() {
        // Read .env file
        this.dotenv = Dotenv.configure()
            .directory("/app")
            .filename(".env")
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

        // Connect to MongoDB
        String mongoHost = dotenv.get("MONGO_HOST");
        Integer mongoPort = CastHelper.castInt(dotenv.get("MONGO_PORT"));
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
            .socketKeepAlive(true)
            .connectionsPerHost(10000)
            .build();
        mongoClient = new MongoClient(serverAddress, mongoClientOptions);

        // Connect to Postgres
        try {
            String pgHost = dotenv.get("POSTGRES_HOST");
            String pgPort = dotenv.get("POSTGRES_PORT");
            String pgUser = dotenv.get("POSTGRES_USER");
            String pgPass = dotenv.get("POSTGRES_PASS");
            String pgDb = dotenv.get("POSTGRES_DATABASE");
            String url = "jdbc:postgresql://" + pgHost + ":" + pgPort + "/" + pgDb + "?user=" + pgUser + "&password=" + pgPass;
            pgDatabase = DriverManager.getConnection(url);
            pgDatabase.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @return MongoClient
     */
    public static MongoClient getMongoClient() {
        return INSTANCE.mongoClient;
    }

    /**
     *
     * @param dbName
     * @return MongoDatabase
     */
    public static MongoDatabase getMongoDatabase(String dbName) {
        return INSTANCE.mongoClient.getDatabase(dbName);
    }

    /**
     *
     * @return Connection
     */
    public static Connection getPgDatabase() {
        return INSTANCE.pgDatabase;
    }

}