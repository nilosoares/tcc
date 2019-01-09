// add log time
var queryStartedAt = new Date();
print('Query started at ' + queryStartedAt.toISOString());

// TPC-H Query 8 for MongoDB
db = db.getSiblingDB("final");

// variables
var start = new Date(1995, 0, 1); // month is 0-indexed
var end = new Date(1996, 11, 31);
var region = 'AMERICA';
var part_type = "ECONOMY ANODIZED STEEL";
var nation = 'BRAZIL';

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

var group = {
    _id: "$o_year",
    total_sum: {
        $sum: "$volume"
    },
    mkt_share: {
        $sum: {
            $cond: [
                { $eq: ["$nation", nation] },
                "$volume",
                0
            ]
        }
    }
};

var result = db.deals.aggregate([
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
    },
    {
        $group: group
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

// add log time
var queryEndedAt = new Date();
print('Query ended at ' + queryEndedAt.toISOString());
print('Execution time: ' + ((queryEndedAt - queryStartedAt) / 1000)) + ' seconds';