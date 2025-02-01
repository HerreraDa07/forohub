package com.forohub.forohub.dominio.rol;

import jakarta.validation.constraints.NotBlank;

public record RolDto(@NotBlank String nombre) {
}