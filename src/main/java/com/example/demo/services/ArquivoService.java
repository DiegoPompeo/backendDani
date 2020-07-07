package com.example.demo.services;

import com.example.demo.entidade.Arquivo;
import com.example.demo.repositorio.ArquivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ArquivoService {
    @Autowired
    private ArquivoRepository arquivoRepository;

    public Arquivo save(MultipartFile file, Integer idArtigo) throws IOException {
        Arquivo arquivo = new Arquivo();
        arquivo.setNroArtigo(idArtigo);
        arquivo.setDocName(file.getOriginalFilename());
        arquivo.setFile(file.getBytes());
        arquivo.setType(file.getContentType());

        return arquivoRepository.save(arquivo);
    }

    public List<Arquivo> findAll() {
        return arquivoRepository.findAll();
    }

    public Arquivo findFileByIdArtigo(Integer artigoId) {
        List<Arquivo> lista = arquivoRepository.findAll();
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getNroArtigo().equals(artigoId)){
                return arquivoRepository.findById(lista.get(i).getId()).get();
            }
        }
        return null;
    }

    public Arquivo findArquivoById(Integer idArquivo) {
        return arquivoRepository.findById(idArquivo).get();
    }

    public void delete(Integer id){
        arquivoRepository.deleteById(id);
    }

    public void deleteAll(){
        arquivoRepository.deleteAll();
    }
}
