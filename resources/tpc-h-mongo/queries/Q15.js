// add log time
var queryStartedAt = new Date();
print('Query started at ' + queryStartedAt.toISOString());

// TPC-H Query 15 for MongoDB
db = db.getSiblingDB("final");

// variables
var start = new Date(1996, 0, 1);
var end = new Date(1996, 3, 1);

// month is 0-indexed
var subquery = {
	"shipdate": {
		"$gte": start,
		"$lt": end
	}
};

// extracts the total revenue of each supplier
var result = db.deals.aggregate([
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

// print result
printjson(result.toArray());

// add log time
var queryEndedAt = new Date();
print('Query ended at ' + queryEndedAt.toISOString());