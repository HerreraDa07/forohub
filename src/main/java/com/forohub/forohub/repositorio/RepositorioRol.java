package com.forohub.forohub.repositorio;

import com.forohub.forohub.dominio.rol.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioRol extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}