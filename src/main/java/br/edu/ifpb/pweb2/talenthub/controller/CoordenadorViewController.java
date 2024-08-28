package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import br.edu.ifpb.pweb2.talenthub.utils.CandidaturaDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorViewController {

    @Autowired
    private AlunoService alunoService;

    // Método para listar todas as candidaturas
    @GetMapping("/candidaturas")
    public String listarOfertas(Model model) {
        List<CandidaturaDTO> candidaturas = alunoService.listarCandidaturas();
        model.addAttribute("candidaturas", candidaturas);
        return "coordenador/listarCandidaturas";
    }

    // Método para navegar para a página de login de coordenador
    @GetMapping("/login")
    public String mostrarPaginaDeLogin() {
        return "coordenador/loginCoordenador";
    }
}
