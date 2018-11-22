// TPC-H Query 21 for MongoDB
db = db.getSiblingDB("final");

var query = {
	$and: [
		{ "order.orderstatus": "F"},
		{ "partsupp.supplier.nation.name": { $regex : '^SAUDI ARABIA' }},
		{ $expr: "this.receiptdate > this.commitdate"}
	]
};

// check if there are other suppliers with same order
var multisupp = {
	$expr : function() {
		return db.deals.findOne({
			$and: [
				{
					"order.orderkey": this.order.orderkey
				},
				{
					"partsupp.supplier.suppkey": {
						"$ne": this.partsupp.supplier.suppkey
					}
				}
			]
		}) != null;
	}
};

// make sure that no other supplier failed
var onlyfail = {
	$expr : function() {
		return db.deals.findOne({
			$and: [
				{
					"order.orderkey": this.order.orderkey
				},
				{
					"partsupp.supplier.suppkey": {
						"$ne": this.partsupp.supplier.suppkey
					}
				},
				{
					$expr: "this.receiptdate > this.commitdate"
				}
			]
		}) == null;
	}
};

res = db.deals.aggregate([
    {
        $match : query
    },
    {
        $match : multisupp
    },
    {
        $match : onlyfail
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
    }
]);
