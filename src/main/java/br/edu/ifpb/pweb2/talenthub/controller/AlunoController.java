package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Método para listar todos os alunos (API REST)
    @GetMapping
    @ResponseBody
    public List<Aluno> listarTodos(){
        return alunoService.listarTodos();
    }

    // Método para criar um novo aluno via API REST (espera JSON)
    @PostMapping("/api")
    @ResponseBody
    public Aluno criarViaApi(@RequestBody Aluno aluno){
        return alunoService.salvar(aluno);
    }

    // Método para buscar um aluno por ID (API REST)
    @GetMapping("/{id}")
    @ResponseBody
    public Aluno buscarPorId(@PathVariable Long id){
        return alunoService.buscarPorId(id);
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
        return "redirect:/alunos/cadastro?success";
    }
}
