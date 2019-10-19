(function() {

    var finalDb = db.getSiblingDB("final");

    var quantity = __PARAM_QUANTITY__;
    var discount = __PARAM_DISCOUNT__;
    var startDate = new Date(__PARAM_START_DATE__); // month is 0-indexed
    var endDate = new Date(__PARAM_END_DATE__);

    var startDiscount = parseFloat((discount - 0.01).toFixed(2));
    var endDiscount = parseFloat((discount + 0.01).toFixed(2));

    return finalDb.deals.aggregate([
        {
            $match: {
                $and: [
                    { "shipdate": { $gte: startDate } },
                    { "shipdate": { $lt: endDate } },
                    { "discount": { $gte: startDiscount } },
                    { "discount": { $lte: endDiscount } },
                    { "quantity": { $lt: quantity } }
                ]
            }
        },
        {
            $group: {
                "_id": null,
                "count": {
                    $sum: 1
                },
                "revenue": {
                    $sum: {
                        $multiply: ["$extendedprice", "$discount"]
                    }
                }
            }
        },
        {
            $project: {
                "_id": 0,
                "count": 1,
                "revenue": 1
            }
        }
    ]);

})();