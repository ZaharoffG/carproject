package com.projeto.carproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.carproject.model.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

    
} 