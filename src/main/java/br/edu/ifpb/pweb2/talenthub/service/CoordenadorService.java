package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Coordenador;
import br.edu.ifpb.pweb2.talenthub.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public Coordenador salvar(Coordenador coordenador) {
        return coordenadorRepository.save(coordenador);
    }

    public List<Coordenador> listarTodos() {
        return coordenadorRepository.findAll();
    }

    public Coordenador buscarPorId(Long id) {
        return coordenadorRepository.findById(id).orElse(null);
    }

    public void deletar(Long id){
        coordenadorRepository.deleteById(id);
    }


}
