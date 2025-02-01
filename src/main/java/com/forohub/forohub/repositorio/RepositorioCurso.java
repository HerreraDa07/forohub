package com.forohub.forohub.repositorio;

import com.forohub.forohub.dominio.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCurso extends JpaRepository<Curso, Long> {
}