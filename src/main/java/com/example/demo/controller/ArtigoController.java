package com.example.demo.controller;

import com.example.demo.entidade.Arquivo;
import com.example.demo.entidade.Artigo;
import com.example.demo.services.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("arquivo/{idArtigo}")
    public Artigo criaArquivo(@RequestParam("myFile")MultipartFile file,
                               @PathVariable Integer idArtigo) throws IOException {
        return artigoService.createArquivo(file, idArtigo);
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

    @GetMapping("analisaArtigo/{idArtigo}/{idPessoa}")
    public boolean analisaArtigo(@PathVariable Integer idArtigo, @PathVariable Integer idPessoa){
        return artigoService.analisaArtigo(idArtigo, idPessoa);
    }

    @GetMapping("curtir/{idPessoaCurtiu}/{idArtigoCurtido}")
    public Artigo curtir(@PathVariable Integer idPessoaCurtiu, @PathVariable Integer idArtigoCurtido){
        return artigoService.curtir(idPessoaCurtiu, idArtigoCurtido);
    }

    @GetMapping("undoCurtir/{idPessoaCurtiu}/{idArtigoCurtido}")
    public Artigo undoCurtir(@PathVariable Integer idPessoaCurtiu, @PathVariable Integer idArtigoCurtido){
        return artigoService.undoCurtir(idPessoaCurtiu, idArtigoCurtido);
    }

    @GetMapping("listaTodos")
    public List<Artigo> listaAll(){
        return artigoService.readAll();
    }
}
