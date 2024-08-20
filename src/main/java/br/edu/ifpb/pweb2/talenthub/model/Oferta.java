package br.edu.ifpb.pweb2.talenthub.model;

import jakarta.persistence.*;
import java.util.Set;

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

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(String atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(int cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getValeTransporte() {
        return valeTransporte;
    }

    public void setValeTransporte(Boolean valeTransporte) {
        this.valeTransporte = valeTransporte;
    }

    public String getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(String preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public Set<String> getHabilidadesNecessarias() {
        return habilidadesNecessarias;
    }

    public void setHabilidadesNecessarias(Set<String> habilidadesNecessarias) {
        this.habilidadesNecessarias = habilidadesNecessarias;
    }

    public Set<String> getHabilidadesDesejaveis() {
        return habilidadesDesejaveis;
    }

    public void setHabilidadesDesejaveis(Set<String> habilidadesDesejaveis) {
        this.habilidadesDesejaveis = habilidadesDesejaveis;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<Estagio> getEstagios() {
        return estagios;
    }

    public void setEstagios(Set<Estagio> estagios) {
        this.estagios = estagios;
    }
}
