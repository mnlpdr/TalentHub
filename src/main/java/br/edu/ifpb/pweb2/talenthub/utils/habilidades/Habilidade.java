package br.edu.ifpb.pweb2.talenthub.utils.habilidades;

import lombok.Getter;

@Getter
public enum Habilidade {
    JAVA("Java"),
    SPRING_BOOT("Spring Boot"),
    GIT("Git"),
    AWS("AWS"),
    DOCKER("Docker"),
    SEO("SEO"),
    GOOGLE_ADS("Google Ads"),
    ANALISE_DE_DADOS("Análise de Dados"),
    DESIGN_GRAFICO("Design Gráfico"),
    COPYWRITING("Copywriting"),
    SCRUM("Scrum"),
    KANBAN("Kanban"),
    JIRA("JIRA"),
    GESTAO_DE_RISCOS("Gestão de Riscos"),
    COMUNICACAO("Comunicação");

    private String label;

    Habilidade(String label) {
        this.label = label;
    }
}
