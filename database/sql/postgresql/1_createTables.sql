create table player (playerId integer primary key not null,
					 playerName varchar);

create table ability (abilityId integer primary key not null,
                      abilityName varchar,
                      abilityDescription varchar,
                      abilityLevel numeric default 0);
					
create table "character" (characterId integer primary key not null,
                        characterName varchar,
                        mortal boolean default true);
                     
create table gameobject (objectId integer primary key not null,
                         gameobjectName varchar,
                         gameobjectDescription varchar);
                         
create table quest (questId integer primary key not null,
                    questName varchar,
                    involvedCharacter text,
                    questDialogue text);
                    
create table "mod" (modId integer primary key not null,
                    modName varchar,
                    modInstallationDate date,
                    modBinary bytea);
                    
create table playerAbilities (playerId integer references player not null,
                              abilityId integer references ability not null);
                               
create table relationship (playerId integer references player not null,
                           personId integer references "character" not null,
                           relationshipLevel numeric default 0);
                           
create table activeQuests (playerId integer references player not null,
                           questId integer references quest not null,
                           questProgress numeric default 0);
                           
create table inventory (playerId integer references player not null,
                        objectId integer references gameobject not null,
                        stolen boolean default false);

create table characterInventory (characterId integer references "character" not null,
                                 objectId integer references gameobject not null);