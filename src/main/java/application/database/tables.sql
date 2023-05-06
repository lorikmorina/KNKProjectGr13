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

CREATE TABLE children (
                          child_id INT NOT NULL AUTO_INCREMENT,
                          childsName VARCHAR(50) NOT NULL,
                          parent_id int NOT NULL,
                          age int NOT NULL,
                          teacher VARCHAR(50) NOT NULL,
                          classroomNr int,
                          contactInfo VARCHAR(20),
                          medicalInfo VARCHAR(256),
                          PRIMARY KEY (child_id),
                          foreign key(parent_id) references parents(id)
);

Select * from children;