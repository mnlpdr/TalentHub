package br.edu.ifpb.pweb2.talenthub.utils;

public class CandidaturaDTO {
    private String nomeAluno;
    private String nomeEmpresa;
    private String nomeOferta;

    // Construtor esperado pelo Hibernate
    public CandidaturaDTO(String nomeAluno, String nomeEmpresa, String nomeOferta) {
        this.nomeAluno = nomeAluno;
        this.nomeEmpresa = nomeEmpresa;
        this.nomeOferta = nomeOferta;
    }

    // Getters e Setters
    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getNomeOferta() {
        return nomeOferta;
    }

    public void setNomeOferta(String nomeOferta) {
        this.nomeOferta = nomeOferta;
    }
}
