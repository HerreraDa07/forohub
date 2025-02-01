create table roles
(
id bigint not null auto_increment,
nombre varchar(50) not null unique,
primary key (id)
)