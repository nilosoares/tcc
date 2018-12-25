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
                { $gt: ["$receiptdate", "$commitdate"] }
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
        $project : {
            _id : 0,
            s_name : "$partsupp.supplier.name"
        }
    },
    {
        $group : {
            _id : "$s_name",
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

printjson(result.toArray());