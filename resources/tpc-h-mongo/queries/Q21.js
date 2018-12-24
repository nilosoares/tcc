// TPC-H Query 21 for MongoDB
db = db.getSiblingDB("final");

var result = db.deals.aggregate([
    {
        $match: {
            $and: [
                { "order.orderstatus": "F", },
                { "partsupp.supplier.nation.name": { $regex : '^SAUDI ARABIA' }, },
                { $expr: { $gt: ["$receiptdate", "$commitdate"] }, }
            ]
        }
    },
    {
        $lookup: {
            from: "deals",
            let: {
                thisOrderKey: "$order.orderkey",
                thisSuppKey: "$partsupp.supplier.suppkey"
            },
            pipeline: [
                {
                    $match: {
                        $expr: {
                            $and: [
                                { $eq: ["$order.orderkey",  "$$thisOrderKey"] },
                                { $ne: ["$partsupp.supplier.suppkey",  "$$thisSuppKey"] }
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
            from: "deals",
            let: {
                thisOrderKey: "$order.orderkey",
                thisSuppKey: "$partsupp.supplier.suppkey"
            },
            pipeline: [
                {
                    $match: {
                        $expr: {
                            $and: [
                                { $eq: ["$order.orderkey",  "$$thisOrderKey"] },
                                { $ne: ["$partsupp.supplier.suppkey",  "$$thisSuppKey"] },
                                { $gt: ["$receiptdate", "$commitdate"] }
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
            $expr: { $gt: [{ $size: "$multisupp" }, 0] },
            $expr: { $eq: [{ $size: "$onlyfail" }, 0] }
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