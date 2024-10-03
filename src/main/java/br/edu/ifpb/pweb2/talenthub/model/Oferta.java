package br.edu.ifpb.pweb2.talenthub.model;

import jakarta.persistence.*;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String atividadePrincipal;

    private int cargaHorariaSemanal;

    private Double valor;

    private Boolean valeTransporte;

    private String preRequisitos;

    @ElementCollection
    private Set<String> habilidadesNecessarias;

    @ElementCollection
    private Set<String> habilidadesDesejaveis;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "oferta")
    private Set<Estagio> estagios;

    @ManyToMany
    @JoinTable(
            name = "oferta_candidato",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id"))
    private Set<Aluno> candidatos;


}
