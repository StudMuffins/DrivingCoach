BEGIN TRANSACTION;
CREATE TABLE `ScoresDB` (
	`Vehicle`	TEXT,
	`Tripdate`	INTEGER,
	`Brakes Score`	INTEGER,
	`Gear Score`	INTEGER,
	`Fuel Score`	INTEGER,
	PRIMARY KEY(Vehicle)
);
COMMIT;
