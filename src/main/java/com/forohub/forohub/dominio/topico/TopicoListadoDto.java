package com.forohub.forohub.dominio.topico;

import java.time.LocalDateTime;

public record TopicoListadoDto(long id, String titulo, String mensaje, LocalDateTime fecha, String estatus) {
    public TopicoListadoDto(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus());
    }
}