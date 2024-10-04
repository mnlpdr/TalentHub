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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
    public String candidatar(@RequestParam("oferta") Long ofertaId, Model model) {
        // Obter o aluno autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        // Buscar o aluno logado pelo username
        Aluno alunoLogado = alunoService.findByUsername(username);
        if (alunoLogado == null) {
            // Se o aluno não for encontrado, redirecionar para uma página de erro ou login
            return "redirect:/login";
        }

        // Buscar a oferta selecionada
        Oferta ofertaSelecionada = ofertaService.buscarPorId(ofertaId);
        if (ofertaSelecionada == null) {
            // Se a oferta não for encontrada, redirecionar para uma página de erro
            return "redirect:/error";
        }

        // Realizar a candidatura
        alunoService.candidatarAOferta(alunoLogado.getId(), ofertaSelecionada.getId());

        // Redirecionar para uma página de confirmação ou para a lista de candidaturas
        return "redirect:/alunos/detalhamento";
    }


    @GetMapping("/detalhamento")
    public String detalhamentoAlunoLogado(Model model) {
        // Recupera o Authentication do usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Extrai o nome de usuário (username) do Authentication
        String username = authentication.getName();
        
        // Busca o aluno usando o nome de usuário
        Aluno aluno = alunoService.findByUsername(username);
        
        // Adiciona o aluno no modelo para ser exibido na view
        model.addAttribute("aluno", aluno);
        
        // Retorna a view de detalhamento do aluno
        return "aluno/detalhamentoAluno";
    }

    @GetMapping("/detalhamento/{id}")
    public String detalhamentoPorId(@PathVariable Long id, Model model) {
        // Buscar o aluno usando o id
        Aluno aluno = alunoService.buscarPorId(id);
        model.addAttribute("aluno", aluno);
        return "aluno/detalhamentoAluno"; 
        
    }
}
