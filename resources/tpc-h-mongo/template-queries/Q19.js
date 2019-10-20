(function() {

    var finalDb = db.getSiblingDB("final");

    var quantity1 = __PARAM_QUANTITY_1__;
    var quantity2 = __PARAM_QUANTITY_2__;
    var quantity3 = __PARAM_QUANTITY_3__;
    var brand1 = "__PARAM_BRAND_1__";
    var brand2 = "__PARAM_BRAND_2__";
    var brand3 = "__PARAM_BRAND_3__";

    return finalDb.deals.__PARAM_MONGO_METHOD__([
        {
            $match: {
                $or: [
                    {
                        $and: [
                            { "partsupp.part.brand": { $eq: brand1 } },
                            { "partsupp.part.container": { $in: ['SM CASE', 'SM BOX', 'SM PACK', 'SM PKG'] } },
                            { "quantity": { $gte: quantity1 } },
                            { "quantity": { $lte: quantity1 + 10 } },
                            { "partsupp.part.size": { $gte: 1 } },
                            { "partsupp.part.size": { $lte: 5 } },
                            { "shipmode": { $in: ['AIR', 'AIR REG'] } },
                            { "shipinstruct": { $eq: 'DELIVER IN PERSON' } }
                        ]
                    },
                    {
                        $and: [
                            { "partsupp.part.brand": { $eq: brand2 } },
                            { "partsupp.part.container": { $in: ['MED BAG', 'MED BOX', 'MED PKG', 'MED PACK'] } },
                            { "quantity": { $gte: quantity2 } },
                            { "quantity": { $lte: quantity2 + 10 } },
                            { "partsupp.part.size": { $gte: 1 } },
                            { "partsupp.part.size": { $lte: 10 } },
                            { "shipmode": { $in: ['AIR', 'AIR REG'] } },
                            { "shipinstruct": { $eq: 'DELIVER IN PERSON' } }
                        ]
                    },
                    {
                        $and: [
                            { "partsupp.part.brand": { $eq: brand3 } },
                            { "partsupp.part.container": { $in: ['LG CASE', 'LG BOX', 'LG PACK', 'LG PKG'] } },
                            { "quantity": { $gte: quantity3 } },
                            { "quantity": { $lte: quantity3 + 10 } },
                            { "partsupp.part.size": { $gte: 1 } },
                            { "partsupp.part.size": { $lte: 15 } },
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
    ]);

})();