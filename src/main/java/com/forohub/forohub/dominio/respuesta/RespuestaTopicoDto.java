package com.forohub.forohub.dominio.respuesta;

import com.forohub.forohub.dominio.autor.Autor;

import java.time.LocalDateTime;

public record RespuestaTopicoDto(Long id, String mensaje, LocalDateTime fecha, Autor autor, String solucion) {
}