package com.example.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Conteudo {
    private String titulo;
    private String localDaPublicacao;
    private String anoDaPublicacao;
    private String url;
    private String tags;
}
