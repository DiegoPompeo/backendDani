package com.example.demo.services;

import com.example.demo.entidade.Pessoa;
import com.example.demo.model.Informacao;
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
        List<Pessoa> pessoas = pessoaRepository.findAll();
        for (Pessoa pessoa: pessoas){
            if (pessoa.getInformacao().getEmail().equals(email)) {
                return pessoa;
            }
        }
        return null;
    }

    public Pessoa login(Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        String email = pessoa.getInformacao().getEmail();
        String senha = pessoa.getInformacao().getSenha();

        for (Pessoa p: pessoas){
            if (p.getInformacao().getEmail().equals(email)
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
        Pessoa pessoa = findByEmail(email);
        if (pessoa != null){
            for(Pessoa p: lista){
                if (p == pessoa){
                    lista.remove(p);
                    return lista;
                }
            }
        }
        return null;
    }

    public List<Pessoa> findSeguindo(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();

        if(!(pessoa.getSeguindo() == null || pessoa.getSeguindo() == "")){
            List<String> seguindo = Arrays.asList(pessoa.getSeguindo().split(","));
            List<Pessoa> aux = new ArrayList<>();
            for (int i = 0; i < seguindo.size(); i++) {
                aux.add(pessoaRepository.findById(Integer.parseInt(seguindo.get(i))).get());
            }
            return aux;
        }
        return null;
    }

    public List<Pessoa> findSeguidores(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();

        if(!(pessoa.getSeguidores() == null || pessoa.getSeguidores() == "")){
            List<String> seguidores = Arrays.asList(pessoa.getSeguidores().split(","));
            List<Pessoa> aux = new ArrayList<>();
            for (int i = 0; i < seguidores.size(); i++) {
                aux.add(pessoaRepository.findById(Integer.parseInt(seguidores.get(i))).get());
            }
            return aux;
        }
        return null;
    }

    public List<Pessoa> followPessoa(Integer id, Integer idSeguindo) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        Pessoa seguindo = pessoaRepository.findById(idSeguindo).get();

        pessoa.setSeguindo(pessoa.getSeguindo().concat(idSeguindo.toString() + ","));
        seguindo.setSeguidores(seguindo.getSeguindo().concat(pessoa.getId().toString() + ","));

        pessoaRepository.save(pessoa);
        pessoaRepository.save(seguindo);

        List<Pessoa> aux = new ArrayList<>();
        aux.add(pessoa);
        aux.add(seguindo);

        return aux;
    }

    public List<Pessoa> undoFollowPessoa(Integer id, Integer idSeguindo){
        Pessoa pessoa = pessoaRepository.findById(id).get();
        Pessoa seguindo = pessoaRepository.findById(idSeguindo).get();

        if(pessoa.getSeguidores() != null){
            List<String> aux = Arrays.asList(pessoa.getSeguidores().split(","));
            aux.remove(aux.indexOf(idSeguindo));
            pessoa.setSeguidores(aux.toString());
            pessoaRepository.save(pessoa);
        }

        if(seguindo.getSeguidores() != null){
            List<String> aux1 = Arrays.asList(seguindo.getSeguidores().split(","));
            aux1.remove(aux1.indexOf(idSeguindo));
            seguindo.setSeguidores(aux1.toString());
            pessoaRepository.save(seguindo);
        }

        List<Pessoa> lista_aux = new ArrayList<>();
        lista_aux.add(pessoa);
        lista_aux.add(seguindo);

        return lista_aux;
    }

    public boolean verificaFollow(Integer id, Integer idASeguir) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        if (pessoa.getSeguindo() != "" && pessoa.getSeguindo() != null){
            String[] aux = pessoa.getSeguindo().split(",");
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].equals(idASeguir.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
