DROP TABLE Inscription;
DROP TABLE SportEvent;
DROP TABLE Province;
DROP TABLE SportEventType;
DROP TABLE User;

-----------------PROVINCE-----------------------------------------------------------
CREATE TABLE Province (
    provinceId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    CONSTRAINT ProvincePK PRIMARY KEY (provinceId)
) ENGINE = InnoDB;


----------------SportEventType------------------------------------------------------
CREATE TABLE SportEventType (
    typeId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    CONSTRAINT SportEventTypePK PRIMARY KEY (typeId)
) ENGINE = Innodb;


----------------SportEvent-----------------------------------------------------------
CREATE TABLE SportEvent (
    eventId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    date TIMESTAMP NOT NULL,
    price DECIMAL(11, 2) NOT NULL,
    maxParticipants SMALLINT NOT NULL,
    numParticipants SMALLINT NOT NULL,
    location VARCHAR(50) NOT NULL,
    typeId BIGINT NOT NULL,
    provinceId BIGINT NOT NULL,
    numberOfRates SMALLINT NOT NULL,
    valueOfRates SMALLINT NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT SportEventPK PRIMARY KEY (eventId),
    CONSTRAINT SportEventTypeIdFK FOREIGN KEY (typeId) REFERENCES SportEventType (typeId),
    CONSTRAINT SportEventProvinceIdFK FOREIGN KEY (provinceId) REFERENCES Province (provinceId)
) ENGINE = InnoDB;

--------------User---------------------------------------------------------------------
CREATE TABLE User (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
    password VARCHAR(60) NOT NULL,
    firstName VARCHAR(60) NOT NULL,
    lastName VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UserNameUniqueKey UNIQUE (userName)
) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

--------------Inscription---------------------------------------------------------------
CREATE TABLE Inscription (
    inscriptionId BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    sportEventId BIGINT NOT NULL,
    dorsal SMALLINT NOT NULL,
    creditCard VARCHAR(255) COLLATE latin1_bin NOT NULL,
    collected BIT NOT NULL,
    rated BIT NOT NULL,
    rateValue TINYINT NOT NULL,
    date TIMESTAMP NOT NULL,
    CONSTRAINT InscriptionPK PRIMARY KEY (inscriptionId),
    CONSTRAINT InscriptionSportEventIdFK FOREIGN KEY (sportEventId) REFERENCES SportEvent (eventId),
    CONSTRAINT InscriptionUserIdFK FOREIGN KEY (userId) REFERENCES User (id)
) ENGINE = Innodb;