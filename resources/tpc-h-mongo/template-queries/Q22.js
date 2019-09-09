(function() {

    var finalDb = db.getSiblingDB("final");

    var countryCodes = [
        "__PARAM_COUNTRY_CODE_1__",
        "__PARAM_COUNTRY_CODE_2__",
        "__PARAM_COUNTRY_CODE_3__",
        "__PARAM_COUNTRY_CODE_4__",
        "__PARAM_COUNTRY_CODE_5__",
        "__PARAM_COUNTRY_CODE_6__",
        "__PARAM_COUNTRY_CODE_7__"
    ];

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
    var sum1 = finalDb.customers.aggregate([
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

    var sum2 = finalDb.deals.aggregate([
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
    return finalDb.customers.aggregate([
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

})();