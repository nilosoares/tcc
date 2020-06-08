(function() {

    var finalDb = db.getSiblingDB("final");

    var startDate = new Date(__PARAM_START_DATE__); // month is 0-indexed
    var endDate = new Date(__PARAM_END_DATE__);

    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                $and: [
                    { "shipdate": { $gt: startDate } },
                    { "shipdate": { $lt: endDate } }
                ]
            }
        },
        {
            $group: {
                "_id": null,
                "numerator": {
                    $sum: {
                        $cond: [
                            {
                                $eq: [
                                    { $substr: ["$partsupp.part.type", 0, 5] },
                                    'PROMO'
                                ] // begins with "PROMO"
                            },
                            {
                                $multiply: [
                                    "$extendedprice",
                                    { $subtract: [1, "$discount"] }
                                ]
                            },
                            0
                        ]
                    }
                },
                "denominator": {
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
                "promo_revenue": {
                    $divide: [
                        { $multiply: ["$numerator", 100] },
                        "$denominator"
                    ]
                }
            }
        }
    ], { allowDiskUse: true });

})();