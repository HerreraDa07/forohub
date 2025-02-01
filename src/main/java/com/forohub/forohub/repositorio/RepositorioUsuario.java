package com.forohub.forohub.repositorio;

import com.forohub.forohub.dominio.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    UserDetails findByCorreo(String username);
}