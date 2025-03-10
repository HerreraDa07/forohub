package com.forohub.forohub.dominio.respuesta;

import com.forohub.forohub.dominio.topico.TopicoUsuarioDto;

import java.time.LocalDateTime;

public record RespuestaUsuarioDto(Long id, String mensaje, String solucion, LocalDateTime fecha,
                                  TopicoUsuarioDto topico) {
}