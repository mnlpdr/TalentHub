package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import br.edu.ifpb.pweb2.talenthub.model.Usuario;
import br.edu.ifpb.pweb2.talenthub.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.talenthub.repository.EstagioRepository;
import br.edu.ifpb.pweb2.talenthub.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EstagioRepository estagioRepository;


    @Autowired
    private EmpresaService empresaService;

    // LISTAGEM

    @GetMapping
    public String listarTodos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model
            ) {
        
        // Recupera o Authentication do usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o usuário logado tem o papel de "ROLE_EMPRESA"
        boolean isEmpresa = authentication.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equals("ROLE_EMPRESA"));

        if (isEmpresa) {
            // Retorna a view de detalhamento da empresa
            return "redirect:/empresas/detalhamento";
        }

        // Caso o usuário seja um Coordenador, faz a listagem de todas as empresas
        Pageable pageable = PageRequest.of(page, size);
        Page<Empresa> empresaPage = empresaService.listarTodos(pageable);

        model.addAttribute("empresas", empresaPage.getContent());
        model.addAttribute("totalPages", empresaPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "empresa/listarEmpresa";
    }

    @GetMapping("/detalhamento")
    public String detalhamentoAlunoLogado(Model model) {

        // Recupera o Authentication do usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extrai o nome de usuário (username) do Authentication
        String username = authentication.getName();

        // Busca a empresa associada ao CNPJ do usuário logado
        Empresa empresa = empresaService.findByCnpj(username);

        if (empresa == null) {
            throw new IllegalStateException("Erro");
        }

        // Adiciona a empresa no modelo para ser exibida na view
        model.addAttribute("empresa", empresa);

        // Retorna a view de detalhamento da empresa
        return "empresa/detalhamentoEmpresa";
    }


    @GetMapping("/{id}")
    public String buscarPorId(@PathVariable Long id, Model model){
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "empresa/cadastroEmpresa";

    }


    @GetMapping("/{id}/estagios")
    public String listarEstagiosPorEmpresa(@PathVariable Long id, Model model) {
        List<Estagio> estagios = empresaService.listarEstagiosPorEmpresa(id);
        model.addAttribute("estagios", estagios);
        return "empresa/listarEstagiosEmpresa";
    }

    


    // CADASTRO

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

        empresaService.cadastrar(empresa);
        return "redirect:/empresas";
    }

    // EDIÇÃO

    @GetMapping("/editar/{id}")
    public String editarEmpresa(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id);
        if (empresa != null) {
            model.addAttribute("empresa", empresa);
            return "empresa/cadastroEmpresa";
        } else {
            return "redirect:/empresas";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizarEmpresa(@PathVariable Long id, @Valid @ModelAttribute Empresa empresa, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "empresa/cadastroEmpresa";
        }

        try {

            empresa.setId(id);
            empresaService.atualizar(empresa);
        } catch (IllegalArgumentException e) {
            model.addAttribute("cnpjError", e.getMessage());
            return "empresa/cadastroEmpresa";
        }

        return "redirect:/empresas";
    }

    // DELETE

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletar(@PathVariable Long id) {
        empresaService.excluir(id);
    }



}
