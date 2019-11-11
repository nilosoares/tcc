(function() {

    var finalDb = db.getSiblingDB("final");

    var shipMode1 = "__PARAM_SHIP_MODE_1__";
    var shipMode2 = "__PARAM_SHIP_MODE_2__";
    var startDate = new Date(__PARAM_START_DATE__); // month is 0-indexed
    var endDate = new Date(__PARAM_END_DATE__);

    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                $and: [
                    { "shipmode": { $in: [shipMode1, shipMode2] } },
                    { "receiptdate": { $gte: startDate } },
                    { "receiptdate": { $lt: endDate } },
                    {
                        $expr: {
                            $lt: ["$commitdate", "$receiptdate"]
                        }
                    },
                    {
                        $expr: {
                            $lt: ["$shipdate", "$commitdate"]
                        }
                    }
                ]
            }
        },
        {
            $group: {
                "_id": {
                    "shipmode": "$shipmode",
                },
                "high_line_count": {
                    $sum: {
                        $cond: [
                            {
                                $in: [
                                    "$order.orderpriority",
                                    ['1-URGENT', '2-HIGH']
                                ]
                            },
                            1,
                            0
                        ]
                    }
                },
                "low_line_count": {
                    $sum: {
                        $cond: [
                            {
                                $in: [
                                    "$order.orderpriority",
                                    ['1-URGENT', '2-HIGH']
                                ]
                            },
                            0,
                            1
                        ]
                    }
                }
            }
        },
        {
            $project: {
                "_id": 0,
                "shipmode": "$_id.shipmode",
                "high_line_count": 1,
                "low_line_count": 1
            }
        },
        {
            $sort: {
                "shipmode": 1
            }
        },
    ], { allowDiskUse: true });

})();