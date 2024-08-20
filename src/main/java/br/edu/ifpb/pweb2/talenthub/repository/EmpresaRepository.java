package br.edu.ifpb.pweb2.talenthub.repository;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
