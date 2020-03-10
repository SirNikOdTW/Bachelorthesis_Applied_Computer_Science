create table inventory (objectId integer not null);

create table relationships (personId integer not null,
                            relationshipLevel integer default 0);

create table activeQuests (questId integer not null,
                           progress integer default 0);

create table abilities (`name` char(255) not null,
                        description varchar(1024) not null,
                        `level` integer default 0);