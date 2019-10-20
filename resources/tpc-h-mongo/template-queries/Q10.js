(function() {

    var finalDb = db.getSiblingDB("final");

//    var startDate = new Date(__PARAM_START_DATE__); // month is 0-indexed
//    var endDate = new Date(__PARAM_END_DATE__);
    var startDate = new Date(1993, 9, 1); // month is 0-indexed
    var endDate = new Date(1994, 0, 1);

    return finalDb.deals.aggregate([
        {
            $match: {
                $and: [
                    { "order.orderdate": { $gte: startDate } },
                    { "order.orderdate": { $lt: endDate } },
                    { "returnflag": { $eq: "R" } }
                ]
            }
        },
        {
            $group: {
                "_id": {
                    "c_custkey": "$order.customer.custkey",
                    "c_name": "$order.customer.name",
                    "c_acctbal": "$order.customer.acctbal",
                    "c_phone": "$order.customer.phone",
                    "n_name": "$order.customer.nation.name",
                    "c_address": "$order.customer.address",
                    "c_comment": "$order.customer.comment"
                },
                "revenue": {
                    $sum: {
                        $multiply: [
                            "$extendedprice",
                            { $subtract: [1, "$discount"] }
                        ]
                    }
                }
            }
        },
        {
            $project: {
                "_id": 0,
                "c_custkey": "$_id.c_custkey",
                "c_name": "$_id.c_name",
                "c_acctbal": "$_id.c_acctbal",
                "n_name": "$_id.n_name",
                "c_address": "$_id.c_address",
                "c_phone": "$_id.c_phone",
                "c_comment": "$_id.c_comment",
                "revenue": 1
            }
        },
        {
            $sort: {
                "revenue": -1
            }
        },
        {
            $limit: 20
        }
    ]);

})();