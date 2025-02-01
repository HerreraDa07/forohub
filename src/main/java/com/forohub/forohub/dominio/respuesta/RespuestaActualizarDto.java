package com.forohub.forohub.dominio.respuesta;

import jakarta.validation.constraints.NotNull;

public record RespuestaActualizarDto(@NotNull Long id, String mensaje, String solucion) {
}