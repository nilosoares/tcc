(function() {

    // TPC-H Query 15 for MongoDB
    finalDb = db.getSiblingDB("final");

    // variables
    var start = new Date(__PARAM_START_DATE__);
    var end = new Date(__PARAM_END_DATE__);

    // month is 0-indexed
    var subquery = {
    	"shipdate": {
    		"$gte": start,
    		"$lt": end
    	}
    };

    // extracts the total revenue of each supplier
    return finalDb.deals.aggregate([
        {
            $match: subquery
        },
        {
            $project: {
                "_id": 0, // remove _id field
                "revenue": {
                    $multiply: [
                        "$extendedprice",
                        { $subtract : [1, "$discount"] }
                    ]
                },
                "supplier_no": "$partsupp.supplier.suppkey",
                "name": "$partsupp.supplier.name",
                "address": "$partsupp.supplier.address",
                "phone": "$partsupp.supplier.phone"
            }
        },
        {
            $group: {
                _id: "$supplier_no",
                total_revenue: { $sum : "$revenue" },
                name: { $first : "$name" },
                address: { $first : "$address" },
                phone: { $first : "$phone" }
            }
        },
        {
            $sort: {
                total_revenue : -1
            }
        },
        {
            $limit: 1
        }
    ]);

})();