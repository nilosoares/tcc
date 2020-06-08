(function() {

    var finalDb = db.getSiblingDB("final");

    var quantity1 = __PARAM_QUANTITY_1__;
    var quantity2 = __PARAM_QUANTITY_2__;
    var brand1 = "__PARAM_BRAND_1__";
    var brand2 = "__PARAM_BRAND_2__";

    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                $or: [
                    {
                        $and: [
                            { "partsupp.part.brand": { $eq: brand1 } },
                            { "partsupp.part.container": { $in: ['SM CASE', 'SM BOX', 'SM PACK', 'SM PKG'] } },
                            { "quantity": { $gt: quantity1 } },
                            { "quantity": { $lt: quantity1 + 10 } },
                            { "shipmode": { $in: ['AIR', 'AIR REG'] } },
                            { "shipinstruct": { $eq: 'DELIVER IN PERSON' } }
                        ]
                    },
                    {
                        $and: [
                            { "partsupp.part.brand": { $eq: brand2 } },
                            { "partsupp.part.container": { $in: ['MED BAG', 'MED BOX', 'MED PKG', 'MED PACK'] } },
                            { "quantity": { $gt: quantity2 } },
                            { "quantity": { $lt: quantity2 + 10 } },
                            { "shipmode": { $in: ['AIR', 'AIR REG'] } },
                            { "shipinstruct": { $eq: 'DELIVER IN PERSON' } }
                        ]
                    }
                ]
            }
        },
        {
            $group: {
                "_id": null,
                "revenue": {
                    $sum: {
                        $multiply: [
                            "$extendedprice",
                            { $subtract: [1, "$discount"] }
                        ]
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