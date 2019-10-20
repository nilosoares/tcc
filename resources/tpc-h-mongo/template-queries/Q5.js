(function() {

    var finalDb = db.getSiblingDB("final");

    var region = "__PARAM_REGION__";
    var startDate = new Date(__PARAM_START_DATE__); // month is 0-indexed
    var endDate = new Date(__PARAM_END_DATE__);

    return finalDb.deals.aggregate([
        {
            $match: {
                $and: [
                    { "order.customer.nation.region.name": { $eq: region } },
                    { "order.orderdate": { $gte: startDate } },
                    { "order.orderdate": { $lt: endDate } },
                    {
                        $expr: {
                            $eq: ["$order.customer.nation.nationkey", "$partsupp.supplier.nation.nationkey"]
                        }
                    }
                ]
            }
        },
        {
            $group: {
                "_id": {
                    "n_name": "$order.customer.nation.name"
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
                "n_name": "$_id.n_name",
                "revenue": 1
            }
        },
        {
            $sort: {
                "revenue": -1
            }
        },
        {
            $limit: 10
        }
    ]);

})();