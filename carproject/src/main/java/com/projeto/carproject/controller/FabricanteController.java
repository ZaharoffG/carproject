package com.projeto.carproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projeto.carproject.model.Modelo;
import com.projeto.carproject.model.Fabricante;
import com.projeto.carproject.repository.FabricanteRepository;
import ch.qos.logback.core.testUtil.RandomUtil;
import jakarta.validation.Valid;

import java.util.Optional;

@Controller
@RequestMapping("/fabricante")
public class FabricanteController {

    @Autowired
    private FabricanteRepository repository;

    @GetMapping()
    public String index(Model modelView) {
        // Obtendo todos os usuários
        modelView.addAttribute("listaFabricante", repository.findAll());
        return "lista-fabricante";
    }

    @GetMapping("/create")
    public String createFabricante(Fabricante fabricante) {
        return "criar-fabricante";
    }

    @PostMapping("/saveFabricante")
    public String saveFabricante(@Valid @ModelAttribute Fabricante fabricante, BindingResult result) {

        if (result.hasErrors()) {
            return "criar-fabricante";
        }

        // Inserção no banco de dados
        repository.save(fabricante); 

        return "redirect:/fabricante";
    }

    @GetMapping("/delete/{id}")
    public String deleteFabricante(@PathVariable("id") Integer id) {
        Optional<Fabricante> optFabricante = repository.findById(id);

        if (optFabricante.isPresent()) {
            repository.delete(optFabricante.get());
        }

        return "redirect:/fabricante";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model modelAndView, @PathVariable("id") Integer id) {
        Optional<Fabricante> optFabricante = repository.findById(id);

        modelAndView.addAttribute("editFabricante", optFabricante.get());
        return "editar-fabricante";
    }

    @PostMapping("/editFabricante/{id}")
    public String editFabricante(@PathVariable("id") Integer id, @Valid @ModelAttribute Fabricante newData, BindingResult result, Model modelAndView) {

        if (result.hasErrors()) {
            modelAndView.addAttribute("editFabricante", newData);
            return "editar-fabricante";
        }

        Optional<Fabricante> optFabricante = repository.findById(id);

        Fabricante fabricante = optFabricante.get();

        fabricante.setName(newData.getName());

        repository.save(fabricante);
        
        return "redirect:/fabricante";
    }



}
