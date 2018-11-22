// TPC-H Query 1 for MongoDB using the native group-statement
db = db.getSiblingDB("final");

// random date
function rand(min, max) {
    var interval = max - min + 1;
    return Math.floor((Math.random() * interval)) + min;
}
var delta = rand(60, 120);
var date = new Date(1998, 11, 1); // month is 0-indexed
date.setDate(date.getDate() - delta);

// reduce function
var red = function(doc, out) {
    out.count_order++;
    out.sum_qty += doc.quantity;
    out.sum_base_price += doc.extendedprice;
    out.sum_disc_price += doc.extendedprice * (1 - doc.discount);
    out.sum_charge += doc.extendedprice * (1 - doc.discount) * (1 + doc.tax);
    out.avg_disc += doc.discount; // sum the discount first
};

// finalize function
var avg = function(out) {
    out.avg_qty = out.sum_qty / out.count_order;
    out.avg_price = out.sum_base_price / out.count_order;
    out.avg_disc = out.avg_disc / out.count_order; // calculate the average of the discount
};

// run query
db.deals.group({
    key : { returnflag : true, linestatus : true },
    cond : { "shipdate" : { $lte: date }},
    initial: { count_order : 0, sum_qty : 0, sum_base_price : 0, sum_disc_price : 0, sum_charge : 0, avg_disc : 0 },
    reduce : red,
    finalize : avg
});