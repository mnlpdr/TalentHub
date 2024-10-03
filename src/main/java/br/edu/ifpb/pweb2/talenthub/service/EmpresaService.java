package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.talenthub.repository.EstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifpb.pweb2.talenthub.model.Estagio;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private EstagioRepository estagioRepository;


    public Empresa salvar(Empresa empresa) {
        // Verifica se estamos tentando editar (ID não nulo)
        if (empresa.getId() != null) {
            // Busca a empresa existente
            Empresa empresaExistente = empresaRepository.findById(empresa.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada para o ID fornecido"));

            // Verifica se o CNPJ já existe em outra empresa
            Empresa empresaComMesmoCnpj = empresaRepository.findByCnpj(empresa.getCnpj());
            if (empresaComMesmoCnpj != null && !empresaComMesmoCnpj.getId().equals(empresa.getId())) {
                throw new IllegalArgumentException("O CNPJ já está em uso por outra empresa.");
            }

            // Atualiza os dados da empresa existente
            empresaExistente.setNome(empresa.getNome());
            empresaExistente.setCnpj(empresa.getCnpj());
            empresaExistente.setEndereco(empresa.getEndereco());
            empresaExistente.setTelefone(empresa.getTelefone());
            empresaExistente.setEmail(empresa.getEmail());
            empresaExistente.setAtividadePrincipal(empresa.getAtividadePrincipal());
            empresaExistente.setPessoaContato(empresa.getPessoaContato());
            empresaExistente.setUrl(empresa.getUrl());

            // Se houver documento, atualiza
            if (empresa.getDocumentoEndereco() != null) {
                empresaExistente.setDocumentoEndereco(empresa.getDocumentoEndereco());
            }

            return empresaRepository.save(empresaExistente);  // Atualiza no banco
        }

        // Caso seja uma nova empresa (sem ID)
        return empresaRepository.save(empresa);  // Insere nova empresa
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

    public List<Estagio> listarEstagiosPorEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);

        if (empresa != null) {
            return estagioRepository.findByOferta_Empresa(empresa);
        }

        return new ArrayList<>();
    }



}
