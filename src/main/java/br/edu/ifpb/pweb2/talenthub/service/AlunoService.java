package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.repository.AlunoRepository;
import br.edu.ifpb.pweb2.talenthub.repository.OfertaRepository;
import br.edu.ifpb.pweb2.talenthub.utils.CandidaturaDTO;
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
    private OfertaRepository ofertaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Aluno salvar(Aluno aluno) {
        // Criptografa a senha antes de salvar
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id){
        return alunoRepository.findById(id).orElse(null);
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
