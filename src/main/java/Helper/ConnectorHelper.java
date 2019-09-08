import io.github.cdimascio.dotenv.Dotenv;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorHelper {

    private final Dotenv dotenv;

    /**
     *
     */
    public ConnectorHelper() {
        this.dotenv = Dotenv.configure()
            .directory("/app")
            .filename(".env")
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();
    }

    /**
     *
     * @param host
     * @param port
     * @param dbName
     * @return MongoDatabase
     */
    public MongoDatabase connectMongo(String host, int port, String dbName) {
        MongoClient mongoClient = new MongoClient(host, port);

        return mongoClient.getDatabase(dbName);
    }

    /**
     *
     * @param dbName
     * @return MongoDatabase
     */
    public MongoDatabase connectMongo(String dbName) {
        String host = dotenv.get("MONGO_HOST");
        Integer port = CastHelper.castInt(dotenv.get("MONGO_PORT"));

        return this.connectMongo(host, port, dbName);
    }

    /**
     *
     * @return MongoDatabase
     */
    public MongoDatabase connectMongo() {
        String dbName = dotenv.get("MONGO_DATABASE");

        return this.connectMongo(dbName);
    }

    /**
     *
     * @param host
     * @param port
     * @param dbName
     * @return
     * @throws SQLException
     */
    public Connection connectPostgres(String host, int port, String dbName) throws SQLException {
        String user = dotenv.get("POSTGRES_USER");
        String pass = dotenv.get("POSTGRES_PASS");

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName + "?user=" + user + "&password=" + pass;

        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);

        return conn;
    }

    /**
     *
     * @param dbName
     * @return
     * @throws SQLException
     */
    public Connection connectPostgres(String dbName) throws SQLException {
        String host = dotenv.get("POSTGRES_HOST");
        Integer port = CastHelper.castInt(dotenv.get("POSTGRES_PORT"));

        return connectPostgres(host, port, dbName);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public Connection connectPostgres() throws SQLException {
        String dbName = dotenv.get("POSTGRES_DATABASE");

        return connectPostgres(dbName);
    }

}