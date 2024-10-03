package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Autoridade;
import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.talenthub.repository.EstagioRepository;
import br.edu.ifpb.pweb2.talenthub.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import br.edu.ifpb.pweb2.talenthub.model.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private EstagioRepository estagioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Empresa salvar(Empresa empresa) {
        if (empresa.getId() != null) {
            Empresa empresaExistente = empresaRepository.findById(empresa.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada para o ID fornecido"));

            Empresa empresaComMesmoCnpj = empresaRepository.findByCnpj(empresa.getCnpj());
            if (empresaComMesmoCnpj != null && !empresaComMesmoCnpj.getId().equals(empresa.getId())) {
                throw new IllegalArgumentException("O CNPJ já está em uso por outra empresa.");
            }

            empresaExistente.setNome(empresa.getNome());
            empresaExistente.setCnpj(empresa.getCnpj());
            empresaExistente.setEndereco(empresa.getEndereco());
            empresaExistente.setTelefone(empresa.getTelefone());
            empresaExistente.setEmail(empresa.getEmail());
            empresaExistente.setAtividadePrincipal(empresa.getAtividadePrincipal());
            empresaExistente.setPessoaContato(empresa.getPessoaContato());
            empresaExistente.setUrl(empresa.getUrl());

            if (empresa.getDocumentoEndereco() != null) {
                empresaExistente.setDocumentoEndereco(empresa.getDocumentoEndereco());
            }

            return empresaRepository.save(empresaExistente);
        }

       /*  // Cria um novo usuário para o login
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(a.getUsername());
        novoUsuario.setPassword(aluno.getSenha()); // Senha já encriptada
        novoUsuario.setEnabled(true);

        // Defina o papel (ROLE) como "ALUNO"
        Autoridade autoridade = new Autoridade();
        autoridade.setUsername(novoUsuario);
        autoridade.setAuthority("ALUNO");

        novoUsuario.setAuthorities(List.of(autoridade));
        usuarioRepository.save(novoUsuario); */

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

    public void deletarEmpresas(List<Long> empresaIds) {
        // Busca todas as empresas que existem no banco de dados com os IDs fornecidos
        List<Empresa> empresasParaDeletar = empresaRepository.findAllById(empresaIds);

        // Verifica se todas as empresas foram encontradas
        if (empresasParaDeletar.size() != empresaIds.size()) {
            throw new IllegalArgumentException("Uma ou mais empresas não foram encontradas.");
        }

        // Realiza a deleção em lote
        empresaRepository.deleteAll(empresasParaDeletar);
    }



}
