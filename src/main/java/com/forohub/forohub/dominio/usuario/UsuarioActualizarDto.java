package com.forohub.forohub.dominio.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UsuarioActualizarDto(@NotNull Long id, String nombre, @Email String correo, String clave) {
}