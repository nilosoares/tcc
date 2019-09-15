(function() {

    var finalDb = db.getSiblingDB("final");

    // not exists index
    finalDb.deals.createIndex(
        {
            "order.orderkey": 1,
            "partsupp.supplier.suppkey": 1
        },
        {
           partialFilterExpression: {
               "receiptdate": {
                   $gt: "$commitdate"
               }
           }
       }
    );

    return finalDb.deals.createIndex(
       {
           "order.orderstatus": 1,
           "partsupp.supplier.nation.name": 1,
           "receiptdate": 1
       },
       {
           partialFilterExpression: {
               "order.orderstatus": { $eq: "F" },
               "partsupp.supplier.nation.name": { $eq: "__PARAM_COUNTRY__" },
               "receiptdate": { $gt: "$commitdate" }
           }
       }
   );

})();