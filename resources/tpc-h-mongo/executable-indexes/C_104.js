/*

CREATE INDEX c_104
ON orders
USING btree
(o_orderdate);

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    finalDb.deals.createIndex(
        {
            "order.orderdate": 1
        },
         {
             name: "C104"
         }
    );

})();