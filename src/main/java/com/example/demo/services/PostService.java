package com.example.demo.services;

import com.example.demo.entidade.Pessoa;
import com.example.demo.entidade.Post;
import com.example.demo.repositorio.PessoaRepository;
import com.example.demo.repositorio.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        if(posts.size() > 0){
            return posts;
        }
        return null;
    }

    public List<Post> findByEmailAutor(String emailAutor) {
        return postRepository.findByEmailAutor(emailAutor);
    }

    public boolean analisaPost(Integer idPost, Integer idPessoa){
        if (postRepository.findById(idPost).get() == null ||
                pessoaRepository.findById(idPessoa).get() == null){
            return false;
        }
        return pessoaRepository.findById(idPessoa).get().getPostsCurtidos().contains(idPost);
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).get();
    }

    public boolean deletePost(Integer idPost) {
        Post post = postRepository.findById(idPost).get();
        if (post != null){
            postRepository.delete(post);
            return true;
        }
        return false;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post atualizaPost(Post post) {
        if (postRepository.existsById(post.getId())){
            Post post_update = postRepository.findById(post.getId()).get();
            post_update.setConteudo(post.getConteudo());
            post_update.setCurtidas(post.getCurtidas());
            return postRepository.save(post_update);
        }
        return postRepository.save(post);
    }

    public Post curtir(Integer idPessoaCurtiu, Integer idPostCurtido) {
        Pessoa pessoa = pessoaRepository.findById(idPessoaCurtiu).get();
        Post post = postRepository.findById(idPostCurtido).get();

        pessoa.getPostsCurtidos().add(idPostCurtido);

        post.getPessoasCurtiram().add(idPessoaCurtiu);
        post.setCurtidas(post.getCurtidas() + 1);

        postRepository.save(post);
        pessoaRepository.save(pessoa);

        return post;
    }

    public Post undoCurtir(Integer idPessoaCurtiu, Integer idPostCurtido){
        Pessoa pessoa = pessoaRepository.findById(idPessoaCurtiu).get();
        Post post = postRepository.findById(idPostCurtido).get();

        pessoa.getPostsCurtidos().remove(idPostCurtido);

        post.getPessoasCurtiram().remove(idPessoaCurtiu);
        post.setCurtidas(post.getCurtidas() - 1);

        postRepository.save(post);
        pessoaRepository.save(pessoa);

        return post;
    }
}
