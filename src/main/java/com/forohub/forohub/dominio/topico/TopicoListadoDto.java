package com.forohub.forohub.dominio.topico;

import com.forohub.forohub.dominio.autor.AutorDto;

import java.time.LocalDateTime;

public record TopicoListadoDto(long id, String titulo, String mensaje, LocalDateTime fecha, String estatus,
                               AutorDto autor) {
    public TopicoListadoDto(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus(), new AutorDto(topico.getAutor().getNombre(), topico.getAutor().getCorreo()));
    }
}