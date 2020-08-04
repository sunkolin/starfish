/*删除数据库*/

drop database if exists sampledb;

/*创建数据库*/
create database sampledb default character set utf8;

/*使用此数据库*/
use sampledb;

/*创建用户表*/
create table t_user(user_id int auto_increment primary key,user_name varchar(30),credits int,password varchar(32),last_visit datetime,last_ip varchar(23))engine=innodb;

/*创建日志表*/
create table t_login_log(login_log_id int auto_increment primary key,user_id int,ip varchar(23),login_datetime datetime)engine=innodb;

/*初始化数据*/
insert into t_user(user_name,password) values('admin','123456');
commit;

