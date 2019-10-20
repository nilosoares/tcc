/*

CREATE INDEX c_12_14
ON lineitem
USING btree
(l_shipmode COLLATE pg_catalog."default", l_receiptdate);

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    finalDb.deals.createIndex(
        {
            "shipmode": 1,
            "receiptdate": 1
        },
         {
             name: "C1214"
         }
    );

})();