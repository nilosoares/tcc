(function() {

    var finalDb = db.getSiblingDB("final");

    finalDb.deals.createIndex(
       {
           "order.orderstatus": 1,
           "partsupp.supplier.nation.name": 1
       },
       {
            name: "q21.deals.orderstatus__nation_name",
            partialFilterExpression: {
                "order.orderstatus": { $eq: "F" },
                "partsupp.supplier.nation.name": { $eq: "__PARAM_COUNTRY__" }
            }
        }
   );

})();