db = db.getSiblingDB("final");

db.setProfilingLevel(0);

db.system.profile.drop();

db.createCollection("system.profile", { capped: true, size: 15 * 1000 * 1000 } ); // 15MB

db.setProfilingLevel(1, { slowms: 100 });
