-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner2','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner3','0wn3r',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (5,'owner3','clinicOwner');
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
INSERT INTO authorities(id,username,authority) VALUES (4,'owner2','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

-- One Clinic owner
INSERT INTO clinic_owner VALUES(1,'Juan','Carlos','owner3');

-- One Clinic
INSERT INTO clinic VALUES (1,'hola',1,4,1);
INSERT INTO clinic VALUES (2,'Otra',1,4,1);

-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,clinic_id) VALUES ('admin1','4dm1n',TRUE,1);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO vets(id, first_name,last_name,clinic_id) VALUES (1, 'James', 'Carter',1);
INSERT INTO vets(id, first_name,last_name,clinic_id) VALUES (2, 'Helen', 'Leary',2);
INSERT INTO vets(id, first_name,last_name,clinic_id) VALUES (3, 'Linda', 'Douglas',2);
INSERT INTO vets(id, first_name,last_name,clinic_id) VALUES (4, 'Rafael', 'Ortega',2);
INSERT INTO vets(id, first_name,last_name,clinic_id) VALUES (5, 'Henry', 'Stevens',2);
INSERT INTO vets(id, first_name,last_name,clinic_id) VALUES (6, 'Sharon', 'Jenkins',1);

INSERT INTO specialties VALUES (1, 'radiologia');
INSERT INTO specialties VALUES (2, 'cirugia');
INSERT INTO specialties VALUES (3, 'dentista');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'gato');
INSERT INTO types VALUES (2, 'perro');
INSERT INTO types VALUES (3, 'lagarto');
INSERT INTO types VALUES (4, 'serpiente');
INSERT INTO types VALUES (5, 'pájaro');
INSERT INTO types VALUES (6, 'hamster');


INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023',1,'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Rusia', '6085551749',2,'owner2');
-- INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
-- INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
-- INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
-- INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
-- INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
-- INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
-- INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
-- INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4,2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1,2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Gonzalo', '2011-08-06', 3, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 3, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 5, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 1);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 2, '2013-01-01', 'Vacuna para la rabia');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 3, '2013-01-02', 'Vacuna para la rabia');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 4, '2013-01-03', 'Castración');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 5, '2013-01-04', 'Castración');