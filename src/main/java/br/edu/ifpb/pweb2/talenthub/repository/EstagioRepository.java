package br.edu.ifpb.pweb2.talenthub.repository;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstagioRepository extends JpaRepository<Estagio, Long> {
    List<Estagio> findByOferta_Empresa(Empresa empresa);

}
