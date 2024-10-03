package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.talenthub.repository.EstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private EstagioRepository estagioRepository;


    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Page<Empresa> listarTodos(Pageable pageable){
        return empresaRepository.findAll(pageable);
    }

    public Empresa buscarPorId(Long id){
        return empresaRepository.findById(id).orElse(null);
    }

    public void excluir(Long id){
        empresaRepository.deleteById(id);
    }

    public List<Estagio> listarEstagiosPorEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);

        if (empresa != null) {
            return estagioRepository.findByOferta_Empresa(empresa);
        }

        return new ArrayList<>();
    }



}
