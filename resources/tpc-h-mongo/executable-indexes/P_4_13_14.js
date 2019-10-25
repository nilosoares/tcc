/*

CREATE INDEX p_4_13_14
ON lineitem
USING btree
(l_quantity, l_shipinstruct COLLATE pg_catalog."default", l_shipmode COLLATE
pg_catalog."default")
WHERE
    l_quantity < 13.144078204270654 AND l_quantity > 3.8326392129350366 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 13.144078204270654 AND l_quantity > 3.8326392129350366 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar OR
    l_quantity < 18.83615584074578 AND l_quantity > 4.217496800557237 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 18.83615584074578 AND l_quantity > 4.217496800557237 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar OR
    l_quantity < 18.900223499571077 AND l_quantity > 7.729150561354452 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 18.900223499571077 AND l_quantity > 7.729150561354452 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar OR
    l_quantity < 20.363782209224638 AND l_quantity > 8.592161900463697 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 20.363782209224638 AND l_quantity > 8.592161900463697 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar OR
    l_quantity < 21.612358918648976 AND l_quantity > 5.205766389851035 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 21.612358918648976 AND l_quantity > 5.205766389851035 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar OR
    l_quantity < 21.88697710685508 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 21.88697710685508 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar OR
    l_quantity < 21.88697710685508 AND l_quantity > 9.512009501171956 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 21.88697710685508 AND l_quantity > 9.512009501171956 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar OR
    l_quantity < 6.537656193382689 AND l_quantity > 2.6506868182323378 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR'::bpchar OR
    l_quantity < 6.537656193382689 AND l_quantity > 2.6506868182323378 AND l_shipinstruct = 'DELIVER IN PERSON'::bpchar AND l_shipmode = 'AIR REG'::bpchar;

*/
(function() {

    var finalDb = db.getSiblingDB("final");

    var indexes = [
        {
            name: "P_4_13_14_1",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_1": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 13.144078204270654 } },
                    { "quantity": { $gte: 3.8326392129350366 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_2",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_2": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 13.144078204270654 } },
                    { "quantity": { $gte: 3.8326392129350366 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
                ]
            }
        },
        {
            name: "P_4_13_14_3",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_3": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 18.83615584074578 } },
                    { "quantity": { $gte: 4.217496800557237 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_4",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_4": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 18.83615584074578 } },
                    { "quantity": { $gte: 4.217496800557237 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
                ]
            }
        },
        {
            name: "P_4_13_14_5",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_5": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 18.900223499571077 } },
                    { "quantity": { $gte: 7.729150561354452 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_6",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_6": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 18.900223499571077 } },
                    { "quantity": { $gte: 7.729150561354452 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
                ]
            }
        },
        {
            name: "P_4_13_14_7",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_7": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 20.363782209224638 } },
                    { "quantity": { $gte: 8.592161900463697 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_8",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_8": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 20.363782209224638 } },
                    { "quantity": { $gte: 8.592161900463697 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
                ]
            }
        },
        {
            name: "P_4_13_14_9",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_9": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 21.612358918648976 } },
                    { "quantity": { $gte: 5.205766389851035 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_10",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_10": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 21.612358918648976 } },
                    { "quantity": { $gte: 5.205766389851035 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
                ]
            }
        },
        {
            name: "P_4_13_14_11",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_11": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 21.88697710685508 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_12",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_12": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 21.88697710685508 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
                ]
            }
        },
        {
            name: "P_4_13_14_13",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_13": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 21.88697710685508 } },
                    { "quantity": { $gte: 9.512009501171956 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_14",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_14": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 21.88697710685508 } },
                    { "quantity": { $gte: 9.512009501171956 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
                ]
            }
        },
        {
            name: "P_4_13_14_15",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_15": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 6.537656193382689 } },
                    { "quantity": { $gte: 2.6506868182323378 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR' } }
                ]
            }
        },
        {
            name: "P_4_13_14_16",
            keys: {
                "quantity": 1,
                "shipinstruct": 1,
                "shipmode": 1,
                "_partial_index_16": 1
            },
            expression: {
                $and: [
                    { "quantity": { $lte: 6.537656193382689 } },
                    { "quantity": { $gte: 2.6506868182323378 } },
                    { "shipinstruct": { $eq: 'DELIVER IN PERSON' } },
                    { "shipmode": { $eq: 'AIR REG' } }
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


