create table usuarios
(
id bigint not null auto_increment,
nombre varchar(100) not null unique,
correo varchar(100) not null unique,
clave varchar(300) not null,
id_rol bigint not null,
primary key (id),
foreign key (id_rol) references roles (id) on delete cascade on update cascade
)