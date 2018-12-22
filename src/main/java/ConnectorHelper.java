import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.sql.*;

public class ConnectorHelper {

    public DB connectMongo(String host, int port, String dbName) {
        MongoClient mongoClient = new MongoClient(host, port);
        return mongoClient.getDB(dbName);
    }

    public Connection connectPostgres(String host, int port, String dbName) throws SQLException {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName + "?user=postgres&password=123456";
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

}