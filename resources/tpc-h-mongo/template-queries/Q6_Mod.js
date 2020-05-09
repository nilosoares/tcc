(function() {

    var finalDb = db.getSiblingDB("final");

    var quantity = __PARAM_QUANTITY__;
    var discount = __PARAM_DISCOUNT__;
    var startDate = new Date(__PARAM_START_DATE__); // month is 0-indexed
    var endDate = new Date(__PARAM_END_DATE__);

    var startDiscount = parseFloat((discount - 0.01).toFixed(4));
    var endDiscount = parseFloat((discount + 0.01).toFixed(4));

    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                $and: [
                    { "shipdate": { $gt: startDate } },
                    { "shipdate": { $lt: endDate } },
                    { "discount": { $gt: startDiscount } },
                    { "discount": { $lt: endDiscount } },
                    { "quantity": { $lt: quantity } }
                ]
            }
        },
        {
            $group: {
                "_id": null,
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
                "revenue": 1
            }
        }
    ], { allowDiskUse: true });

})();