package com.forohub.forohub.repositorio;

import com.forohub.forohub.dominio.respuesta.Respuesta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioRespuesta extends JpaRepository<Respuesta, Long> {
    @Query("SELECT r FROM Respuesta r WHERE r.topico.id = :id ORDER BY r.id DESC")
    List<Respuesta> findByIdTopico(Long id, Pageable pageable);

    @Query("SELECT r FROM Respuesta r WHERE r.autor.nombre = :usuario")
    List<Respuesta> findByAutorNombre(@Param("usuario") String usuario);
}