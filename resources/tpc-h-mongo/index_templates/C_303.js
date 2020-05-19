/*

CREATE INDEX c_303
ON part
USING btree
(p_brand COLLATE pg_catalog."default");

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    finalDb.deals.createIndex(
        {
            "partsupp.part.brand": 1
        },
        {
            name: "C_303"
        }
    );

})();