package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import br.edu.ifpb.pweb2.talenthub.service.EstagioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estagios")
public class EstagioController {

    @Autowired
    private EstagioService estagioService;

    @GetMapping
    public List<Estagio> listarTodos(){
        return estagioService.listarTodos();
    }

    @PostMapping
    public Estagio criar(@RequestBody Estagio estagio){
        return estagioService.salvar(estagio);
    }

    @GetMapping("/{id}")
    public Estagio buscarPorId(@PathVariable Long id){
        return estagioService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        estagioService.deletar(id);
    }



}
