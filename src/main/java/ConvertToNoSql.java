import java.text.ParseException;
import java.sql.*;
import com.mongodb.*;

/**
 *
 * @author Nilo Soares
 */
public class ConvertToNoSql {

    private static final int FETCH_SIZE = 10000;

    private static final String MONGO_DATABASE_NAME = "final";
    private static final String MONGO_HOST = "mongo";
    private static final int MONGO_PORT = 27017;

    private static final String PGSQL_DATABASE_NAME = "tpch";
    private static final String PGSQL_HOST = "postgres";
    private static final int PGSQL_PORT = 5432;

    public static void main(String[] args) throws SQLException, ParseException {
        ConnectorHelper ch = new ConnectorHelper();

        // connect to Mongo
        DB mongoConn = ch.connectMongo(MONGO_HOST, MONGO_PORT, MONGO_DATABASE_NAME);

        // Drop the deals collection and create a new one
        DBCollection collection = mongoConn.getCollection("deals");
        collection.drop();

        // connect to PGSQL
        Connection pgsqlConn = ch.connectPostgres(PGSQL_HOST, PGSQL_PORT, PGSQL_DATABASE_NAME);
        pgsqlConn.setAutoCommit(false);

        // Fetch the data
        Statement st = pgsqlConn.createStatement();
        st.setFetchSize(FETCH_SIZE);
        ResultSet rs = st.executeQuery("" +
            "SELECT " +
                "lineitem.*, " +
                "orders.*, " +
                "customer.*, " +
                "cnation.n_nationkey AS cn_nationkey, " +
                "cnation.n_name AS cn_name, " +
                "cnation.n_regionkey AS cn_regionkey, " +
                "cnation.n_comment AS cn_comment, " +
                "cregion.r_regionkey AS cr_regionkey, " +
                "cregion.r_name AS cr_name, " +
                "cregion.r_comment AS cr_comment, " +
                "partsupp.*, " +
                "part.*, " +
                "supplier.*, " +
                "snation.n_nationkey AS sn_nationkey, " +
                "snation.n_name AS sn_name, " +
                "snation.n_regionkey AS sn_regionkey, " +
                "snation.n_comment AS sn_comment, " +
                "sregion.r_regionkey AS sr_regionkey, " +
                "sregion.r_name AS sr_name, " +
                "sregion.r_comment AS sr_comment " +
            "FROM lineitem " +
            "INNER JOIN orders ON orders.o_orderkey = lineitem.l_orderkey " +
            "INNER JOIN customer ON customer.c_custkey = orders.o_custkey " +
            "INNER JOIN nation AS cnation ON cnation.n_nationkey = customer.c_nationkey " +
            "INNER JOIN region AS cregion ON cregion.r_regionkey = cnation.n_regionkey " +
            "INNER JOIN partsupp ON partsupp.ps_partkey = lineitem.l_partkey AND partsupp.ps_suppkey = lineitem.l_suppkey " +
            "INNER JOIN part ON part.p_partkey = partsupp.ps_partkey " +
            "INNER JOIN supplier ON supplier.s_suppkey = partsupp.ps_suppkey " +
            "INNER JOIN nation AS snation ON snation.n_nationkey = supplier.s_nationkey " +
            "INNER JOIN region AS sregion ON sregion.r_regionkey = snation.n_regionkey "
        );

        // Convert each lineItem to MongoDB
        while (rs.next()) {
            // lineItem.order.customer.nation.region
            DBObject liocnr = new BasicDBObject();
            liocnr.put("regionkey", rs.getInt("cr_regionkey"));
            liocnr.put("name", rs.getString("cr_name").trim());
            liocnr.put("comment", rs.getString("cr_comment").trim());

            // lineItem.order.customer.nation
            DBObject liocn = new BasicDBObject();
            liocn.put("nationkey", rs.getInt("cn_nationkey"));
            liocn.put("name", rs.getString("cn_name").trim());
            liocn.put("comment", rs.getString("cn_comment").trim());
            liocn.put("region", liocnr);

            // lineItem.order.customer
            DBObject lioc = new BasicDBObject();
            lioc.put("custkey", rs.getInt("c_custkey"));
            lioc.put("name", rs.getString("c_name").trim());
            lioc.put("address", rs.getString("c_address").trim());
            lioc.put("phone", rs.getString("c_phone").trim());
            lioc.put("acctbal", CastHelper.castDouble(rs.getString("c_acctbal")));
            lioc.put("mktsegment", rs.getString("c_mktsegment").trim());
            lioc.put("comment", rs.getString("c_comment").trim());
            lioc.put("nation", liocn);

            // lineItem.order
            DBObject lio = new BasicDBObject();
            lio.put("orderkey", rs.getInt("o_orderkey"));
            lio.put("orderstatus", rs.getString("o_orderstatus").trim());
            lio.put("totalprice", CastHelper.castDouble(rs.getString("o_totalprice")));
            lio.put("orderdate", CastHelper.castDate(rs.getString("o_orderdate")));
            lio.put("orderpriority", rs.getString("o_orderpriority").trim());
            lio.put("clerk", rs.getString("o_clerk").trim());
            lio.put("shippriority", rs.getInt("o_shippriority"));
            lio.put("comment", rs.getString("o_comment").trim());
            lio.put("customer", lioc);

            // lineItem.partSupp.supplier.nation.region
            DBObject lipssnr = new BasicDBObject();
            lipssnr.put("regionkey", rs.getInt("sr_regionkey"));
            lipssnr.put("name", rs.getString("sr_name").trim());
            lipssnr.put("comment", rs.getString("sr_comment").trim());

            // lineItem.partSupp.supplier.nation
            DBObject lipssn = new BasicDBObject();
            lipssn.put("nationkey", rs.getInt("sn_nationkey"));
            lipssn.put("name", rs.getString("sn_name").trim());
            lipssn.put("comment", rs.getString("sn_comment").trim());
            lipssn.put("region", lipssnr);

            // lineItem.partSupp.supplier
            DBObject lipss = new BasicDBObject();
            lipss.put("suppkey", rs.getInt("s_suppkey"));
            lipss.put("name", rs.getString("s_name").trim());
            lipss.put("address", rs.getString("s_address").trim());
            lipss.put("phone", rs.getString("s_phone").trim());
            lipss.put("acctbal", CastHelper.castDouble(rs.getString("s_acctbal")));
            lipss.put("comment", rs.getString("s_comment").trim());
            lipss.put("nation", lipssn);

            // lineItem.partSupp.part
            DBObject lipsp = new BasicDBObject();
            lipsp.put("partkey", rs.getInt("p_partkey"));
            lipsp.put("name", rs.getString("p_name").trim());
            lipsp.put("mfgr", rs.getString("p_mfgr").trim());
            lipsp.put("brand", rs.getString("p_brand").trim());
            lipsp.put("type", rs.getString("p_type").trim());
            lipsp.put("size", rs.getInt("p_size"));
            lipsp.put("container", rs.getString("p_container").trim());
            lipsp.put("retailprice", CastHelper.castDouble(rs.getString("p_retailprice")));
            lipsp.put("comment", rs.getString("p_comment").trim());

            // lineItem.partSupp
            DBObject lips = new BasicDBObject();
            lips.put("availqty", rs.getInt("ps_availqty"));
            lips.put("supplycost", CastHelper.castDouble(rs.getString("ps_supplycost")));
            lips.put("comment", rs.getString("ps_comment").trim());
            lips.put("supplier", lipss);
            lips.put("part", lipsp);

            // lineItem
            DBObject li = new BasicDBObject();
            li.put("linenumber", rs.getInt("l_linenumber"));
            li.put("quantity", rs.getInt("l_quantity"));
            li.put("extendedprice", CastHelper.castDouble(rs.getString("l_extendedprice")));
            li.put("discount", CastHelper.castDouble(rs.getString("l_discount")));
            li.put("tax", CastHelper.castDouble(rs.getString("l_tax")));
            li.put("returnflag", rs.getString("l_returnflag").trim());
            li.put("linestatus", rs.getString("l_linestatus").trim());
            li.put("shipdate", CastHelper.castDate(rs.getString("l_shipdate")));
            li.put("commitdate", CastHelper.castDate(rs.getString("l_commitdate")));
            li.put("receiptdate", CastHelper.castDate(rs.getString("l_receiptdate")));
            li.put("shipinstruct", rs.getString("l_shipinstruct").trim());
            li.put("shipmode", rs.getString("l_shipmode").trim());
            li.put("comment", rs.getString("l_comment").trim());
            li.put("order", lio);
            li.put("partsupp", lips);

            collection.insert(li);
        }

        rs.close();
        st.close();
    }

}