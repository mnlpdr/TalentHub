package br.edu.ifpb.pweb2.talenthub.repository;

import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

// Imports necessários
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    // Encontra ofertas que possuem pelo menos uma das habilidades necessárias fornecidas.
    @Query("SELECT o FROM Oferta o WHERE EXISTS (SELECT 1 FROM o.habilidadesNecessarias h WHERE h IN :habilidades)")
    List<Oferta> findByHabilidadesNecessarias(@Param("habilidades") Set<String> habilidades);

    // Encontra ofertas que possuem pelo menos uma das habilidades desejáveis fornecidas.
    @Query("SELECT o FROM Oferta o WHERE EXISTS (SELECT 1 FROM o.habilidadesDesejaveis h WHERE h IN :habilidades)")
    List<Oferta> findByHabilidadesDesejaveis(@Param("habilidades") Set<String> habilidades);

    // Encontra ofertas associadas a uma empresa específica pelo ID da empresa.
    List<Oferta> findByEmpresaId(Long empresaId);

    // Encontra ofertas que correspondem a uma atividade principal específica.
    List<Oferta> findByAtividadePrincipal(String atividadePrincipal);

    // Encontra ofertas que contêm um pré-requisito específico em sua descrição.
    @Query("SELECT o FROM Oferta o WHERE o.preRequisitos LIKE %:preRequisitos%")
    List<Oferta> findByPreRequisitosContaining(@Param("preRequisitos") String preRequisitos);

    List<Oferta> findByValeTransporte(boolean valeTransporte);


}
