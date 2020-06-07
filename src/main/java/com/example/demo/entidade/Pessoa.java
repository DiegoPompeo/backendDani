package com.example.demo.entidade;

import com.example.demo.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private Curriculo curriculo;
    private Formacao formacao;
    private InfoAdicionais infoAdicionais;
    private Informacao informacao;
    private Trabalho trabalho;

    private String interesses;

    @ElementCollection
    @CollectionTable(name="seguindo", joinColumns=@JoinColumn(name="id_seguindo"))
    @Column(name="id_seguindo")
    private List<Integer> seguindo;

    @ElementCollection
    @CollectionTable(name="seguidores", joinColumns=@JoinColumn(name="id_seguidores"))
    @Column(name="id_seguidores")
    private List<Integer> seguidores;
}
