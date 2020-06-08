/*

CREATE INDEX p_4_6_10
ON lineitem
USING btree (l_quantity, l_discount, l_shipdate)
WHERE
    l_quantity < 18.2811003923472 AND
    l_discount < 0.023341357999829684 AND
    l_discount > 0.018050010074103496 AND
    l_shipdate < '1997-08-21'::date AND
    l_shipdate > '1997-08-12'::date
    OR
    l_quantity < 21.666989134568418 AND
    l_discount < 0.004121353997759745 AND
    l_discount > 0.0038651513156768855 AND
    l_shipdate < '1997-11-07'::date AND
    l_shipdate > '1997-07-19'::date
    OR
    l_quantity < 21.826561729161746 AND
    l_discount < 0.02689858944060175 AND
    l_discount > 0.0038651513156768855 AND
    l_shipdate > '1997-02-11'::date
    OR
    l_quantity < 21.826561729161746 AND
    l_discount < 0.02689858944060175 AND
    l_discount > 0.007071355868115796 AND
    l_shipdate < '1997-12-21'::date AND
    l_shipdate > '1997-02-11'::date
    OR
    l_quantity < 15.702815436753523 AND
    l_discount < 0.0245999444624751 AND
    l_discount > 0.01676940262235773 AND
    l_shipdate < '1997-12-10'::date AND
    l_shipdate > '1997-10-27'::date
    OR
    l_quantity < 21.826561729161746 AND
    l_discount < 0.02689858944060175 AND
    l_discount > 0.012656307650684718 AND
    l_shipdate < '1997-12-21'::date AND
    l_shipdate > '1997-02-11'::date
    OR
    l_quantity < 21.051974780381435 AND
    l_discount < 0.011978658096637933 AND
    l_discount > 0.008760678573503338 AND
    l_shipdate < '1997-09-02'::date AND
    l_shipdate > '1997-02-11'::date
    OR
    l_quantity < 21.826561729161746 AND
    l_discount < 0.02834339277229088 AND
    l_discount > 0.0038651513156768855 AND
    l_shipdate > '1997-02-11'::date
    OR
    l_quantity < 21.035837899903893 AND
    l_discount < 0.02834339277229088 AND
    l_discount > 0.024092944232228637 AND
    l_shipdate < '1997-11-19'::date AND
    l_shipdate > '1997-03-12'::date
    OR
    l_quantity < 9.020350886249453 AND
    l_discount < 0.017931561233652735 AND
    l_discount > 0.007939723121103179 AND
    l_shipdate < '1997-11-04'::date AND
    l_shipdate > '1997-10-26'::date
    OR
    l_quantity < 21.88697710685508 AND
    l_discount < 0.02840772987973926 AND
    l_discount > 0.0038651513156768855 AND
    l_shipdate > '1997-01-18'::date
    OR
    l_quantity < 9.772429999364398 AND
    l_discount < 0.02636983897058536 AND
    l_discount > 0.013967884488129835 AND
    l_shipdate < '1997-11-01'::date AND
    l_shipdate > '1997-10-08'::date
    OR
    l_quantity < 21.88697710685508 AND
    l_discount < 0.02840772987973926 AND
    l_discount > 0.0016128480368708886 AND
    l_shipdate > '1997-01-18'::date
    OR
    l_quantity < 18.26829224583729 AND
    l_discount < 0.0032273764692443366 AND
    l_discount > 0.0016128480368708886 AND
    l_shipdate < '1997-11-30'::date AND
    l_shipdate > '1997-03-05'::date
    OR
    l_quantity < 11.342758059655893 AND
    l_discount < 0.02134765826100224 AND
    l_discount > 0.018397866843956345 AND
    l_shipdate < '1997-08-13'::date AND
    l_shipdate > '1997-05-08'::date
    OR
    l_quantity < 21.70198717123795 AND
    l_discount < 0.021197833591757434 AND
    l_discount > 0.013101787594299355 AND
    l_shipdate < '1997-11-13'::date AND
    l_shipdate > '1997-05-17'::date
    OR
    l_quantity < 21.750792226504927 AND
    l_discount < 0.02054023061522453 AND
    l_discount > 0.01766455125443555 AND
    l_shipdate < '1997-08-17'::date AND
    l_shipdate > '1997-02-27'::date
    OR
    l_quantity < 21.826561729161746 AND
    l_discount < 0.02840772987973926 AND
    l_discount > 0.0038651513156768855 AND
    l_shipdate > '1997-02-11'::date
    OR
    l_quantity < 8.006048062149967 AND
    l_discount < 0.02840772987973926 AND
    l_discount > 0.00611192626

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    var indexes = [
        {
            name: "P_4_6_10_5",
            keys: {
                "quantity": 1,
                "discount": 1,
                "shipdate": 1,
                "_partial_index_5": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lt: 15.702815436753523 } },
                    { "discount": { $lt: 0.0245999444624751 } },
                    { "discount": { $gt: 0.01676940262235773 } },
                    { "shipdate": { $lt: new Date('1997-12-10') } },
                    { "shipdate": { $gt: new Date('1997-10-27') } }
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