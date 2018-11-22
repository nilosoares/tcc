// TPC-H Query 15 for MongoDB
db = db.getSiblingDB("final");

// month is 0-indexed
var subquery = {
	"shipdate": {
		"$gte": new Date(1996, 0, 1),
		"$lt": new Date(1996, 3, 1)
	}
};

// extracts the total revenue of each supplier
var eachsupp = db.deals.aggregate([
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
    }
]);

// store the result in the database
db.tmp.insert(eachsupp.toArray());

// find the top supplier
db.tmp.find().sort({total_revenue : -1}).limit(1);