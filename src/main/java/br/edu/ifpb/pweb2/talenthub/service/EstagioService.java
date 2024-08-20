package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import br.edu.ifpb.pweb2.talenthub.repository.EstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstagioService {
    @Autowired
    private EstagioRepository estagioRepository;

    public Estagio salvar(Estagio estagio) {
        return estagioRepository.save(estagio);
    }

    public List<Estagio> listarTodos() {
        return estagioRepository.findAll();
    }

    public Estagio buscarPorId(Long id) {
        return estagioRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        estagioRepository.deleteById(id);
    }

}
