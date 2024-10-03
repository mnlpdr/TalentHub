package br.edu.ifpb.pweb2.talenthub.repository;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findByCnpj(String cnpj);

    @Query("SELECT e FROM Empresa e JOIN FETCH e.ofertas WHERE e.id = :id")
    Optional<Empresa> findByIdWithoutLob(@Param("id") Long id);

}
