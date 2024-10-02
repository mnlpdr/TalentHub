package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Estagio;
import br.edu.ifpb.pweb2.talenthub.service.EstagioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/estagios")
public class EstagioController {

    @Autowired
    private EstagioService estagioService;

    @GetMapping
    public List<Estagio> listarTodos(){
        return estagioService.listarTodos();
    }

    @PostMapping
    public Estagio criar(@RequestBody Estagio estagio){
        return estagioService.salvar(estagio);
    }

    @GetMapping("/{id}")
    public Estagio buscarPorId(@PathVariable Long id){
        return estagioService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        estagioService.deletar(id);
    }


    @GetMapping("/{id}/termo")
    public ResponseEntity<byte[]> downloadTermoDeEstagio(@PathVariable Long id) throws DocumentException, IOException {
        byte[] termoPdf = estagioService.gerarTermoDeEstagio(id);

        if (termoPdf != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=termo_estagio_" + id + ".pdf");

            return new ResponseEntity<>(termoPdf, headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
