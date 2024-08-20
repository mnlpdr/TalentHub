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

    // Método para criar um novo Coordenador
    @PostMapping
    public Coordenador criarCoordenador(@RequestBody Coordenador coordenador) {
        return coordenadorService.salvar(coordenador);
    }

    // Método para listar todos os Coordenadores
    @GetMapping
    public List<Coordenador> listarTodos() {
        return coordenadorService.listarTodos();
    }

    // Método para buscar um Coordenador pelo ID
    @GetMapping("/{id}")
    public Coordenador buscarCoordenadorPorId(@PathVariable Long id) {
        return coordenadorService.buscarPorId(id);
    }

    // Método para atualizar um Coordenador pelo ID
    @PutMapping("/{id}")
    public Coordenador atualizarCoordenador(@PathVariable Long id, @RequestBody Coordenador coordenador) {
        coordenador.setId(id);
        return coordenadorService.salvar(coordenador);
    }

    // Método para deletar um Coordenador pelo ID
    @DeleteMapping("/{id}")
    public void deletarCoordenador(@PathVariable Long id) {
        coordenadorService.deletar(id);
    }
}
