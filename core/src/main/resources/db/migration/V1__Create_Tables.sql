
drop table if exists TALKS;
drop table if exists TIMESLOTS;
drop table if exists TRACKS;
drop table if exists DAYS;
drop table if exists conference;

create table if not exists CONFERENCE
	(
	ID uuid primary key,
	NAME varchar
	);

create table if not exists DAYS
	(
	ID uuid primary key,
	date date,
	CONFERENCE_ID uuid references CONFERENCE(ID)
	);


create table if not exists TRACKS
	(
	ID uuid primary key,
	NAME varchar,
	CONFERENCE_ID UUID references CONFERENCE(ID)
	);

create table if not exists TIMESLOTS
	(
	ID uuid primary key,
	TIME_FROM time,
	TIME_TO time,
	DAYS_ID uuid references DAYS(ID),
	TRACKS_ID uuid references TRACKS(ID)
	);

create table if not exists TALKS
	(
	ID uuid primary key,
	TITLE varchar,
	DESCRIPTION varchar,
	TOPIC varchar,
	TIMESLOT_ID uuid references TIMESLOTS(ID)
	);





