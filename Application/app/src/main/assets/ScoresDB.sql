BEGIN TRANSACTION;
CREATE TABLE `ScoresDB` (
	`Vehicle`	TEXT,
	`Tripdate`	INTEGER,
	`BrakeScore`	INTEGER,
	`GearScore`	INTEGER,
	`FuelScore`	INTEGER,
	PRIMARY KEY(Vehicle)
);
COMMIT;
