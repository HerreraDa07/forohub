package com.forohub.forohub.dominio.topico;

import com.forohub.forohub.dominio.autor.AutorDto;

public record TopicoUsuarioDto(String titulo, String mensaje, AutorDto autor) {
}
