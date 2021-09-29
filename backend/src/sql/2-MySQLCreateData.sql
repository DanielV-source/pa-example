-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "pa-project" database.
-------------------------------------------------------------------------------
INSERT INTO Province (name) VALUES ('A Coruña');
INSERT INTO Province (name) VALUES ('Pontevedra');

INSERT INTO SportEventType (name) VALUES ('Running');
INSERT INTO SportEventType (name) VALUES ('Ciclismo');

INSERT INTO SportEvent (name, description, date, price, maxParticipants, numParticipants, location, typeId, provinceId,
    numberOfRates, valueOfRates, version) VALUES ('Prueba 1', 'Carrera en A Coruña',
    '2020-07-01 012:00:00.000000000', '10', '500', '20', 'Oleiros', '1', '1',
     '0', '0', '0');

INSERT INTO SportEvent (name, description, date, price, maxParticipants, numParticipants, location, typeId, provinceId,
    numberOfRates, valueOfRates, version) VALUES ('Prueba 2', 'Carrera en A Coruña',
    '2022-01-01 012:00:00.000000000', '10', '100', '20', 'Oleiros', '2', '1',
     '0', '0', '0');

INSERT INTO SportEvent (name, description, date, price, maxParticipants, numParticipants, location, typeId, provinceId,
    numberOfRates, valueOfRates, version) VALUES ('Prueba 3', 'Carrera en A Coruña',
    '2022-02-1 012:00:00.000000000', '10', '300', '0', 'Oleiros', '1', '2',
     '0', '0', '0');

INSERT INTO SportEvent (name, description, date, price, maxParticipants, numParticipants, location, typeId, provinceId,
    numberOfRates, valueOfRates, version) VALUES ('Prueba 4', 'Carrera en A Coruña',
    '2022-03-01 012:00:00.000000000', '10', '2', '0', 'Oleiros', '2', '2',
     '0', '0', '0');

INSERT INTO User (userName, password, firstName, lastname, email, role) VALUES (
    'competitor1', '$2a$10$uicNGMO41KcyofyMPaCCwuQ89o1WM/wn5f9TGgCuFjZrfDLnECPyi', 'Pablo', 'García',
        'pablo.garcia.saleta@udc.es', '0');

INSERT INTO User (userName, password, firstName, lastname, email, role) VALUES (
    'competitor2', '$2a$10$uicNGMO41KcyofyMPaCCwuQ89o1WM/wn5f9TGgCuFjZrfDLnECPyi', 'Joaquín', 'Baltasar',
        'joaquin.paradelo.perez@udc.es', '0');

INSERT INTO User (userName, password, firstName, lastname, email, role) VALUES (
    'competitor3', '$2a$10$uicNGMO41KcyofyMPaCCwuQ89o1WM/wn5f9TGgCuFjZrfDLnECPyi', 'Daniel', 'Vicente',
        'daniel.vicente.ramos@udc.es', '0');

INSERT INTO User (userName, password, firstName, lastname, email, role) VALUES (
    'employee', '$2a$10$uicNGMO41KcyofyMPaCCwuQ89o1WM/wn5f9TGgCuFjZrfDLnECPyi', 'Juan', 'Raposo',
        'juan.raposo@udc.es', '1');
