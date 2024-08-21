package br.edu.ifpb.pweb2.talenthub.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
