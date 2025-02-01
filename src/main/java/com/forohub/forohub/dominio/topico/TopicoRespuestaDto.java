package com.forohub.forohub.dominio.topico;

import com.forohub.forohub.dominio.autor.AutorDto;
import com.forohub.forohub.dominio.curso.CursoDto;

import java.time.LocalDateTime;

public record TopicoRespuestaDto(String titulo, String mensaje, LocalDateTime fecha, String estatus, AutorDto autor,
                                 CursoDto curso) {
}