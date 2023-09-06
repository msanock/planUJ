CREATE SCHEMA IF NOT EXISTS projektuj;

CREATE  TABLE IF NOT EXISTS projektuj.tbl (
	users_tasks          varchar[]    
 );

CREATE  TABLE IF NOT EXISTS projektuj.users (
	id                   serial  NOT NULL  ,
	name                 varchar  NOT NULL  UNIQUE,
	CONSTRAINT pk_users PRIMARY KEY ( id )
 );

CREATE  TABLE IF NOT EXISTS projektuj.teams_users (
	user_id              integer  NOT NULL  ,
	team_id              integer  NOT NULL  ,
	"role"               varchar    ,
	"position"           varchar    ,
	CONSTRAINT unq_teams_users_team_id UNIQUE ( team_id , user_id),
	CONSTRAINT fk_teams_users_users FOREIGN KEY ( user_id ) REFERENCES projektuj.users( id )   
 );

CREATE  TABLE IF NOT EXISTS projektuj.teams (
	id                   serial  NOT NULL  ,
	name                 varchar  NOT NULL  UNIQUE,
	CONSTRAINT pk_teams PRIMARY KEY ( id )
 );

CREATE  TABLE IF NOT EXISTS projektuj.tasks (
	id                   serial    ,
	team_id              integer  NOT NULL  ,
	info                 varchar    ,
	status              varchar    ,
	priority            varchar    ,
	deadline             date    ,
	CONSTRAINT unq_tasks_id UNIQUE ( id ) ,
	CONSTRAINT fk_tasks_teams FOREIGN KEY ( team_id ) REFERENCES projektuj.teams( id )
 );

CREATE  TABLE IF NOT EXISTS projektuj.users_tasks (
	user_id              integer  NOT NULL  ,
	task_id              integer  NOT NULL  ,
	CONSTRAINT unq_users_tasks_team_id UNIQUE ( team_id , user_id),
	CONSTRAINT fk_users_tasks_users FOREIGN KEY ( user_id ) REFERENCES projektuj.users( id )   ,
	CONSTRAINT fk_users_tasks_tasks FOREIGN KEY ( task_id ) REFERENCES projektuj.tasks( id )   
 );
