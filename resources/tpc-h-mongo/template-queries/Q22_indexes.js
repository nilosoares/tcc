(function() {

    var finalDb = db.getSiblingDB("final");

    // variables
    var countryCodes = [
        "__PARAM_COUNTRY_CODE_1__",
        "__PARAM_COUNTRY_CODE_2__",
        "__PARAM_COUNTRY_CODE_3__",
        "__PARAM_COUNTRY_CODE_4__",
        "__PARAM_COUNTRY_CODE_5__",
        "__PARAM_COUNTRY_CODE_6__",
        "__PARAM_COUNTRY_CODE_7__"
    ];

    // sum1 index
    finalDb.customers.createIndex(
        {
            "order.customer.phone": 1
        },
        {
            name: "q22.sum1.acctbal",
            partialFilterExpression: {
                "acctbal": {
                    $gt: 0.00
                }
            }
        }
    );

    // sum2 index
    finalDb.deals.createIndex(
    	{
    		"order.customer.phone": 1
    	},
    	{
    		name: "q22.sum2.acctbal",
    		partialFilterExpression: {
    			"order.customer.acctbal": {
    				$gt: 0.00
    			}
    		}
    	}
    );

})();