// TPC-H Query 20 for MongoDB
db = db.getSiblingDB("final");

var query_part = {
	"shipdate": {
		"$gte": new Date(1994, 0, 1),
		"$lt": new Date(1995, 0, 1)
	},
	"partsupp.supplier.nation.name": {
		$regex: '^CANADA' // use regex because of whitespaces in the end
	}, 
	"partsupp.part.name": {
		$regex: '^forest',
		$options: 'i' // option i makes case insensitive
	} 
};

var red = function(doc, out) {
	out.sum += doc.quantity;
}

// calculate the the total quantity first
var half_total_quantity = db.deals.group({
	key : "sum_quantity",
	cond : query_part,
	initial : {
		sum : 0
	},
	reduce : red
})[0].sum / 2;

db.deals.find(
	{
		"$and": [
			query_part,
			{
				"partsupp.availqty": {
					"$gt": half_total_quantity
				}
			}
		]
	},
	{
		"_id": 0,
		"partsupp.supplier.name": 1,
		"partsupp.supplier.address": 1
	}
).sort({ "partsupp.supplier.name": 1 });