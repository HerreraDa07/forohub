package com.forohub.forohub.dominio.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopicoRegistroDto(@NotBlank String titulo, @NotBlank String mensaje, LocalDateTime fecha,
                                @NotBlank String estatus, @NotNull Long curso) {
}