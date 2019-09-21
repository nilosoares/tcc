(function() {

    var finalDb = db.getSiblingDB("final");

    // variables
    var start = new Date(__PARAM_START_DATE__);
    var end = new Date(__PARAM_END_DATE__);

    // subquery indexes
    finalDb.deals.createIndex(
        {
            "shipdate": 1
        },
        {
            name: "q20.deals.shipdate",
            partialFilterExpression: {
                "shipdate": {
                    $gte: start,
                    $lt: end
                }
            }
        }
    );

    // run the query
    return finalDb.deals.createIndex(
       {
           "partsupp.supplier.name": 1
       },
       {
            name: "q20.deals.nation_name",
            partialFilterExpression: {
                "partsupp.supplier.nation.name": {
                    $eq: '__PARAM_COUNTRY__'
                }
            }
       }
   );

})();