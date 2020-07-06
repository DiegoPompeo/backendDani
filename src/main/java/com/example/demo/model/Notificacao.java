package com.example.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Notificacao {
    private Integer idPublicacao;
    private boolean visualizacao;
    private String tipoPublicacao;
    private String autor;
    private String titulo;
}
