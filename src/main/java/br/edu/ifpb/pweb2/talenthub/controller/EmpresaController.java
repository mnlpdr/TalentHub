package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public String listarTodos(Model model){
        model.addAttribute("empresas", empresaService.listarTodos());
        return "empresa/listarEmpresa";
    }

    @PostMapping
    @ResponseBody
    public Empresa criar(@RequestBody Empresa empresa){
        return empresaService.salvar(empresa);
    }

    @GetMapping("/{id}")
    public String buscarPorId(@PathVariable Long id, Model model){
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "empresa/cadastroEmpresa";

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletar(@PathVariable Long id){
        empresaService.excluir(id);
    }

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa/cadastroEmpresa";
    }

    @PostMapping("/cadastro")
    public String cadastrarEmpresa(@Valid @ModelAttribute Empresa empresa,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "cadastroEmpresa";
        }

        try {
            if (empresa.getDocumentoEnderecoFile() != null && !empresa.getDocumentoEnderecoFile().isEmpty()) {
                empresa.setDocumentoEndereco(empresa.getDocumentoEnderecoFile().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("fileError", "Erro ao processar o arquivo enviado.");
            return "cadastroEmpresa";
        }

        empresaService.salvar(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("detalhamento/{id}")
    public String detalhamento(@PathVariable Long id, Model model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "empresa/detalhamentoEmpresa";
    }


}
