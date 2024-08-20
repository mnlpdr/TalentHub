package br.edu.ifpb.pweb2.talenthub.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String senha;

    // Exemplo de habilidades (isso pode ser ajustado conforme a necessidade)
    @ElementCollection
    private Set<String> habilidades;

    @OneToMany(mappedBy = "aluno")
    private Set<Estagio> estagios;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Set<String> habilidades) {
        this.habilidades = habilidades;
    }

    public Set<Estagio> getEstagios() {
        return estagios;
    }

    public void setEstagios(Set<Estagio> estagios) {
        this.estagios = estagios;
    }
}
