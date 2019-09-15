(function() {

    var finalDb = db.getSiblingDB("final");

    // variables
    var start = new Date(1995, 0, 1); // month is 0-indexed
    var end = new Date(1996, 11, 31);
    var region = "__PARAM_REGION__";
    var part_type = "__PARAM_TYPE__";

    // run query
    return finalDb.deals.createIndex(
        {
            o_year: 1
        },
        {
            partialFilterExpression: {
                "order.customer.nation.region.name": { $eq: region },
                "partsupp.part.type": { $eq: part_type },
                "order.orderdate": {
                   "$gte": start,
                   "$lte": end
               }
            }
        }
    );

})();