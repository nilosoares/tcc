(function() {

    var finalDb = db.getSiblingDB("final");

    var segment = "__PARAM_SEGMENT__";
    var date = new Date(__PARAM_DATE__); // month is 0-indexed

    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                "order.customer.mktsegment": { $eq: segment },
                "order.orderdate": { $lt: date },
                "shipdate": { $gt: date }
            }
        },
        {
            $group: {
                "_id": {
                    "o_orderkey": "$order.orderkey",
                    "o_orderdate": "$order.orderdate",
                    "o_shippriority": "$order.shippriority"
                },
                "revenue": {
                    $sum: {
                        $multiply: [
                            "$extendedprice",
                            { $subtract: [1, "$discount"] }
                        ]
                    }
                }
            }
        },
        {
            $project: {
                "_id": 0,
                "o_orderkey": "$_id.o_orderkey",
                "revenue": 1,
                "o_orderdate": "$_id.o_orderdate",
                "o_shippriority": "$_id.o_shippriority"
            }
        },
        {
            $sort: {
                "revenue": -1,
                "o_orderdate": 1
            }
        },
        {
            $limit: 10
        }
    ], { allowDiskUse: true });

})();