create database if not exists  `tangshi`;
use `tangshi`;
drop table if exists poetry_info;
create table if not  exists `poetry_info` (
  title   varchar(64)   not null,
  dynasty varchar(32)   not null,
  author  varchar(12)   not null,
  content varchar(1024) not null
);

# create database if not exists  `wenyanwen`;
# use `wenyanwen`;
# create table if not  exists `info`(
#   title varchar(64) not null ,
#   dynasty varchar(32) not null,
#   author varchar(12) not null,
#   content varchar(1024) not null
# );