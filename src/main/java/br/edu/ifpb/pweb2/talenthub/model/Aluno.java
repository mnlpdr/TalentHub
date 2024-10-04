package br.edu.ifpb.pweb2.talenthub.model;

import br.edu.ifpb.pweb2.talenthub.utils.habilidades.Habilidade;
import java.util.HashSet;
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

    // Coleção de habilidades como um Set de Habilidade
    @ElementCollection(targetClass = Habilidade.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "aluno_habilidades")
    @Column(name = "habilidade")
    private Set<Habilidade> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "aluno")
    private Set<Estagio> estagios;

    @ManyToMany
    @JoinTable(
            name = "aluno_oferta",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "oferta_id")
    )
    private Set<Oferta> ofertasCandidaturas = new HashSet<>();

    public Aluno orElse(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }
}
