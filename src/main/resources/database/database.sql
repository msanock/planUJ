CREATE SCHEMA IF NOT EXISTS projektuj;

CREATE  TABLE projektuj.tbl ( 
	users_tasks          varchar[]    
 );

CREATE  TABLE projektuj.users ( 
	id                   serial  NOT NULL  ,
	name                 varchar[]  NOT NULL  UNIQUE,
	CONSTRAINT pk_users PRIMARY KEY ( id )
 );

CREATE  TABLE projektuj.teams_users ( 
	user_id              integer  NOT NULL  ,
	team_id              integer  NOT NULL  ,
	"role"               varchar[]    ,
	"position"           varchar[]    ,
	CONSTRAINT unq_teams_users_team_id UNIQUE ( team_id ) ,
	CONSTRAINT fk_teams_users_users FOREIGN KEY ( user_id ) REFERENCES projektuj.users( id )   
 );

CREATE  TABLE projektuj.team ( 
	id                   serial  NOT NULL  ,
	name                 varchar[]  NOT NULL  ,
	CONSTRAINT pk_team PRIMARY KEY ( id ),
	CONSTRAINT fk_team_teams_users FOREIGN KEY ( id ) REFERENCES projektuj.teams_users( team_id )   
 );

CREATE  TABLE projektuj.tasks ( 
	id                   serial    ,
	team_id              integer  NOT NULL  ,
	info                 varchar[]    ,
	status              varchar[]    ,
	priority            varchar[]    ,
	deadline             date    ,
	CONSTRAINT unq_tasks_id UNIQUE ( id ) ,
	CONSTRAINT fk_tasks_team FOREIGN KEY ( team_id ) REFERENCES projektuj.team( id )   
 );

CREATE  TABLE projektuj.users_tasks ( 
	user_id              integer  NOT NULL  ,
	task_id              integer  NOT NULL  ,
	CONSTRAINT fk_users_tasks_users FOREIGN KEY ( user_id ) REFERENCES projektuj.users( id )   ,
	CONSTRAINT fk_users_tasks_tasks FOREIGN KEY ( task_id ) REFERENCES projektuj.tasks( id )   
 );
