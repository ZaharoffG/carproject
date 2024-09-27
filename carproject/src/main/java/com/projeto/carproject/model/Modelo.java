package com.projeto.carproject.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity(name = "modelos")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    @NotEmpty(message = "Campo nome do modelo não pode ser vazio")
    private String name;

    @Column
    @NotEmpty(message = "Campo tipo do modelo não pode ser vazio")
    private String tipo;
    
    @Column
    @NotEmpty(message = "Campo descricao do modelo não pode ser vazio")
    private String descricao;

    @Column
    @NotEmpty(message = "Campo imagem do modelo não pode ser vazio")
    private String imagem;

    @Column
    private LocalDate createdDate;

}
