/*

CREATE INDEX p_10
ON lineitem USING btree (l_shipdate)
WHERE
    l_shipdate < '1997-11-07'::date AND l_shipdate > '1997-07-19'::date
    OR
    l_shipdate < '1997-12-28'::date AND l_shipdate > '1997-12-12'::date
    OR
    l_shipdate < '1997-12-21'::date AND l_shipdate > '1997-02-11'::date
    OR
    l_shipdate > '1997-12-18'::date
    OR
    l_shipdate < '1997-09-19'::date AND l_shipdate > '1997-03-01'::date
    OR
    l_shipdate < '1997-12-10'::date AND l_shipdate > '1997-10-27'::date
    OR
    l_shipdate < '1997-09-20'::date AND l_shipdate > '1997-05-05'::date
    OR
    l_shipdate < '1997-11-14'::date AND l_shipdate > '1997-06-23'::date
    OR
    l_shipdate > '1997-07-28'::date
    OR
    l_shipdate > '1997-10-02'::date
    OR
    l_shipdate > '1997-10-07'::date
    OR
    l_shipdate < '1997-11-21'::date AND l_shipdate > '1997-09-18'::date
    OR
    l_shipdate < '1997-11-13'::date AND l_shipdate > '1997-09-22'::date
    OR
    l_shipdate < '1997-08-17'::date AND l_shipdate > '1997-02-27'::date
    OR
    l_shipdate < '1997-09-02'::date AND l_shipdate > '1997-02-11'::date
    OR
    l_shipdate < '1997-11-19'::date AND l_shipdate > '1997-03-12'::date
    OR
    l_shipdate > '1997-12-29'::date
    OR
    l_shipdate > '1997-05-11'::date
    OR
    l_shipdate < '1997-11-04'::date AND l_shipdate > '1997-10-26'::date
    OR
    l_shipdate < '1997-11-28'::date AND l_shipdate > '1997-03-14'::date
    OR
    l_shipdate < '1997-12-29'::date AND l_shipdate > '1997-11-06'::date
    OR
    l_shipdate < '1997-12-30'::date AND l_shipdate > '1997-08-12'::date
    OR
    l_shipdate > '1997-09-08'::date
    OR
    l_shipdate > '1997-03-24'::date
    OR
    l_shipdate < '1997-12-15'::date AND l_shipdate > '1997-06-11'::date
    OR
    l_shipdate < '1997-09-18'::date AND l_shipdate > '1997-01-18'::date
    OR
    l_shipdate < '1997-03-10'::date AND l_shipdate > '1997-02-04'::date
    OR
    l_shipdate < '1997-12-15'::date AND l_shipdate > '1997-09-21'::date
    OR
    l_shipdate > '1997-11-03'::date
    OR
    l_shipdate < '1997-12-15'::date AND l_shipdate > '1997-06-22'::date
    OR
    l_shipdate > '1997-11-22'::date
    OR
    l_shipdate < '1997-07-05'::date AND l_shipdate > '1997-05-12'::date
    OR
    l_shipdate < '1997-11-30'::date AND l_shipdate > '1997-03-05'::date
    OR
    l_shipdate > '1997-10-12'::date
    OR
    l_shipdate > '1997-11-08'::date
    OR
    l_shipdate < '1997-12-24'::date AND l_shipdate > '1997-12-15'::date
    OR
    l_shipdate < '1997-08-13'::date AND l_shipdate > '1997-05-08'::date
    OR
    l_shipdate < '1997-10-03'::date AND l_shipdate > '1997-03-30'::date
    OR
    l_shipdate < '1997-11-13'::date AND l_shipdate > '1997-05-17'::date;

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    var indexes = [
        {
            name: "P_10_7",
            keys: {
                "shipdate": 1,
                "_partial_index_7": 1
            },
            expression: {
                $and: [
                    { "shipdate": { $lt: new Date('1997-9-20') } },
                    { "shipdate": { $gt: new Date('1997-5-5') } }
                ]
            }
        }
    ];

    var nbIndexes = indexes.length;

    for (var i = 0; i < nbIndexes; i++) {
        finalDb.deals.createIndex(
            indexes[i].keys,
            {
                name: indexes[i].name,
                partialFilterExpression: indexes[i].expression
            }
        );
    }

})();