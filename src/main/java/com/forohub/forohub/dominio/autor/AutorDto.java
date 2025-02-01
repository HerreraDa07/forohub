package com.forohub.forohub.dominio.autor;

import jakarta.validation.constraints.NotBlank;

public record AutorDto(@NotBlank String nombre, @NotBlank String correo) {
}