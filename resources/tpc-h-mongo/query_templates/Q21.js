(function() {

    var finalDb = db.getSiblingDB("final");

    // Exists
    var exists = finalDb.deals.aggregate([
        {
            $project: {
                orderkey: "$order.orderkey",
                suppkey: "$partsupp.supplier.suppkey"
            }
        },
        {
            $out: "tmp_q21_1"
        }
    ]);

    // Not Exists
    var notExists = finalDb.deals.aggregate([
        {
            $match: {
                $expr: {
                    $gt: ["$receiptdate", "$commitdate"]
                }
            }
        },
        {
            $project: {
                orderkey: "$order.orderkey",
                suppkey: "$partsupp.supplier.suppkey"
            }
        },
        {
            $out: "tmp_q21_2"
        }
    ]);

    // Final Query
    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                $and: [
                    { "order.orderstatus": "F" },
                    { "partsupp.supplier.nation.name": { $eq : '__PARAM_COUNTRY__' } },
                    { $expr: { $gt: ["$receiptdate", "$commitdate"] } }
                ]
            }
        },
        {
            $lookup: {
                from: "tmp_q21_2",
                let: {
                    thisOrderKey: "$order.orderkey",
                    thisSuppKey: "$partsupp.supplier.suppkey"
                },
                pipeline: [
                    {
                        $match: {
                            $expr: {
                                $and: [
                                    { $eq: ["$orderkey",  "$$thisOrderKey"] },
                                    { $ne: ["$suppkey",  "$$thisSuppKey"] }
                                ]
                            }
                        }
                    }
                ],
                as: "onlyfail"
            }
        },
        {
            $match: {
                $expr: {
                    $eq: [{ $size: "$onlyfail" }, 0]
                }
            }
        },
        {
            $lookup: {
                from: "tmp_q21_1",
                let: {
                    thisOrderKey: "$order.orderkey",
                    thisSuppKey: "$partsupp.supplier.suppkey"
                },
                pipeline: [
                    {
                        $match: {
                            $expr: {
                                $and: [
                                    { $eq: ["$orderkey",  "$$thisOrderKey"] },
                                    { $ne: ["$suppkey",  "$$thisSuppKey"] }
                                ]
                            }
                        }
                    }
                ],
                as: "multisupp"
            }
        },
        {
            $match: {
                $expr: {
                    $gt: [{ $size: "$multisupp" }, 0]
                }
            }
        },
        {
            $group : {
                _id : "$partsupp.supplier.name",
                numwait : {
                    $sum : 1
                }
            }
        },
        {
            $sort : {
                numwait : -1,
                _id : 1
            }
        },
        {
            $limit: 1
        }
    ]);

})();
