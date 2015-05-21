BEGIN TRANSACTION;
CREATE TABLE `CarsDB` (
	`carName`	TEXT UNIQUE,
	`transmission`	TEXT,
	`fuelType`  TEXT,
	PRIMARY KEY(carName)
);
COMMIT;
