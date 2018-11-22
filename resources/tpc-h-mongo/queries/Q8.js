// TPC-H Query 8 for MongoDB
db = db.getSiblingDB("final");

// subquery
var start = new Date(1995, 0, 1); // month is 0-indexed
var end = new Date(1996, 11, 31);

var subquery = {
	$and: [
		{
			"order.customer.nation.region.name": { $regex : '^AMERICA' }
		},
		{
			"partsupp.part.type": "ECONOMY ANODIZED STEEL"
		},
		{
			"order.orderdate": {
				"$gte": start,
				"$lt": end
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
db.tmp.insert(volume_each_nation.toArray());

// process result of subquery (stored in the database)
var red = function(doc, out) {
	out.o_year = doc.o_year;
	out.total_sum += doc.volume; // helper field to calculate market share
	if (doc.nation == "BRAZIL") { // sum mkt_share of the country
		out.mkt_share += doc.volume;
	}
};

var share = function(out) {
	out.mkt_share = out.mkt_share / out.total_sum
	delete out.total_sum; // remove the total_sum field
};

db.tmp.group({
	key: {
		o_year: true
	},
	initial: {
		total_sum: 0,
		mkt_share: 0
	},
	reduce: red,
	finalize: share
});