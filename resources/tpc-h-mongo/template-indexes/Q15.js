(function() {

    var finalDb = db.getSiblingDB("final");

    // variables
    var start = new Date(__PARAM_START_DATE__);
    var end = new Date(__PARAM_END_DATE__);

    finalDb.deals.createIndex(
        {
            shipdate: -1
        },
        {
            name: "q15.deals.shipdate",
            partialFilterExpression: {
                "shipdate": {
                    "$gte": start,
                    "$lt": end
                }
            }
        }
    );

})();