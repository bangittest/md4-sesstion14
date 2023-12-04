create database sesstion14;
use sesstion14;

create table category(
    id int primary key auto_increment,
    name varchar(255) not null ,
    status bit(1) default 1
);

delimiter //
create procedure PROC_FIN_ALL()
begin
    select * from category;
end //
;

delimiter //
create procedure PROC_CREATE_CATE(in _name varchar(255),_status bit)
begin
    insert into category(name, status) values (_name,_status);
end //;

delimiter //
create procedure PROC_FIND_BY_ID(in _id int )
begin
    select * from category where id=_id;
end //


delimiter //
create procedure PROC_CHANGE_STATUS(in _id int)
begin
    update category set status=status^1 where id=_id;
end //

delimiter //
create procedure PROC_UPDATE_CATE(in _id int,_name varchar(255),_status bit)
begin
    update category set name=_name ,status=_status where id=_id;
end //