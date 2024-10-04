package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Autoridade;
import br.edu.ifpb.pweb2.talenthub.model.Empresa;
import br.edu.ifpb.pweb2.talenthub.repository.AutoridadeRepository;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutoridadeRepository autoridadeRepository;

    @Autowired
    private EstagioRepository estagioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Empresa atualizar(Empresa empresa) {
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


        return empresaRepository.save(empresa);
    }

    public Empresa cadastrar(Empresa empresa) {
        // Cria o Aluno e salva no banco
        empresa.setSenha(passwordEncoder.encode(empresa.getSenha())); // Encripta a senha
        Empresa novaEmpresa = empresaRepository.save(empresa);

        // Cria um novo usuário para o login
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(empresa.getCnpj());
        novoUsuario.setPassword(empresa.getSenha()); // Senha já encriptada
        novoUsuario.setEnabled(true);

        // Salva o novo usuário
        usuarioRepository.save(novoUsuario);

        // Defina o papel (ROLE) como "EMPRESA"
        Autoridade autoridade = new Autoridade();

        // Criar o ID embutido da autoridade (AuthorityId)
        Autoridade.AuthorityId authorityId = new Autoridade.AuthorityId(novoUsuario.getUsername(), "ROLE_EMPRESA");
        autoridade.setId(authorityId); // Associa o ID
        autoridade.setUsername(novoUsuario); // Associa ao novo usuário
        autoridade.setAuthority("ROLE_EMPRESA");

        // Salva a autoridade
        autoridadeRepository.save(autoridade);

        return novaEmpresa;
    }

    public Page<Empresa> listarTodos(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        empresaRepository.deleteById(id);
    }

    public List<Estagio> listarEstagiosPorEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);

        if (empresa != null) {
            return estagioRepository.findByOferta_Empresa(empresa);
        }

        return new ArrayList<>();
    }

    public Empresa findByCnpj(String username) {
        return empresaRepository.findByCnpj(username);
    }

    

}
