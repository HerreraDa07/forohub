create table respuestas
(
id bigint not null auto_increment,
mensaje varchar(30) not null unique,
id_topico bigint not null,
fecha datetime not null,
autor_nombre varchar(100) not null,
autor_correo varchar(100) not null,
solucion text not null,
primary key (id),
foreign key (id_topico) references topicos (id) on delete cascade on update cascade
)