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
            "returnflag": 1
        },
        {
            name: "P_8",
            partialFilterExpression: {
                "returnflag": { $eq: 'A' }
            }
        }
    );

})();