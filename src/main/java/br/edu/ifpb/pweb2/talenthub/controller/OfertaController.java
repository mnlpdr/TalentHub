package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ofertas")
public class OfertaController {


    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public List<Oferta> listarTodos(){
        return ofertaService.listarTodos();
    }

    @PostMapping
    public Oferta criar(@RequestBody Oferta oferta){
        return ofertaService.salvar(oferta);
    }

    @GetMapping("/{id}")
    public Oferta buscarPorId(@PathVariable Long id){
        return ofertaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        ofertaService.deletar(id);
    }
}
