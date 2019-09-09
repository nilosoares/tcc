(function() {

    var finalDb = db.getSiblingDB("final");

    var exists = finalDb.deals.aggregate([
        {
            $project: {
                orderkey: "$order.orderkey",
                suppkey: "$partsupp.supplier.suppkey"
            }
        }
    ]);

    finalDb.tmp_q21_1.drop();
    finalDb.tmp_q21_1.insert(exists.toArray());

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
        }
    ]);

    finalDb.tmp_q21_2.drop();
    finalDb.tmp_q21_2.insert(notExists.toArray());

    return finalDb.deals.aggregate([
        {
            $match: {
                $and: [
                    { "order.orderstatus": "F" },
                    { "partsupp.supplier.nation.name": { $regex : '^__PARAM_COUNTRY__' } },
                    { $expr: { $gt: ["$receiptdate", "$commitdate"] } }
                ]
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
                    $and: [
                        { $gt: [{ $size: "$multisupp" }, 0] },
                        { $eq: [{ $size: "$onlyfail" }, 0] }
                    ]
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
