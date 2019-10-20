/*

CREATE INDEX p_8
ON lineitem
USING btree
(l_returnflag COLLATE pg_catalog."default")
WHERE l_returnflag = 'A'::bpchar;

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    finalDb.deals.createIndex(
        {
            "l_returnflag": 1
        },
        {
            name: "P8",
            partialFilterExpression: {
                "l_returnflag": { $eq: 'A' }
            }
        }
    );

})();