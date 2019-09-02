// add log time
var queryStartedAt = new Date();
print('Query started at ' + queryStartedAt.toISOString());

// TPC-H Query 20 for MongoDB
db = db.getSiblingDB("final");

// Find the half of the quantity first
var halfQtyArr = db.deals.aggregate([
    {
        $match: {
            "shipdate": {
                $gte: new Date(__PARAM_START_DATE__),
                $lt: new Date(__PARAM_END_DATE__)
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
    }
]).toArray();

db.tmp_q20.drop();
db.tmp_q20.insert(halfQtyArr);

var result = db.deals.aggregate([
    {
        $match: {
            "partsupp.part.name": {
                $regex: '^__PARAM_COLOR__',
                $options: 'i' // option i makes case insensitive
            },
            "partsupp.supplier.nation.name": {
                $regex: '^__PARAM_COUNTRY__' // use regex because of whitespaces in the end
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

// print result
printjson(result.toArray());

// add log time
var queryEndedAt = new Date();
print('Query ended at ' + queryEndedAt.toISOString());
print('Execution time: ' + ((queryEndedAt - queryStartedAt) / 1000)) + ' seconds';