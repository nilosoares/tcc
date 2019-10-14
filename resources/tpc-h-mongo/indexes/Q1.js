(function() {

    var finalDb = db.getSiblingDB("final");

    // random date
    var delta = 50;
    var date = new Date(1998, 11, 1); // month is 0-indexed
    date.setDate(date.getDate() - delta);

    finalDb.deals.createIndex(
        {
            shipdate: 1
        },
        {
            name: "q1.deals.shipdate",
            partialFilterExpression: {
                "shipdate": { $lte: date }
            }
        }
    );

})();