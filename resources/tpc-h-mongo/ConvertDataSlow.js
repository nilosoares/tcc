dbSource = db.getSiblingDB("1to1");
dbDest = db.getSiblingDB("final");
dbDest.deals.drop();

dbSource.lineitem.find().batchSize(1000).forEach(
    function(lineitem) {
        {
            // replace order
            var order = dbSource.orders.findOne( {"orderkey" : lineitem.orderkey} );
            lineitem.order = order;
            delete(lineitem.orderkey);

            {
                // replace customer
                var customer = dbSource.customer.findOne( {"custkey" : order.custkey} );
                lineitem.order.customer = customer;
                delete(lineitem.order.custkey);
                delete(lineitem.order._id);

                {
                    // replace nation
                    var nation = dbSource.nation.findOne( {"nationkey" : customer.nationkey} );
                    lineitem.order.customer.nation = nation;
                    delete(lineitem.order.customer.nationkey);
                    delete(lineitem.order.customer._id);

                    {
                        // replace region
                        var region = dbSource.region.findOne( {"regionkey" : nation.regionkey} );
                        lineitem.order.customer.nation.region = region;
                        delete(lineitem.order.customer.nation.regionkey);
                        delete(lineitem.order.customer.nation._id);
                        delete(lineitem.order.customer.nation.region._id);
                    }
                }
            }
        }

        {

            // replace partsupp
            var partsupp = dbSource.partsupp.findOne( {"partkey" : lineitem.partkey, "suppkey" : lineitem.suppkey} );
            lineitem.partsupp = partsupp;
            delete(lineitem.partkey);
            delete(lineitem.suppkey);
            delete(lineitem.partsupp._id);

            {
                // replace part
                var part = dbSource.part.findOne( {"partkey" : partsupp.partkey} );
                lineitem.partsupp.part = part;
                delete(lineitem.partsupp.partkey);
                delete(lineitem.partsupp.part._id);
            }

            {
                // replace supplier
                var supplier = dbSource.supplier.findOne( {"suppkey" : partsupp.suppkey} );
                lineitem.partsupp.supplier = supplier;
                delete(lineitem.partsupp.suppkey);
                delete(lineitem.partsupp.supplier._id);

                {
                    // replace nation
                    var nation = dbSource.nation.findOne( {"nationkey" : supplier.nationkey} );
                    lineitem.partsupp.supplier.nation = nation;
                    delete(lineitem.partsupp.supplier.nationkey);
                    delete(lineitem.partsupp.supplier._id);

                    {
                        // replace region
                        var region = dbSource.region.findOne( {"regionkey" : nation.regionkey} );
                        lineitem.partsupp.supplier.nation.region = region;
                        delete(lineitem.partsupp.supplier.nation.regionkey);
                        delete(lineitem.partsupp.supplier.nation._id);
                        delete(lineitem.partsupp.supplier.nation.region._id);
                    }
                }
            }

        }

        dbDest.deals.insert(lineitem);
    }
);