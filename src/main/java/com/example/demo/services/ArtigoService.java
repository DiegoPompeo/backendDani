package com.example.demo.services;

import com.example.demo.entidade.Artigo;
import com.example.demo.entidade.Post;
import com.example.demo.repositorio.ArtigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtigoService {
    @Autowired
    private ArtigoRepository artigoRepository;

    public Artigo create(Artigo artigo){
        return artigoRepository.save(artigo);
    }

    public List<Artigo> readAllByEmailAutor(String emailAutor){
        return artigoRepository.findByEmailAutor(emailAutor);
    }

    public Artigo readById(Integer id){
        return artigoRepository.findById(id).get();
    }

    public Artigo update(Artigo artigo){
        if (artigoRepository.existsById(artigo.getId())){
            Artigo _artigo = artigoRepository.findById(artigo.getId()).get();
            _artigo.setConteudo(artigo.getConteudo());

            return artigoRepository.save(_artigo);
        }
        return artigoRepository.save(artigo);
    }

    public boolean delete(Integer id){
        if(!(artigoRepository.existsById(id))){
            artigoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
