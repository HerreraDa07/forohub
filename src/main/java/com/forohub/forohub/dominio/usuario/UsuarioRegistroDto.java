package com.forohub.forohub.dominio.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRegistroDto(@NotBlank String nombre, @NotBlank @Email String correo, @NotBlank String clave) {
}