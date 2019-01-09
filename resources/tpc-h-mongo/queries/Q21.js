// add log time
var queryStartedAt = new Date();
print('Query started at ' + queryStartedAt.toISOString());

// TPC-H Query 21 for MongoDB
db = db.getSiblingDB("final");

var exists = db.deals.aggregate([
    {
        $project: {
            orderkey: "$order.orderkey",
            suppkey: "$partsupp.supplier.suppkey"
        }
    }
]);
db.tmp_q21_1.drop();
db.tmp_q21_1.insert(exists.toArray());

var notExists = db.deals.aggregate([
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
db.tmp_q21_2.drop();
db.tmp_q21_2.insert(notExists.toArray());

var result = db.deals.aggregate([
    {
        $match: {
            $and: [
                { "order.orderstatus": "F" },
                { "partsupp.supplier.nation.name": { $regex : '^SAUDI ARABIA' } },
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

// print result
printjson(result.toArray());

// add log time
var queryEndedAt = new Date();
print('Query ended at ' + queryEndedAt.toISOString());