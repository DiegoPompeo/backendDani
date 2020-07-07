package com.example.demo.controller;

import com.example.demo.entidade.Arquivo;
import com.example.demo.services.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/arquivo/")
public class ArquivoController {
    @Autowired
    private ArquivoService arquivoService;

    @PostMapping("save/{id}")
    public Arquivo uploadFile(@RequestParam("myFile") MultipartFile file,
                              @PathVariable Integer id) throws IOException {
        return arquivoService.save(file, id);
    }

    @GetMapping("listaFile")
    public List<Arquivo> findAll(){
        return arquivoService.findAll();
    }

    @GetMapping("FiPorArtigo/{artigoId}")
    public Arquivo findAllByIdArtigo(@PathVariable Integer artigoId){
        return arquivoService.findFileByIdArtigo(artigoId);
    }

    @GetMapping("arquivo/{idArquivo}")
    public Arquivo findArquivoById(@PathVariable Integer idArquivo){
        return arquivoService.findArquivoById(idArquivo);
    }

    @DeleteMapping("removeAll")
    public void delete(){
        arquivoService.deleteAll();
    }

    @DeleteMapping("removePorId/{id}")
    public void deletePorId(@PathVariable Integer id){
        arquivoService.delete(id);
    }
}
