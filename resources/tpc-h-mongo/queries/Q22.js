// TPC-H Query 22 for MongoDB
db = db.getSiblingDB("final");

// query to match country code
var phone = {
	$or : [
		{ "order.customer.phone" : { $regex : '^13' } },
		{ "order.customer.phone" : { $regex : '^31' } },
		{ "order.customer.phone" : { $regex : '^23' } },
		{ "order.customer.phone" : { $regex : '^29' } },
		{ "order.customer.phone" : { $regex : '^30' } },
		{ "order.customer.phone" : { $regex : '^18' } },
		{ "order.customer.phone" : { $regex : '^17' } }
	]
};

// calculate the average account balance
var result = db.deals.aggregate([
  {
      $match: phone
  },
  {
      $match: {
          "order.customer.acctbal" : { "$gt" : 0 }
      }
  },
  {
      $group : {
          _id: "avg_acc",
          avg: {
              $avg: "$order.customer.acctbal"
          }
      }
  },
]).toArray();

var avgAccBal = result[0].avg;

db.deals.aggregate([
    { $match: phone },
    {
        $match: {
            "order.customer.acctbal": {
                "$gt": avgAccBal
            }
        }
    },
    {
        $group: { // group to have distinct customers
            _id: "$order.customer.custkey",
            bal: { $first : "$order.customer.acctbal" },
            phone: { $first : "$order.customer.phone" }
        }
    },
    {
        $group: {
            _id: {
                "$substr": ["$phone", 0, 2]
            },
            numcust: { "$sum": 1 },
            totacctbal: { "$sum": "$bal"}
        }
    },
    {
        $sort: {
            "_id" : 1
        }
    } // _id = cntrycode
]);
