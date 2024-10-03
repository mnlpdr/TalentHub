package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import br.edu.ifpb.pweb2.talenthub.service.EmpresaService;
import br.edu.ifpb.pweb2.talenthub.service.OfertaService;
import br.edu.ifpb.pweb2.talenthub.utils.habilidades.Habilidade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {


    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public String listarTodos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        // Cria o objeto Pageable
        Pageable pageable = PageRequest.of(page, size);

        // Chama o serviço com o Pageable
        Page<Oferta> ofertaPage = ofertaService.listarTodos(pageable);

        // Adiciona os dados ao modelo
        model.addAttribute("ofertas", ofertaPage.getContent());
        model.addAttribute("totalPages", ofertaPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "oferta/listarOferta";
    }

    @PostMapping
    public Oferta criar(@RequestBody Oferta oferta){
        return ofertaService.salvar(oferta);
    }

    @GetMapping("/{id}")
    public Oferta buscarPorId(@PathVariable Long id){
        return ofertaService.buscarPorId(id);
    }
    @GetMapping("/listar")
    public ModelAndView listarOfertas(ModelAndView model) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Oferta> ofertas = ofertaService.listarTodos(pageable).getContent();
        model.addObject("ofertas", ofertas);
        model.setViewName("oferta/listarOferta");
        return model;
    }

    @RequestMapping("/{id}/delete")
    public ModelAndView deletar(@PathVariable Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        ofertaService.deletar(id);
        Pageable pageable = PageRequest.of(0, 10);
        model.addObject("ofertas", ofertaService.listarTodos(pageable).getContent());
        model.addObject("oferta", new Oferta());
        model.setViewName("redirect:/ofertas/listar");
        redirectAttributes.addFlashAttribute("oferta", "Deletado.");
        return model;
    }

    @GetMapping("/cadastro")
    public ModelAndView showOfertaForm(ModelAndView model){
        model.setViewName("oferta/criarOferta");
        model.addObject("oferta", new Oferta());
        Pageable pageable = PageRequest.of(0, 10);
        model.addObject("empresas", empresaService.listarTodos(pageable).getContent());
        model.addObject("habilidades", Habilidade.values()); // Passa o enum para o template
        return model;
    }


    @PostMapping("/cadastro")
    public String cadastrarOferta(@Valid @ModelAttribute Oferta oferta, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "oferta/criarOferta";
        }
        ofertaService.salvar(oferta);
        return "redirect:/ofertas/listar";
        }

    @GetMapping("/filtrar")
    public String filtrarOfertas(
            @RequestParam(value = "valeTransporte", required = false) Boolean valeTransporte,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        List<Oferta> ofertasFiltradas;

        if (valeTransporte == null) {
            ofertasFiltradas = ofertaService.listarTodos(pageable).getContent();
        } else {
            ofertasFiltradas = ofertaService.filtrarPorValeTransporte(valeTransporte);
        }

        model.addAttribute("ofertas", ofertasFiltradas);
        model.addAttribute("alunos", alunoService.listarTodos());
        return "aluno/formCandidatura";
    }

    @GetMapping("detalhamento/{id}")
    public String detalhamento(@PathVariable Long id, Model model) {
        model.addAttribute("oferta", ofertaService.buscarPorId(id));
        return "oferta/detalhamentoOferta";
    }


}

