package com.example.demo.repositorio;

import com.example.demo.entidade.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    Arquivo findByNroArtigo(Integer nroArtigo);
}
