/*

CREATE INDEX c_10
ON lineitem
USING btree
(l_shipdate);

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    finalDb.deals.createIndex(
        {
            "l_shipdate": 1
        },
         {
             name: "C10"
         }
    );

})();