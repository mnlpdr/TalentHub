package br.edu.ifpb.pweb2.talenthub.repository;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.utils.CandidaturaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    //Aqui, podemos implementar métodos personalizados

    @Query("SELECT new br.edu.ifpb.pweb2.talenthub.utils.CandidaturaDTO(a.nome, e.nome, o.atividadePrincipal) " +
            "FROM Aluno a " +
            "JOIN a.ofertasCandidaturas o " +
            "JOIN o.empresa e")
    List<CandidaturaDTO> listarCandidaturas();

}
