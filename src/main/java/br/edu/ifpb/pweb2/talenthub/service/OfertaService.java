package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;


    public Oferta salvar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> listarTodos(){
        return ofertaRepository.findAll();
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


    public void deletar(Long id) {
        ofertaRepository.deleteById(id);
    }

    @Transactional
    public List<Oferta> filtrarPorValeTransporte(boolean valeTransporte) {
        return ofertaRepository.findByValeTransporte(valeTransporte);
    }


}
