(function() {

    var finalDb = db.getSiblingDB("final");

    // variables
    var start = new Date(1995, 0, 1); // month is 0-indexed
    var end = new Date(1996, 11, 31);
    var region = "__PARAM_REGION__";
    var part_type = "__PARAM_TYPE__";
    var nation = "__PARAM_COUNTRY__";

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

    return finalDb.deals.aggregate([
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

})();
