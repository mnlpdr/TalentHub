package br.edu.ifpb.pweb2.talenthub.controller;

import br.edu.ifpb.pweb2.talenthub.model.Coordenador;
import br.edu.ifpb.pweb2.talenthub.service.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @PostMapping
    public Coordenador criarCoordenador(@RequestBody Coordenador coordenador) {
        return coordenadorService.salvar(coordenador);
    }

    @GetMapping
    public List<Coordenador> listarTodos() {
        return coordenadorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Coordenador buscarCoordenadorPorId(@PathVariable Long id) {
        return coordenadorService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Coordenador atualizarCoordenador(@PathVariable Long id, @RequestBody Coordenador coordenador) {
        coordenador.setId(id);
        return coordenadorService.salvar(coordenador);
    }

    @DeleteMapping("/{id}")
    public void deletarCoordenador(@PathVariable Long id) {
        coordenadorService.deletar(id);
    }
}
