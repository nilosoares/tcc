// TPC-H Query 22 for MongoDB
db = db.getSiblingDB("final");

// query to match country code
var countryCodes = ['13', '31', '23', '29', '30', '18', '17'];
var phone = {
    $expr: {
        $in: [
            { $substr: ["$order.customer.phone", 0, 2] },
            countryCodes
        ]
    }
};

// group to have distinct customers
var groupCust = {
    _id: "$order.customer.custkey",
    bal: { $first : "$order.customer.acctbal" },
    phone: { $first : "$order.customer.phone" }
};

// calculate the average account balance
var avgAccBalArr = db.deals.aggregate([
    {
        $match: phone
    },
    {
        $match: {
            "order.customer.acctbal" : { $gt : 0 }
        }
    },
    {
        $group: groupCust
    },
    {
        $group : {
            _id: null,
            avg_acctbal: {
                $avg: "$bal"
            }
        }
    }
]).toArray();

var avgAccBal = avgAccBalArr[0].avg_acctbal;

// Find the final result
var result = db.deals.aggregate([
    { $match: phone },
    {
        $match: {
            "order.customer.acctbal": {
                $gt: avgAccBal
            }
        }
    },
    {
        $group: groupCust
    },
    {
        $group: {
            _id: {
                $substr: ["$phone", 0, 2]
            },
            numcust: { $sum: 1 },
            totacctbal: { $sum: "$bal" }
        }
    },
    {
        $sort: {
            "_id" : 1 // _id = cntrycode
        }
    },
    {
        $limit: 1
    }
]);

printjson(result.toArray());