package com.forohub.forohub.dominio.respuesta;

import java.time.LocalDateTime;

public record RespuestaListadoDto(Long id, String mensaje, LocalDateTime fecha, String solucion) {
    public RespuestaListadoDto(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFecha(), respuesta.getSolucion());
    }
}
