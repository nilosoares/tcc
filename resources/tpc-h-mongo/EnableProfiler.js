db = db.getSiblingDB("final");

db.setProfilingLevel(1, { slowms: 100 });
