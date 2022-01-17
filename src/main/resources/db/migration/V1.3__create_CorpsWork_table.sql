create table Corpswork(
                          id int NOT NULL AUTO_INCREMENT,
                          name varchar (255 )NOT NULL,
                          PRIMARY KEY (id)
);
ALTER TABLE employee ADD column corps_id int;
--alter table EMPLOYEE ADD CONSTRAINT FOREIGN KEY(corps_id) REFERENCES Corpswork(id);
