package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.service.AlunoService;
import br.edu.ifpb.pweb2.talenthub.service.EmpresaService;
import br.edu.ifpb.pweb2.talenthub.service.OfertaService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/povoamento")
public class PovoamentoController {

    @Autowired
    private EmpresaService empresaService; 

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/salvar")
    @ResponseBody
    public String salvar() {
        try{
            // Empresa 1
            Empresa empresa1 = new Empresa();
            empresa1.setNome("Alpha Tech");
            empresa1.setCnpj("12345623901234");
            empresa1.setEndereco("Rua Alpha, 100");
            empresa1.setTelefone("11987654321");
            empresa1.setEmail("contato@alphatech.com");
            empresa1.setAtividadePrincipal("Desenvolvimento de Software");
            empresa1.setPessoaContato("João Silva");
            empresa1.setUrl("http://alphatech.com");

            empresaService.salvar(empresa1);

            // Empresa 2
            Empresa empresa2 = new Empresa();
            empresa2.setNome("Beta Solutions");
            empresa2.setCnpj("98765432109876");
            empresa2.setEndereco("Avenida Beta, 200");
            empresa2.setTelefone("21987654321");
            empresa2.setEmail("contato@betasolutions.com");
            empresa2.setAtividadePrincipal("Consultoria em TI");
            empresa2.setPessoaContato("Maria Souza");
            empresa2.setUrl("http://betasolutions.com");

            empresaService.salvar(empresa2);

            // Empresa 3
            Empresa empresa3 = new Empresa();
            empresa3.setNome("Gamma Enterprises");
            empresa3.setCnpj("11223344556677");
            empresa3.setEndereco("Praça Gamma, 300");
            empresa3.setTelefone("31987654321");
            empresa3.setEmail("contato@gammaenterprises.com");
            empresa3.setAtividadePrincipal("Serviços de Infraestrutura");
            empresa3.setPessoaContato("Carlos Mendes");
            empresa3.setUrl("http://gammaenterprises.com");

            empresaService.salvar(empresa3);

            Oferta oferta1 = new Oferta();
            oferta1.setAtividadePrincipal("Desenvolvimento de Software");
            oferta1.setCargaHorariaSemanal(30);
            oferta1.setValor(1500.00);
            oferta1.setValeTransporte(true);
            oferta1.setPreRequisitos("Conhecimento em Java e Spring Boot");
            oferta1.setHabilidadesNecessarias(Set.of("Java", "Spring Boot", "Git"));
            oferta1.setHabilidadesDesejaveis(Set.of("AWS", "Docker"));
            oferta1.setEmpresa(empresa1);

            Oferta oferta2 = new Oferta();
            oferta2.setAtividadePrincipal("Marketing Digital");
            oferta2.setCargaHorariaSemanal(25);
            oferta2.setValor(1200.00);
            oferta2.setValeTransporte(false);
            oferta2.setPreRequisitos("Conhecimento em SEO e Google Ads");
            oferta2.setHabilidadesNecessarias(Set.of("SEO", "Google Ads", "Análise de Dados"));
            oferta2.setHabilidadesDesejaveis(Set.of("Design Gráfico", "Copywriting"));
            oferta2.setEmpresa(empresa2);

            Oferta oferta3 = new Oferta();
            oferta3.setAtividadePrincipal("Gestão de Projetos");
            oferta3.setCargaHorariaSemanal(20);
            oferta3.setValor(1800.00);
            oferta3.setValeTransporte(true);
            oferta3.setPreRequisitos("Conhecimento em Metodologias Ágeis");
            oferta3.setHabilidadesNecessarias(Set.of("Scrum", "Kanban", "JIRA"));
            oferta3.setHabilidadesDesejaveis(Set.of("Gestão de Riscos", "Comunicação"));
            oferta3.setEmpresa(empresa3);
            
            ofertaService.salvar(oferta1);
            ofertaService.salvar(oferta2);
            ofertaService.salvar(oferta3);

            Aluno aluno1 = new Aluno();
            aluno1.setNome("João Silva");
            aluno1.setUsername("joaosilva");
            aluno1.setSenha("senha123");
            aluno1.setHabilidades(Set.of("Java", "Spring Boot", "SQL"));
            aluno1.setOfertasCandidaturas(Set.of(oferta1, oferta2)); // Candidatura para oferta1 e oferta2

            Aluno aluno2 = new Aluno();
            aluno2.setNome("Maria Oliveira");
            aluno2.setUsername("mariaoliveira");
            aluno2.setSenha("senha456");
            aluno2.setHabilidades(Set.of("SEO", "Google Ads", "Análise de Dados"));
            aluno2.setOfertasCandidaturas(Set.of(oferta2, oferta3)); // Candidatura para oferta2 e oferta3

            Aluno aluno3 = new Aluno();
            aluno3.setNome("Pedro Santos");
            aluno3.setUsername("pedrosantos");
            aluno3.setSenha("senha789");
            aluno3.setHabilidades(Set.of("Scrum", "Kanban", "JIRA"));
            aluno3.setOfertasCandidaturas(Set.of(oferta1, oferta3)); // Candidatura para oferta1 e oferta3

            alunoService.salvar(aluno1);
            alunoService.salvar(aluno2);
            alunoService.salvar(aluno3);

            return "Empresas, ofertas e alunos inseridos!";   
        } catch (Exception e) {
            return "Ja cadastrados";        }
    }
}
