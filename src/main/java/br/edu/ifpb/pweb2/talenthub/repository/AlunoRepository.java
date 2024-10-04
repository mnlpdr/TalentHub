package br.edu.ifpb.pweb2.talenthub.repository;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.utils.DTO.CandidaturaDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    //Aqui, podemos implementar métodos personalizados

    @Query("SELECT new br.edu.ifpb.pweb2.talenthub.utils.DTO.CandidaturaDTO(a.nome, a.id, e.nome, e.id, o.atividadePrincipal, o.id) "
            +
            "FROM Aluno a " +
            "JOIN a.ofertasCandidaturas o " +
            "JOIN o.empresa e")
    List<CandidaturaDTO> listarCandidaturas();


    Aluno findByUsername(String username);
}
