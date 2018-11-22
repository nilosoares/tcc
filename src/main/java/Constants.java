import java.util.Arrays;
import java.util.List;

public final class Constants {

    /**
     * Customer
     */
    public static final List<String> MAP_CUSTOMER_1TO1 = Arrays.asList(
        "custkey",
        "name",
        "address",
        "nationkey",
        "phone",
        "acctbal",
        "mktsegment",
        "comment"
    );

    /**
     * Line Item
     */
    public static final List<String> MAP_LINEITEM_1TO1 = Arrays.asList(
        "orderkey",
        "partkey",
        "suppkey",
        "linenumber",
        "quantity",
        "extendedprice",
        "discount",
        "tax",
        "returnflag",
        "linestatus",
        "shipdate",
        "commitdate",
        "receiptdate",
        "shipinstruct",
        "shipmode",
        "comment"
    );

    /**
     * Nation
     */
    public static final List<String> MAP_NATION_1TO1 = Arrays.asList(
        "nationkey",
        "name",
        "regionkey",
        "comment"
    );

    /**
     * Orders
     */
    public static final List<String> MAP_ORDERS_1TO1 = Arrays.asList(
        "orderkey",
        "custkey",
        "orderstatus",
        "totalprice",
        "orderdate",
        "orderpriority",
        "clerk",
        "shippriority",
        "comment"
    );

    /**
     * Part
     */
    public static final List<String> MAP_PART_1TO1 = Arrays.asList(
        "partkey",
        "name",
        "mfgr",
        "brand",
        "type",
        "size",
        "container",
        "retailprice",
        "comment"
    );

    /**
     * Part Supp
     */
    public static final List<String> MAP_PARTSUPP_1TO1 = Arrays.asList(
        "partkey",
        "suppkey",
        "availqty",
        "supplycost",
        "comment"
    );

    /**
     * Region
     */
    public static final List<String> MAP_REGION_1TO1 = Arrays.asList(
        "regionkey",
        "name",
        "comment"
    );

    /**
     * Supplier
     */
    public static final List<String> MAP_SUPPLIER_1TO1 = Arrays.asList(
        "suppkey",
        "name",
        "address",
        "nationkey",
        "phone",
        "acctbal",
        "comment"
    );

}