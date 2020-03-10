create table person (personId integer primary key not null,
                     `name` char(255),
                     mortal boolean default (true));

create table gameobject (objectId integer primary key not null,
                         `name` char(255),
                         description varchar(1024));

create table quest (questId integer primary key not null,
                    `name` char(255),
                    dialogue text);

create table `mod` (modId integer primary key not null,
                    `name` char(255),
                    installationDate date,
                    `binary` blob);

create table personInventory (personId integer,
                              objectId integer,
                              foreign key (personId) references person (personId),
                              foreign key (objectId) references gameobject (objectId));

create table questParticipation (questId integer,
                                 personId integer,
                                 foreign key (questId) references quest (questid),
                                 foreign key (personId) references person (personId));
