// add log time
var queryStartedAt = new Date();
print('Query started at ' + queryStartedAt.toISOString());

// TPC-H Query 22 for MongoDB
db = db.getSiblingDB("final");

var countryCodes = ['13', '31', '23', '29', '30', '18', '17'];

// query to match country code
var dealsPhoneMatch = {
    $expr: {
        $in: [
            { $substr: ["$order.customer.phone", 0, 2] },
            countryCodes
        ]
    }
};

var customersPhoneMatch = {
    $expr: {
        $in: [
            { $substr: ["$phone", 0, 2] },
            countryCodes
        ]
    }
};

// Calculates the avg of the acctbal
var sum1 = db.customers.aggregate([
    {
        $match: customersPhoneMatch
    },
    {
        $match: {
            "acctbal": { $gt: 0.00 },
        }
    },
    {
        $group: {
            _id: null,
            counter: { $sum: 1 },
            sumAcctBal: { $sum: "$acctbal" }
        }
    }
]).toArray()[0];

var sum2 = db.deals.aggregate([
    {
        $match: dealsPhoneMatch
    },
    {
        $match: {
            "order.customer.acctbal": { $gt: 0.00 },
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
            _id: null,
            counter: { $sum: 1 },
            sumAcctBal: { $sum: "$bal" }
        }
    }
]).toArray()[0];

var avgAcctBal = (sum1.sumAcctBal + sum2.sumAcctBal) / (sum1.counter + sum2.counter);

// Find the final result
var result = db.customers.aggregate([
    {
        $match: customersPhoneMatch
    },
    {
        $match: {
            "acctbal": { $gt: avgAcctBal }
        }
    },
    {
        $group: {
            _id: {
                $substr: ["$phone", 0, 2]
            },
            numcust: { $sum: 1 },
            totacctbal: { $sum: "$acctbal" }
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

// print result
printjson(result.toArray());

// add log time
var queryEndedAt = new Date();
print('Query ended at ' + queryEndedAt.toISOString());