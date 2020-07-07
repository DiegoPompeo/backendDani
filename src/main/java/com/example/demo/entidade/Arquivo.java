package com.example.demo.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Embeddable
@Data
public class Arquivo {

    private String docName;

    @Lob
    private byte[] file;

    private String type;
}