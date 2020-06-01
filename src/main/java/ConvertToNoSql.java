import java.text.ParseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Nilo Soares
 */
public class ConvertToNoSql {

    private static final int CURSOR_SIZE = 10000;
    private static MongoDatabase mongoDatabase;
    private static Connection pgDatabase;

    /**
     *
     * @param args
     * @throws SQLException
     * @throws ParseException
     */
    public static void main(String[] args) throws SQLException, ParseException {
        // connect to MongoDB and PGSQL
        mongoDatabase = ConnectorHelper.getMongoDatabase("final");
        pgDatabase = ConnectorHelper.getPgDatabase();

        // Convert the data
        convertLineItems();
    }

    /**
     *
     */
    private static void convertLineItems() throws SQLException, ParseException {
        // Drop the "deals" collection
        MongoCollection<Document> collection = mongoDatabase.getCollection("deals");
        collection.drop();

        // Fetch all lineItems in PgSQL
        Statement st = pgDatabase.createStatement();
        st.setFetchSize(CURSOR_SIZE);
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
            collection.insertOne(getLineItem(rs));
        }

        rs.close();
        st.close();
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getCustomerRegion(ResultSet rs) throws SQLException {
        Document region = new Document();

        region.put("regionkey", rs.getInt("cr_regionkey"));
        region.put("name", rs.getString("cr_name").trim());
        region.put("comment", rs.getString("cr_comment").trim());

        return region;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getCustomerNation(ResultSet rs) throws SQLException {
        Document nation = new Document();

        nation.put("nationkey", rs.getInt("cn_nationkey"));
        nation.put("name", rs.getString("cn_name").trim());
        nation.put("comment", rs.getString("cn_comment").trim());
        nation.put("region", getCustomerRegion(rs));

        return nation;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getCustomer(ResultSet rs) throws SQLException {
        Document customer = new Document();

        customer.put("custkey", rs.getInt("c_custkey"));
        customer.put("name", rs.getString("c_name").trim());
        customer.put("address", rs.getString("c_address").trim());
        customer.put("phone", rs.getString("c_phone").trim());
        customer.put("acctbal", CastHelper.castDouble(rs.getString("c_acctbal")));
        customer.put("mktsegment", rs.getString("c_mktsegment").trim());
        customer.put("comment", rs.getString("c_comment").trim());
        customer.put("nation", getCustomerNation(rs));

        return customer;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getOrder(ResultSet rs) throws SQLException, ParseException {
        Document order = new Document();

        order.put("orderkey", rs.getInt("o_orderkey"));
        order.put("orderstatus", rs.getString("o_orderstatus").trim());
        order.put("totalprice", CastHelper.castDouble(rs.getString("o_totalprice")));
        order.put("orderdate", CastHelper.castDate(rs.getString("o_orderdate")));
        order.put("orderpriority", rs.getString("o_orderpriority").trim());
        order.put("clerk", rs.getString("o_clerk").trim());
        order.put("shippriority", rs.getInt("o_shippriority"));
        order.put("comment", rs.getString("o_comment").trim());
        order.put("customer", getCustomer(rs));

        return order;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getSupplierRegion(ResultSet rs) throws SQLException {
        Document region = new Document();

        region.put("regionkey", rs.getInt("sr_regionkey"));
        region.put("name", rs.getString("sr_name").trim());
        region.put("comment", rs.getString("sr_comment").trim());

        return region;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getSupplierNation(ResultSet rs) throws SQLException {
        Document nation = new Document();

        nation.put("nationkey", rs.getInt("sn_nationkey"));
        nation.put("name", rs.getString("sn_name").trim());
        nation.put("comment", rs.getString("sn_comment").trim());
        nation.put("region", getSupplierRegion(rs));

        return nation;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getSupplier(ResultSet rs) throws SQLException {
        Document supplier = new Document();

        supplier.put("suppkey", rs.getInt("s_suppkey"));
        supplier.put("name", rs.getString("s_name").trim());
        supplier.put("address", rs.getString("s_address").trim());
        supplier.put("phone", rs.getString("s_phone").trim());
        supplier.put("acctbal", CastHelper.castDouble(rs.getString("s_acctbal")));
        supplier.put("comment", rs.getString("s_comment").trim());
        supplier.put("nation", getSupplierNation(rs));

        return supplier;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getPart(ResultSet rs) throws SQLException {
        Document part = new Document();

        part.put("partkey", rs.getInt("p_partkey"));
        part.put("name", rs.getString("p_name").trim());
        part.put("mfgr", rs.getString("p_mfgr").trim());
        part.put("brand", rs.getString("p_brand").trim());
        part.put("type", rs.getString("p_type").trim());
        part.put("size", rs.getInt("p_size"));
        part.put("container", rs.getString("p_container").trim());
        part.put("retailprice", CastHelper.castDouble(rs.getString("p_retailprice")));
        part.put("comment", rs.getString("p_comment").trim());

        return part;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getPartSupp(ResultSet rs) throws SQLException {
        Document partSupp = new Document();

        partSupp.put("availqty", rs.getInt("ps_availqty"));
        partSupp.put("supplycost", CastHelper.castDouble(rs.getString("ps_supplycost")));
        partSupp.put("comment", rs.getString("ps_comment").trim());
        partSupp.put("supplier", getSupplier(rs));
        partSupp.put("part", getPart(rs));

        return partSupp;
    }

    /**
     *
     * @param rs
     * @return
     */
    private static final Document getLineItem(ResultSet rs) throws SQLException, ParseException {
        Document lineItem = new Document();

        lineItem.put("linenumber", rs.getInt("l_linenumber"));
        lineItem.put("quantity", rs.getInt("l_quantity"));
        lineItem.put("extendedprice", CastHelper.castDouble(rs.getString("l_extendedprice")));
        lineItem.put("discount", CastHelper.castDouble(rs.getString("l_discount")));
        lineItem.put("tax", CastHelper.castDouble(rs.getString("l_tax")));
        lineItem.put("returnflag", rs.getString("l_returnflag").trim());
        lineItem.put("linestatus", rs.getString("l_linestatus").trim());
        lineItem.put("shipdate", CastHelper.castDate(rs.getString("l_shipdate")));
        lineItem.put("commitdate", CastHelper.castDate(rs.getString("l_commitdate")));
        lineItem.put("receiptdate", CastHelper.castDate(rs.getString("l_receiptdate")));
        lineItem.put("shipinstruct", rs.getString("l_shipinstruct").trim());
        lineItem.put("shipmode", rs.getString("l_shipmode").trim());
        lineItem.put("comment", rs.getString("l_comment").trim());
        lineItem.put("order", getOrder(rs));
        lineItem.put("partsupp", getPartSupp(rs));

        return lineItem;
    }

}