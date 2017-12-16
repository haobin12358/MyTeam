use myteam;

drop table if exists Users;

create table Users(
	Uid varchar(64) primary key,
    Uname varchar(32) not null,
    Upwd varchar(32) not null,
    Utype int not null
);

drop table if exists Students;

create table Students(
	Sid varchar(64) primary key,
    Uid varchar(64) not null,
    Sname varchar(32) not null,
    Sno varchar(16) not null,
    Suniversity varchar(256) not null,
    Sschool varchar(256) not null,
    Stel varchar(16) not null,
    Sgrade int,
    Ssex int
);

drop table if exists Teachers;

create table Teachers(
	Tid varchar(64) primary key,
    Uid varchar(64) not null,
    Tname varchar(32) not null,
    Tno varchar(16) not null,
    Ttel varchar(16) not null,
    Tuniversity varchar(256) not null,
    Tschool varchar(256) not null,
    Ttime int
);

drop table if exists Competitions;

create table Competitions(
	Cid varchar(64) primary key,
    Cname varchar(128) not null,
    Cno int not null,
    Clevel int not null,
    Cstart varchar(10) not null,
    Cend varchar(10) not null,
    Cmin int,
    Cmax int,
    Cown varchar(64),
    Cabo text not null
);

drop table if exists Teams;

create table Teams(
	TEid varchar(64) primary key,
    Cid varchar(64) not null,
    TEname varchar(128) not null,
    TEno varchar(16),
    TEuse int not null
);

create table TStudent(
	TEid varchar(64) not null,
    Sid varchar(64) not null,
    TStype int
);

drop table if exists TTeacher;

create table TTeacher(
	TEid varchar(64) not null,
    Tid varchar(64) not null
);

drop table if exists STechs;

create table STechs(
	STid varchar(64) primary key,
    Sid varchar(64) not null,
    STname varchar(32) not null,
    STlevel int not null
);

drop table if exists SCuse;

create table SCuse(
	SCid varchar(64) primary key,
    Sid varchar(64) not null,
    SCname varchar(128) not null,
    SCno varchar(16) not null
);

drop table if exists TCuse;

create table TCuse(
	TCid varchar(64) primary key,
    Tid varchar(64) not null,
    TCname varchar(128) not null,
    TCno varchar(16) not null,
    TCnum int not null
);