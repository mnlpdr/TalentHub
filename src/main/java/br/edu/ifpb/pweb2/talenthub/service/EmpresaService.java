package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Empresa> listarTodos(){
        return empresaRepository.findAll();
    }

    public Empresa buscarPorId(Long id){
        return empresaRepository.findById(id).orElse(null);
    }

    public void excluir(Long id){
        empresaRepository.deleteById(id);
    }

}
