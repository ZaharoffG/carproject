package com.projeto.carproject.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.projeto.carproject.model.Modelo;
import com.projeto.carproject.model.Fabricante;
import com.projeto.carproject.repository.ModeloRepository;
import com.projeto.carproject.repository.FabricanteRepository;
import ch.qos.logback.core.testUtil.RandomUtil;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/{fabricante_id}/modelo")
public class ModeloController {
    
    @Autowired
    private ModeloRepository repository;

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @GetMapping()
    public String index(Model modelView, @PathVariable("fabricante_id") int fabricante_id) {
        //Obtendo todas os modelos do fabricante
        Fabricante fabricante = fabricanteRepository.findById(fabricante_id).get();

        modelView.addAttribute("fabricante_id", fabricante_id);
        modelView.addAttribute("listaModelos", fabricante.getModelos());
        return "lista-modelo";
    }

    @GetMapping("/create")
    public String createModelo(@PathVariable("fabricante_id") int fabricante_id, Model modelView, Modelo modelo) {
        modelView.addAttribute("fabricante_id", fabricante_id);
        return "criar-modelo";
    }

    @PostMapping("/saveModelo")
    public String saveModelo(@PathVariable("fabricante_id") int fabricante_id, @Valid @ModelAttribute Modelo modelo, BindingResult result, Model modelView) {

        if (result.hasErrors()) {
            modelView.addAttribute("fabricante_id", fabricante_id);
            return "criar-modelo";
        }
        
        //Obtendo o fabricante a ser adicionado o modelo
        Fabricante fabricante = fabricanteRepository.findById(fabricante_id).get();

        //Setando a hora que o modelo foi adicionado
        modelo.setCreatedDate(LocalDate.now());

        //Adicionando o modelo na lista do fabricante
        fabricante.getModelos().add(modelo);

        //Adiciona o modelo no banco de dados
        repository.save(modelo);

        //Associa o modelo ao fabricante
        fabricanteRepository.save(fabricante);


        
        return "redirect:/"+fabricante.getId()+"/modelo" ;
    }

    @GetMapping("/{id}/deleteModelo")
    public String deleteModelo(@PathVariable("fabricante_id") Integer fabricante_id, @PathVariable("id") Integer id) {
        Optional<Modelo> optmodelo = repository.findById(id);

        if (optmodelo.isPresent()) {
            repository.delete(optmodelo.get());
        }

        return "redirect:/"+fabricante_id+"/modelo";  // Usando o fabricante_id correto
    }


    @GetMapping("{id}/editar")
    public String editar(Model modelAndView, @PathVariable("fabricante_id") Integer fabricante_id, @PathVariable("id") Integer id) {
        Optional<Modelo> optmodelo = repository.findById(id);

        if (optmodelo.isPresent()) {
            modelAndView.addAttribute("editModelo", optmodelo.get());
            modelAndView.addAttribute("fabricante_id", fabricante_id);  // Adiciona o fabricante_id
            return "editar-modelo";
        }
        return "redirect:/"+fabricante_id+"/modelo";
    }
    
    @PostMapping("{id}/editModelo")
    public String editModelo(@PathVariable("fabricante_id") Integer fabricante_id, @PathVariable("id") Integer id, @ModelAttribute Modelo newData, BindingResult result) {
        if (result.hasErrors()) {
            return "editar-modelo";
        }
    
        Optional<Modelo> optmodelo = repository.findById(id);
    
        if (optmodelo.isPresent()) {
            Modelo modelo = optmodelo.get();
            modelo.setName(newData.getName());
            modelo.setTipo(newData.getTipo());
            modelo.setDescricao(newData.getDescricao());
            modelo.setImagem(newData.getImagem());
    
            repository.save(modelo);
        }
    
        // Redireciona para a lista de modelos do fabricante, garantindo que o fabricante_id esteja correto
        return "redirect:/"+fabricante_id+"/modelo";
    }
    
    
    
    
    
    
    
}
