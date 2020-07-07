package com.example.demo.services;

import com.example.demo.entidade.Artigo;
import com.example.demo.entidade.Pessoa;
import com.example.demo.model.Notificacao;
import com.example.demo.repositorio.ArtigoRepository;
import com.example.demo.repositorio.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
public class ArtigoService {
    @Autowired
    private ArtigoRepository artigoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Artigo create(Artigo artigo){
        Pessoa pessoa = pessoaRepository.findByEmail(artigo.getEmailAutor());
        List<Integer> seguidores = pessoa.getSeguidores();
        Artigo artigoSalvo = artigoRepository.save(artigo);

        for (int i = 0; i < seguidores.size(); i++) {
            Notificacao notificacao = new Notificacao();
            Pessoa aux = pessoaRepository.findById(seguidores.get(i)).get();

            notificacao.setIdPublicacao(artigoSalvo.getId());
            notificacao.setVisualizacao(false);
            notificacao.setTipoPublicacao("Artigo");
            notificacao.setAutor(pessoa.getInformacao().getNomePessoa());
            notificacao.setTitulo(artigo.getConteudo().getTitulo());

            aux.getNotificacao().add(notificacao);

            pessoaRepository.save(aux);
        }
        return artigoSalvo;
    }

    public Artigo createArquivo(MultipartFile file, Integer idArtigo) throws IOException {
        Artigo artigoSalvo = artigoRepository.findById(idArtigo).get();

        artigoSalvo.getArquivo().setDocName(file.getOriginalFilename());
        artigoSalvo.getArquivo().setFile(file.getBytes());
        artigoSalvo.getArquivo().setType(file.getContentType());

        return artigoRepository.save(artigoSalvo);
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
        if(artigoRepository.existsById(id)){
            artigoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean analisaArtigo(Integer idArtigo, Integer idPessoa) {
        if (artigoRepository.findById(idArtigo).get() == null ||
                pessoaRepository.findById(idPessoa).get() == null){
            return false;
        }
        return pessoaRepository.findById(idPessoa).get().getArtigosCurtidos().contains(idArtigo);
    }

    public Artigo curtir(Integer idPessoaCurtiu, Integer idArtigoCurtido) {
        Pessoa pessoa = pessoaRepository.findById(idPessoaCurtiu).get();
        Artigo artigo = artigoRepository.findById(idArtigoCurtido).get();

        pessoa.getArtigosCurtidos().add(idArtigoCurtido);

        artigo.getMembrosCurtiram().add(idPessoaCurtiu);
        artigo.setCurtidas(artigo.getCurtidas() + 1);

        artigoRepository.save(artigo);
        pessoaRepository.save(pessoa);

        return artigo;
    }

    public Artigo undoCurtir(Integer idPessoaCurtiu, Integer idArtigoCurtido) {
        Pessoa pessoa = pessoaRepository.findById(idPessoaCurtiu).get();
        Artigo artigo = artigoRepository.findById(idArtigoCurtido).get();

        pessoa.getArtigosCurtidos().remove(idArtigoCurtido);

        artigo.getMembrosCurtiram().remove(idPessoaCurtiu);
        artigo.setCurtidas(artigo.getCurtidas() - 1);

        artigoRepository.save(artigo);
        pessoaRepository.save(pessoa);

        return artigo;
    }

    public List<Artigo> readAll() {
        return artigoRepository.findAll();
    }


}
