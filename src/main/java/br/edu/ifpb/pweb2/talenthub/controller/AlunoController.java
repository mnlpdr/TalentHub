package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Método para listar todos os alunos (API REST)
    @GetMapping()
    public String listarTodos(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "aluno/listarAluno";
    }

    // Método para buscar um aluno por ID
    @GetMapping("/{id}")
    public String buscarPorId(@PathVariable Long id, Model model){
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "aluno/cadastroAluno";
    }

    // Método para exibir o formulário de cadastro de aluno (Thymeleaf)
    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastroAluno";  // Nome do template Thymeleaf
    }

    // Método para processar o formulário de cadastro de aluno (Thymeleaf)
    @PostMapping("/cadastro")
    public String cadastrarAluno(@Valid @ModelAttribute Aluno aluno, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cadastroAluno";
        }
        alunoService.salvar(aluno);
        return "redirect:/alunos";
    }

    // Método para criar um novo aluno via API REST (espera JSON)
    @PostMapping("/api")
    @ResponseBody
    public Aluno criarViaApi(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }



}
