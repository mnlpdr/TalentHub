package br.edu.ifpb.pweb2.talenthub.repository;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    //Aqui, podemos implementar métodos personalizados
}
