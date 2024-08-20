package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public List<Aluno> listarTodos(){
        return alunoService.listarTodos();
    }

    @PostMapping
    public Aluno criar(@RequestBody Aluno aluno){
        return alunoService.salvar(aluno);
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id){
        return alunoService.buscarPorId(id);
    }
}
