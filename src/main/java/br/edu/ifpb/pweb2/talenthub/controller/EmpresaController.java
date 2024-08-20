package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> listarTotos(){
        return empresaService.listarTodos();
    }

    @PostMapping
    public Empresa criar(@RequestBody Empresa empresa){
        return empresaService.salvar(empresa);
    }

    @GetMapping("/{id}")
    public Empresa buscarPorId(@PathVariable Long id){
        return empresaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        empresaService.excluir(id);
    }

}
