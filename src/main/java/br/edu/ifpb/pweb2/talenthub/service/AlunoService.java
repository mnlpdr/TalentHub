package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Autoridade;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.model.Usuario;
import br.edu.ifpb.pweb2.talenthub.repository.AlunoRepository;
import br.edu.ifpb.pweb2.talenthub.repository.AutoridadeRepository;
import br.edu.ifpb.pweb2.talenthub.repository.OfertaRepository;
import br.edu.ifpb.pweb2.talenthub.repository.UsuarioRepository;
import br.edu.ifpb.pweb2.talenthub.utils.DTO.CandidaturaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutoridadeRepository autoridadeRepository;
    
    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Aluno salvar(Aluno aluno) {
        // Cria o Aluno e salva no banco
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha())); // Encripta a senha
        Aluno novoAluno = alunoRepository.save(aluno);

        // Cria um novo usuário para o login
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(aluno.getUsername());
        novoUsuario.setPassword(aluno.getSenha()); // Senha já encriptada
        novoUsuario.setEnabled(true);

        // Salva o novo usuário
        usuarioRepository.save(novoUsuario);

        // Defina o papel (ROLE) como "ALUNO"
        Autoridade autoridade = new Autoridade();

        // Criar o ID embutido da autoridade (AuthorityId)
        Autoridade.AuthorityId authorityId = new Autoridade.AuthorityId(novoUsuario.getUsername(), "ROLE_ALUNO");
        autoridade.setId(authorityId); // Associa o ID
        autoridade.setUsername(novoUsuario); // Associa ao novo usuário
        autoridade.setAuthority("ROLE_ALUNO");

        // Salva a autoridade
        autoridadeRepository.save(autoridade);

        return novoAluno;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id){
        return alunoRepository.findById(id).orElse(null);
    }

    public Aluno findByUsername(String username) {
        return alunoRepository.findByUsername(username).orElse(null);
    }

    public void deletar(Long id) {
        alunoRepository.deleteById(id);
    }

    // Adicionando uma oferta à lista de candidaturas de um aluno e salvando as alterações.
    @Transactional
    public void candidatarAOferta(Long alunoId, Long ofertaId) {
        Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
        Oferta oferta = ofertaRepository.findById(ofertaId).orElse(null);

        if (aluno != null && oferta != null) {
            aluno.getOfertasCandidaturas().add(oferta);
            alunoRepository.save(aluno);
        }
    }

    // Novo método para listar candidaturas sem carregar o LOB
    public List<CandidaturaDTO> listarCandidaturas() {
        return alunoRepository.listarCandidaturas();
    }
}
