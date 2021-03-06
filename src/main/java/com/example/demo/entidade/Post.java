package com.example.demo.entidade;

import com.example.demo.model.Conteudo;
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
public class Post {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private String mensagem;

    private String emailAutor;

    private Integer curtidas = 0;

    @ElementCollection
    @CollectionTable(name="curtiram", joinColumns=@JoinColumn(name="curtiram_id"))
    @Column(name="id_curtiram")
    private List<Integer> pessoasCurtiram;
}
