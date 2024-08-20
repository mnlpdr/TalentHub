package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Oferta buscarPorId(Long id) {
        return ofertaRepository.findById(id).orElse(null);
    }
    public void deletar(Long id) {
        ofertaRepository.deleteById(id);
    }

}
