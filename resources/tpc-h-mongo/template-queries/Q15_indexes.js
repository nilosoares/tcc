(function() {

    var finalDb = db.getSiblingDB("final");

    // variables
    var start = new Date(__PARAM_START_DATE__);
    var end = new Date(__PARAM_END_DATE__);

    // run query
    return finalDb.deals.createIndex(
        {
            total_revenue: -1
        },
        {
            partialFilterExpression: {
                "shipdate": {
                    "$gte": start,
                    "$lt": end
                }
            }
        }
    );

})();