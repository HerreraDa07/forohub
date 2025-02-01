package com.forohub.forohub.dominio.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RespuestaRegistroDto(@NotBlank String mensaje, @NotNull Long topico, LocalDateTime fecha,
                                   @NotBlank String solucion) {
}