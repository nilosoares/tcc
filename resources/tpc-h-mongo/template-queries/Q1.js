(function() {

    var finalDb = db.getSiblingDB("final");

    // random date
    var delta = __PARAM_DELTA__;
    var date = new Date(1998, 11, 1); // month is 0-indexed
    date.setDate(date.getDate() - delta);

    // run query
    return finalDb.deals.aggregate([
        {
            $match: {
                "shipdate" : { $lte: date }
            }
        },
        {
            $group: {
                _id: {
                    returnflag: "$returnflag",
                    linestatus: "$linestatus"
                },
                count_order: {
                    $sum: 1
                },
                sum_qty: {
                    $sum: "$quantity"
                },
                sum_base_price: {
                    $sum: "$extendedprice"
                },
                sum_disc_price: {
                    $sum: {
                        $multiply: [
                            "$extendedprice",
                            { $subtract: [1, "$discount"] }
                        ]
                    }
                },
                sum_charge: {
                    $sum: {
                        $multiply: [
                            "$extendedprice",
                            { $subtract: [1, "$discount"] },
                            { $sum: [1, "$tax"] }
                        ]
                    }
                },
                sum_disc: {
                    $sum: "$discount"
                }
            },
        },
        {
            $project: {
                _id: 0,
                returnflag: "$_id.returnflag",
                linestatus: "$_id.linestatus",
                sum_qty: 1,
                sum_base_price: 1,
                sum_disc_price: 1,
                sum_charge: 1,
                avg_qty: {
                    $divide: ["$sum_qty", "$count_order"]
                },
                avg_price: {
                    $divide: ["$sum_base_price", "$count_order"]
                },
                avg_disc: {
                    $divide: ["$sum_disc", "$count_order"]
                },
                count_order: 1
            }
        },
        {
            $sort: {
                returnflag: 1,
                linestatus: 1
            }
        },
        {
            $limit: 1
        }
    ]);

})();