package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import br.edu.ifpb.pweb2.talenthub.service.OfertaService;
import br.edu.ifpb.pweb2.talenthub.utils.habilidades.Habilidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private OfertaService ofertaService;

    @GetMapping()
    public String listarTodos(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "aluno/listarAluno";
    }

    @GetMapping("/{id}")
    public String buscarPorId(@PathVariable Long id, Model model){
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "aluno/cadastroAluno";
    }

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("aluno", new Aluno());

        // Lista de habilidades pré-determinadas usando a enumeração Habilidade
        List<Habilidade> habilidades = Arrays.asList(Habilidade.values());
        model.addAttribute("habilidades", habilidades);

        return "aluno/cadastroAluno";  // Nome do template Thymeleaf
    }

    @PostMapping("/cadastro")
    public String cadastrarAluno(@Valid @ModelAttribute Aluno aluno, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Habilidade> habilidades = Arrays.asList(Habilidade.values());
            model.addAttribute("habilidades", habilidades);
            return "aluno/cadastroAluno";
        }
        alunoService.salvar(aluno);
        return "redirect:/alunos";
    }

    @PostMapping("/api")
    @ResponseBody
    public Aluno criarViaApi(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @GetMapping("/candidatura")
    public String showCandidatura(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);  // Define a paginação para as ofertas

        model.addAttribute("alunos", alunoService.listarTodos());

        // Atualizando para usar paginação nas ofertas
        Page<Oferta> ofertaPage = ofertaService.listarTodos(pageable);
        model.addAttribute("ofertas", ofertaPage.getContent());

        return "aluno/formCandidatura";
    }

    @PostMapping("/candidatura")
    public String cadastrarCandidatura(@RequestParam("aluno") Long alunoId,
                                       @RequestParam("oferta") Long ofertaId,
                                       Model model) {
        alunoService.candidatarAOferta(alunoId, ofertaId);
        return "redirect:/alunos";
    }

    @GetMapping("detalhamento/{id}")
    public String detalhamento(@PathVariable Long id, Model model){
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "aluno/detalhamentoAluno";
    }
}
