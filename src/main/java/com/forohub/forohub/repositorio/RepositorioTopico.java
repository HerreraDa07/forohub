package com.forohub.forohub.repositorio;

import com.forohub.forohub.dominio.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTopico extends JpaRepository<Topico, Long> {
}