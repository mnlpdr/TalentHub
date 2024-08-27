package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorViewController {

    @Autowired
    private OfertaService ofertaService;

    // Metodo para listar todas as ofertas de estágio
    @GetMapping("/candidaturas")
    public String listarOfertas(Model model) {
        List<Oferta> ofertas = ofertaService.listarTodos();
        model.addAttribute("ofertas", ofertas);
        return "coordenador/listarCandidaturas";
    }

    //Metodo para navegar para a página de login de coordenador
    @GetMapping("/login")
    public String mostrarPaginaDeLogin() {
        return "coordenador/loginCoordenador";
    }

}
