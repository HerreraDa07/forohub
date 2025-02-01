create table topicos
(
id bigint not null auto_increment,
titulo varchar(100) not null unique,
mensaje text not null,
fecha datetime not null,
estatus varchar(100) not null,
autor_nombre varchar(100) not null,
autor_correo varchar(100) not null,
id_curso bigint not null,
primary key (id),
foreign key (id_curso) references cursos (id) on delete cascade on update cascade
)