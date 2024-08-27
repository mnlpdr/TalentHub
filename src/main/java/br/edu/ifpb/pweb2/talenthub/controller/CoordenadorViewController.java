package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import br.edu.ifpb.pweb2.talenthub.utils.CandidaturaDTO;

import java.util.ArrayList;
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

    // Metodo para listar todas as ofertas de estágio
    @GetMapping("/candidaturas")
    public String listarOfertas(Model model) {
    // Obtém todos os alunos com suas ofertas candidaturas
    List<Aluno> alunos = alunoService.listarTodosComCandidatura();
    
    // Cria uma lista para armazenar a visão das candidaturas
    List<CandidaturaDTO> candidaturas = new ArrayList<>();
    
    for (Aluno aluno : alunos) {
        for (Oferta oferta : aluno.getOfertasCandidaturas()) {
            CandidaturaDTO candidatura = new CandidaturaDTO();
            candidatura.setNomeAluno(aluno.getNome());
            candidatura.setNomeEmpresa(oferta.getEmpresa().getNome());
            candidatura.setNomeOferta(oferta.getAtividadePrincipal());
            candidaturas.add(candidatura);
        }
    }
    model.addAttribute("candidaturas", candidaturas);
        return "coordenador/listarCandidaturas";
    }

    //Metodo para navegar para a página de login de coordenador
    @GetMapping("/login")
    public String mostrarPaginaDeLogin() {
        return "coordenador/loginCoordenador";
    }

}
