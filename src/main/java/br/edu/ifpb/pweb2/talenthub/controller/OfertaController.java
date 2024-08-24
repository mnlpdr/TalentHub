package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.service.OfertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @GetMapping("/cadastro")
    public String showOfertaForm(Model model){
        model.addAttribute("oferta", new Oferta());
        return "oferta/criarOferta";
    }
    @PostMapping("/cadastro")
    public String cadastrarOferta(@Valid @ModelAttribute Oferta oferta, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "oferta/criarOferta";
        }
        ofertaService.salvar(oferta);
        return "redirect:/ofertas/cadastro?success";
        }
    }

