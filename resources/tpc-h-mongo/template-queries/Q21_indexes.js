(function() {

    var finalDb = db.getSiblingDB("final");

    // not exists index
    finalDb.deals.createIndex(
        {
            "order.orderkey": 1,
            "partsupp.supplier.suppkey": 1
        },
        {
            name: "q21.deals.receiptdate",
            partialFilterExpression: {
                "receiptdate": {
                    $gt: "$commitdate"
                }
            }
       }
    );

    finalDb.deals.createIndex(
       {
           "order.orderstatus": 1,
           "partsupp.supplier.nation.name": 1,
           "receiptdate": 1
       },
       {
            name: "q21.deals.orderstatus__nation_name__receiptdate",
            partialFilterExpression: {
                "order.orderstatus": { $eq: "F" },
                "partsupp.supplier.nation.name": { $eq: "__PARAM_COUNTRY__" },
                "receiptdate": { $gt: "$commitdate" }
            }
        }
   );

})();