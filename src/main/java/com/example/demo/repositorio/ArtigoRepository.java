package com.example.demo.repositorio;

import com.example.demo.entidade.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtigoRepository extends JpaRepository<Artigo, Integer> {
    List<Artigo> findByEmailAutor(String emailAutor);
}
