package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.repository.AlunoRepository;
import br.edu.ifpb.pweb2.talenthub.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public Oferta salvar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public Page<Oferta> listarTodos(Pageable pageable) {
        return ofertaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Oferta buscarPorId(Long id) {
        return ofertaRepository.findById(id).orElse(null);
    }

    public List<Oferta> buscarPorHabilidades(Set<String> habilidades) {
        return ofertaRepository.findByHabilidadesNecessarias(habilidades);
    }

    public List<Oferta> buscarPorCritério(String critério) {
        return ofertaRepository.findByPreRequisitosContaining(critério);
    }


    public void deletar(Long ofertaId) {
    Oferta oferta = ofertaRepository.findById(ofertaId).orElse(null);

    if (oferta != null) {
        // Primeiro, remova a oferta de todos os alunos que se candidataram a ela
        for (Aluno aluno : alunoRepository.findAll()) {
            aluno.getOfertasCandidaturas().remove(oferta);
            alunoRepository.save(aluno);
        }
        
        // Agora, exclua a oferta
        ofertaRepository.delete(oferta);
        
    }

    }

    @Transactional
    public List<Oferta> filtrarPorValeTransporte(boolean valeTransporte) {
        return ofertaRepository.findByValeTransporte(valeTransporte);
    }


}
