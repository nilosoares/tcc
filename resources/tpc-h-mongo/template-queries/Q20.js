(function() {

    var finalDb = db.getSiblingDB("final");

    // variables
    var start = new Date(__PARAM_START_DATE__);
    var end = new Date(__PARAM_END_DATE__);

    // Find the half of the quantity first
    var halfQtyArr = finalDb.deals.aggregate([
        {
            $match: {
                "shipdate": {
                    $gte: start,
                    $lt: end
                }
            }
        },
        {
            $group: {
                _id: {
                    suppkey: "$partsupp.supplier.suppkey",
                    partkey: "$partsupp.part.partkey"
                },
                sum_qty: {
                    $sum: "$quantity"
                },
            }
        },
        {
            $project: {
                "_id": 0,
                "suppkey": "$_id.suppkey",
                "partkey": "$_id.partkey",
                "halfqty": {
                    $divide: ["$sum_qty", 2]
                }
            }
        },
        {
            $out: "tmp_q20"
        }
    ]);

    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                "partsupp.part.name": {
                    $regex: '^__PARAM_COLOR__',
                    $options: 'i' // option i makes case insensitive
                },
                "partsupp.supplier.nation.name": {
                    $eq: '__PARAM_COUNTRY__' // use regex because of whitespaces in the end
                }
            }
        },
        {
            $lookup: {
                from: "tmp_q20",
                let: {
                    thisAvailQty: "$partsupp.availqty",
                    thisSuppKey: "$partsupp.supplier.suppkey",
                    thisPartKey: "$partsupp.part.partkey"
                },
                pipeline: [
                    {
                        $match: {
                            $expr: {
                                $and: [
                                    { $eq: ["$suppkey", "$$thisSuppKey"] },
                                    { $ne: ["$partkey", "$$thisPartKey"] },
                                    { $gt: ["$$thisAvailQty", "$halfqty"] }
                                ]
                            }
                        }
                    }
                ],
                as: "subQueryArr"
            }
        },
        {
            $match: {
                $expr: {
                    $gt: [{ $size: "$subQueryArr" }, 0]
                }
            }
        },
        {
            $project: {
                "_id": 0,
                "partsupp.supplier.name": 1,
                "partsupp.supplier.address": 1
            }
        },
        {
            $sort: { "partsupp.supplier.name": 1 }
        },
        {
            $limit: 1
        }
    ]);

})();