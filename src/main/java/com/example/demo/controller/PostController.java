package com.example.demo.controller;

import com.example.demo.entidade.Post;
import com.example.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/post/")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("criar")
    public Post create(@RequestBody Post post){
        return postService.save(post);
    }

    @GetMapping("listaTodos")
    public List<Post> readAll(){
        return postService.findAll();
    }

    @GetMapping("listaTodosPorEmail/{emailAutor}")
    public List<Post> listaPorEmail(@PathVariable String emailAutor){
        return postService.findByEmailAutor(emailAutor);
    }

    @GetMapping("analisaPost/{idPost}/{idPessoa}")
    public boolean analisaPost(@PathVariable Integer idPost, @PathVariable Integer idPessoa){
        return postService.analisaPost(idPost, idPessoa);
    }

    @PutMapping("editarPost")
    public Post updatePost(@RequestBody Post post){
        return postService.atualizaPost(post);
    }

    @GetMapping("listaPorId/{id}")
    public Post readById(@PathVariable Integer id){
        return postService.findById(id);
    }

    @DeleteMapping("deletaPost/{idPost}")
    public boolean delelePost(@PathVariable Integer idPost){
        return postService.deletePost(idPost);
    }

    @GetMapping("curtir/{idPessoaCurtiu}/{idPostCurtido}")
    public Post curtir(@PathVariable Integer idPessoaCurtiu, @PathVariable Integer idPostCurtido){
        return postService.curtir(idPessoaCurtiu, idPostCurtido);
    }

    @GetMapping("undoCurtir/{idPessoaCurtiu}/{idPostCurtido}")
    public Post undoCurtir(@PathVariable Integer idPessoaCurtiu, @PathVariable Integer idPostCurtido){
        return postService.undoCurtir(idPessoaCurtiu, idPostCurtido);
    }
}
