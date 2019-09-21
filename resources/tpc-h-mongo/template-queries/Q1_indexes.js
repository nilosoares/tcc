(function() {

    var finalDb = db.getSiblingDB("final");

    // random date
    var delta = __PARAM_DELTA__;
    var date = new Date(1998, 11, 1); // month is 0-indexed
    date.setDate(date.getDate() - delta);

    // run query
    return finalDb.deals.createIndex(
        {
            returnflag: 1,
            linestatus: 1
        },
        {
            name: "q1.deals.shipdate",
            partialFilterExpression: {
                "shipdate": { $lte: date }
            }
        }
    );

})();