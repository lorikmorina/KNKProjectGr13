use knk2023;
CREATE TABLE parents (
                         id INT NOT NULL AUTO_INCREMENT,
                         fullname VARCHAR(50) NOT NULL,
                         email VARCHAR(50) NOT NULL,
                         personalNr VARCHAR(50) NOT NULL,
                         salted_hash VARCHAR(256) NOT NULL,
                         salt VARCHAR(256) NOT NULL,
                         PRIMARY KEY (id)
);
Select * from parents;