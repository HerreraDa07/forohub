package com.forohub.forohub.dominio.usuario;

public record UsuarioListadoDto(Long id, String nombre, String correo) {
    public UsuarioListadoDto(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreo());
    }
}