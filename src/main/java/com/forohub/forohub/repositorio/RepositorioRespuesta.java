package com.forohub.forohub.repositorio;

import com.forohub.forohub.dominio.respuesta.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioRespuesta extends JpaRepository<Respuesta, Long> {
}