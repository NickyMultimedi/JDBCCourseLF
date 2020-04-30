CREATE TABLE if not exists Brewers (Id int IDENTITY not null, Name varchar(50) default NULL, Address varchar(50) default NULL, ZipCode varchar(6) default NULL,City varchar(50) default NULL, Turnover int default 0, PRIMARY KEY (Id));
CREATE TABLE Categories (Id int IDENTITY not null, Category varchar(50) default NULL,PRIMARY KEY  (Id));
CREATE TABLE Beers (Id int IDENTITY not null, Name varchar(100) DEFAULT NULL, BrewerId int DEFAULT NULL, CategoryId int DEFAULT NULL,Price float DEFAULT 0, Stock int DEFAULT 0, Alcohol float DEFAULT 0, Version int DEFAULT 0, Image blob, PRIMARY KEY (Id));
