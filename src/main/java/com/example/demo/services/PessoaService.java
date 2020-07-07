package com.example.demo.services;

import com.example.demo.entidade.Pessoa;
import com.example.demo.model.Informacao;
import com.example.demo.model.Notificacao;
import com.example.demo.repositorio.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa save(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findById(Integer id) {
        if (pessoaRepository.existsById(id)){
            return pessoaRepository.findById(id).get();
        }
        return null;
    }

    public Pessoa findByEmail(String email) {
        return pessoaRepository.findByEmail(email);
    }

    public Pessoa login(Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        String email = pessoa.getEmail();
        String senha = pessoa.getInformacao().getSenha();

        for (Pessoa p: pessoas){
            if (p.getEmail().equals(email)
            && p.getInformacao().getSenha().equals(senha)) {
                return pessoa;
            }
        }
        return null;
    }

    public Pessoa updatePessoa(Pessoa pessoa) {
        if (pessoaRepository.existsById(pessoa.getId())) {
            Pessoa pessoa_update = pessoaRepository.findById(pessoa.getId()).get();

            pessoa_update.setCurriculo(pessoa.getCurriculo());
            pessoa_update.setFormacao(pessoa.getFormacao());
            pessoa_update.setInfoAdicionais(pessoa.getInfoAdicionais());
            pessoa_update.setInformacao(pessoa.getInformacao());
            pessoa_update.setInteresses(pessoa.getInteresses());
            pessoa_update.setTrabalho(pessoa.getTrabalho());

            return pessoaRepository.save(pessoa_update);
        }

        return null;
    }

    public List<Pessoa> findAllExcept(String email) {
        List<Pessoa> lista = pessoaRepository.findAll();
        Pessoa pessoa = pessoaRepository.findByEmail(email);
        if (pessoa != null){
            for(Pessoa p: lista){
                if (p == pessoa){
                    lista.remove(p);
                    return lista;
                }
            }
        }
        return lista;
    }

    public List<Pessoa> findSeguindo(Integer id) {
        List<Integer> listaSeguindo = pessoaRepository.findById(id).get().getSeguindo();
        List<Pessoa> pessoaSeguindo = new ArrayList<>();
        for (int i = 0; i < listaSeguindo.size(); i++) {
            pessoaSeguindo.add(pessoaRepository.findById(listaSeguindo.get(i)).get());
        }
        return pessoaSeguindo;
    }

    public List<Pessoa> findSeguidores(Integer id) {
        List<Integer> listaSeguidores = pessoaRepository.findById(id).get().getSeguidores();
        List<Pessoa> pessoaSeguidores = new ArrayList<>();
        for (int i = 0; i < listaSeguidores.size(); i++) {
            pessoaSeguidores.add(pessoaRepository.findById(listaSeguidores.get(i)).get());
        }
        return pessoaSeguidores;
    }

    public boolean verificaFollow(Integer id, Integer idASeguir) {
        return pessoaRepository.findById(id).get().getSeguindo().contains(idASeguir);
    }

    public List<Pessoa> followPessoa(Integer id, Integer idSeguir) {
        List<Pessoa> lista = new ArrayList<>();
        Pessoa seguidor = pessoaRepository.findById(id).get();
        Pessoa seguindo = pessoaRepository.findById(idSeguir).get();

        if(!(seguidor.getSeguindo().contains(idSeguir))) {
            seguidor.getSeguindo().add(idSeguir);
            pessoaRepository.save(seguidor);
            lista.add(seguidor);
            seguindo.getSeguidores().add(id);
            pessoaRepository.save(seguindo);
            lista.add(seguindo);
        }

        return lista;
    }

    public List<Pessoa> undoFollowPessoa(Integer id, Integer idDeixarDeSeguir) {
        List<Pessoa> lista = new ArrayList<>();

        Pessoa seguidor = pessoaRepository.findById(id).get();
        Pessoa seguindo = pessoaRepository.findById(idDeixarDeSeguir).get();

        if(seguidor.getSeguindo().contains(idDeixarDeSeguir)) {
            if (!(seguidor.getSeguindo().isEmpty())){
                seguidor.getSeguindo().remove(idDeixarDeSeguir);
                pessoaRepository.save(seguidor);

                lista.add(seguidor);
            }

            if (!(seguindo.getSeguidores().isEmpty())){
                seguindo.getSeguidores().remove(id);
                pessoaRepository.save(seguindo);

                lista.add(seguindo);
            }
        }

        return lista;
    }

    public List<Notificacao> getAllNotificacao(String email) {
        return pessoaRepository.findByEmail(email).getNotificacao();
    }
}
