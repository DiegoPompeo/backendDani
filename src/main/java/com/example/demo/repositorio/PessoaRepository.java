package com.example.demo.repositorio;

import com.example.demo.entidade.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    Pessoa findByEmail(String email);
}
