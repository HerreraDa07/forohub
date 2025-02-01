package com.forohub.forohub.dominio.topico;

import jakarta.validation.constraints.NotNull;

public record TopicoActualizarDto(@NotNull Long id, String titulo, String mensaje, String estatus) {
}