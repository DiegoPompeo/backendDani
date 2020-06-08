package com.example.demo.controller;

import com.example.demo.entidade.Artigo;
import com.example.demo.services.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/artigo/")
public class ArtigoController {
    @Autowired
    private ArtigoService artigoService;

    @PostMapping("criaArtigo")
    public Artigo criaArtigo(@RequestBody Artigo artigo){
        return artigoService.create(artigo);
    }

    @GetMapping("listaPorEmail/{emailAutor}")
    public List<Artigo> listaPorEmail(@PathVariable String emailAutor){
        return artigoService.readAllByEmailAutor(emailAutor);
    }

    @GetMapping("procuraArtigoPorId/{idArtigo}")
    public Artigo procuraArtigoPorId(@PathVariable Integer idArtigo){
        return artigoService.readById(idArtigo);
    }

    @PutMapping("atualizaArtigo")
    public Artigo atualizaArtigo(@RequestBody Artigo artigo){
        return artigoService.update(artigo);
    }

    @DeleteMapping("deletaArtigo/{idArtigo}")
    public boolean deletaArtigo(@PathVariable Integer idArtigo){
        return artigoService.delete(idArtigo);
    }
}
