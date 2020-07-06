package com.example.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Interesse {
    private Integer id;
    private String nome;
}
