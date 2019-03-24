db = db.getSiblingDB("final");

var currentProfilingLevel = db.getProfilingStatus().was;

db.setProfilingLevel(0);

db.system.profile.drop();

db.createCollection("system.profile", { capped: true, size: 15 * 10000000 } ); // 15MB

db.setProfilingLevel(currentProfilingLevel);
