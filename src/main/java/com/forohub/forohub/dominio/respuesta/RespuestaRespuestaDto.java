package com.forohub.forohub.dominio.respuesta;

import com.forohub.forohub.dominio.autor.AutorDto;
import com.forohub.forohub.dominio.topico.TopicoRespuestaDto;

import java.time.LocalDateTime;

public record RespuestaRespuestaDto(Long id, String mensaje, TopicoRespuestaDto topico, LocalDateTime fecha,
                                    AutorDto autor, String solucion) {
}