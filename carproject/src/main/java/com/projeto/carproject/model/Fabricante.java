package com.projeto.carproject.model;

import java.time.LocalDate;

import lombok.Data;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Data
@Entity
public class Fabricante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message = "Campo nome não pode ser vazio")
    private String name;

    @Column
    private LocalDate createdDate;

    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "fabricante_id")
    private List<Modelo> modelos;
}
