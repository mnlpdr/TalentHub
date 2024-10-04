package br.edu.ifpb.pweb2.talenthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.talenthub.model.Autoridade;

public interface AutoridadeRepository extends JpaRepository<Autoridade, Autoridade.AuthorityId> {
}
