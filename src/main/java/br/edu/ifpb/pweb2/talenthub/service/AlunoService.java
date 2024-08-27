package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.repository.AlunoRepository;
import br.edu.ifpb.pweb2.talenthub.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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
    public void candidatarAOferta(Long alunoId, Long ofertaId) {
        Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
        Oferta oferta = ofertaRepository.findById(ofertaId).orElse(null);

        if (aluno != null && oferta != null) {
            aluno.getOfertasCandidaturas().add(oferta);
            alunoRepository.save(aluno);
        }
    }


    public List<Aluno> listarTodosComCandidatura() {
        // Recupera todos os alunos
        List<Aluno> alunos = alunoRepository.findAll();
        // Cria uma lista para armazenar os alunos com candidaturas
        List<Aluno> alunosComCandidaturas = new ArrayList<>();

        // Itera sobre a lista de alunos
        for (Aluno aluno : alunos) {
            // Verifica se o aluno possui pelo menos uma candidatura
            if (aluno.getOfertasCandidaturas() != null && !aluno.getOfertasCandidaturas().isEmpty()) {
                alunosComCandidaturas.add(aluno);
            }
        }

        return alunosComCandidaturas;
    }



}