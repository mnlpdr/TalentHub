package br.edu.ifpb.pweb2.talenthub.service;

import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import br.edu.ifpb.pweb2.talenthub.repository.EstagioRepository;
import com.itextpdf.text.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import br.edu.ifpb.pweb2.talenthub.model.Aluno;
import br.edu.ifpb.pweb2.talenthub.model.Oferta;
import br.edu.ifpb.pweb2.talenthub.repository.OfertaRepository;

import java.time.LocalDate;


import javax.print.Doc;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.IOException;
import java.util.List;

@Service
public class EstagioService {
    @Autowired
    private EstagioRepository estagioRepository;
    @Autowired
    private OfertaRepository ofertaRepository;


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


    public byte[] gerarTermoDeEstagio(Long estagioId) throws DocumentException, IOException {
        Estagio estagio = estagioRepository.findById(estagioId).orElse(null);

        if (estagio != null) {
            // Criação do PDF
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("Termo de Estágio"));
            document.add(new Paragraph("Nome do Aluno: " + estagio.getAluno().getNome()));
            document.add(new Paragraph("Nome da Empresa: " + estagio.getOferta().getEmpresa().getNome()));
            document.add(new Paragraph("Data de Início: " + estagio.getDataInicio()));
            document.add(new Paragraph("Data de Término: " + estagio.getDataTermino()));
            document.add(new Paragraph("Valor: " + estagio.getValor()));

            document.close();

            return baos.toByteArray();
        }

        return null;
    }

    public void converterOfertaParaEstagio(Long ofertaId, List<Aluno> alunos, LocalDate dataInicio, LocalDate dataTermino, Double valor) {
        Oferta oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta não encontrada"));

        for (Aluno aluno : alunos) {
            Estagio estagio = new Estagio();
            estagio.setOferta(oferta);
            estagio.setAluno(aluno);
            estagio.setDataInicio(dataInicio);
            estagio.setDataTermino(dataTermino);
            estagio.setValor(valor);

            // Salvar o estágio
            estagioRepository.save(estagio);
        }
    }


}
