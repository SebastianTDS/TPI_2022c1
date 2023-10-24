drop database if exists IumachStudent;
create database if not exists IumachStudent;
use iumachstudent;

create table student
(	id varchar(255) primary key,
     firstName varchar(255) null,
     lastName varchar(255) null,
     birthday date null,
     valoration int null,
     email varchar(255) null,
     phone  varchar(255) null
);