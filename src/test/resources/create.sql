delete from tag_offer;
delete from offer;
delete from tag;
delete from category;

INSERT INTO category ("id", "name") VALUES
(1,	'cat1'),
(2,	'cat2'),
(3,	'cat3'),
(4,	'cat4'),
(5,	'cat5');

INSERT INTO tag ("id", "name") VALUES
(1,	'tag1'),
(2,	'tag2'),
(3,	'tag3'),
(4,	'tag4'),
(5,	'tag5');

INSERT INTO offer ("id", "name", "category", "price", "description") VALUES
(1,	'TestOffer1',	1,	111.11, 'TestDescription1'),
(2,	'TestOffer2',	2,	222.22, 'TestDescription2'),
(3,	'TestOffer3',	3,	333.33, 'TestDescription3');

INSERT INTO tag_offer ("tag_id", "offer_id") VALUES
(1,	1),
(2,	1),
(3,	1),
(2,	2),
(3,	2),
(3,	3),
(4,	3),
(5,	3);





