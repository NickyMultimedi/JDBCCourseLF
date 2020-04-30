truncate table Brewers;
truncate table Categories;
truncate table Beers;

INSERT INTO Brewers VALUES (1,'TestBrewer','Test Street 911',1111,'Test-County',10000);
INSERT INTO Categories VALUES (1,'TestCategory');
INSERT INTO Beers VALUES (1,'TestBeer',1,1,2.75,100,7,0,NULL);
