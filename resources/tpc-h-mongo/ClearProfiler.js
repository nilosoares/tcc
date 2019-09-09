db = db.getSiblingDB("final");

db.setProfilingLevel(0);

db.system.profile.drop();

db.createCollection("system.profile", { capped: true, size: 10 * 1000 * 1000 } ); // 100MB

db.setProfilingLevel(2);
