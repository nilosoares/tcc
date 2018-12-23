// TPC-H Query 8 for MongoDB
db = db.getSiblingDB("final");

// variables
var start = new Date(1995, 0, 1); // month is 0-indexed
var end = new Date(1996, 11, 31);
var region = 'AMERICA';
var part_type = "ECONOMY ANODIZED STEEL";
var nation = 'BRAZIL'; // You must replace the value manually in the "red" function

// subquery
var subquery = {
    $and: [
        {
            "order.customer.nation.region.name": { $regex : '^' + region }
        },
        {
            "partsupp.part.type": part_type
        },
        {
            "order.orderdate": {
                "$gte": start,
                "$lte": end
            }
        }
    ]
};

var volume_each_nation = db.deals.aggregate([
    {
        $match: subquery // eliminate items which are not matching
    },
    {
        $project: {
            "_id": 0, // remove the id field
            "o_year": {
                $year: "$order.orderdate"
            }, // extract the year
            "volume": {
                $multiply: [
                    "$extendedprice",
                    { $subtract: [1, "$discount"] }
                ]
            },
            "nation" : "$partsupp.supplier.nation.name"
        }
    }
]);

// cache the result temporarily in the database
db.tmp_q8.drop();
db.tmp_q8.insert(volume_each_nation.toArray());

var result = db.tmp_q8.aggregate([
    {
        $group: {
            _id: "$o_year",
            total_sum: {
                $sum: "$volume"
            },
            mkt_share: {
                $sum: {
                    $cond: [
                        { $eq: ["$nation", "BRAZIL"] },
                        "$volume",
                        0
                    ]
                }
            }
        }
    },
    {
        $project: {
            mkt_share: {
                $divide: ["$mkt_share", "$total_sum"]
            }
        },
    },
    {
        $sort: { o_year: 1 }
    },
    {
        $limit: 1
    }
]);

// print the result
printjson(result.toArray());
