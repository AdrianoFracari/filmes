package com.catalogo.filmes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filmes")
@CrossOrigin(origins = "*")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;

    // URL: http://localhost:8081
    @GetMapping("/todos")
    public List<Filme> listarTodos() {
        return repository.findAll();
    }

    @GetMapping
    public List<Filme> listarPorUsuario(@RequestParam String login) {
        return repository.findByUsuarioLogin(login);
    }

    @PostMapping
    public Filme salvar(@RequestBody Filme filme) {
        return repository.save(filme);
    }

    @PutMapping("/{id}")
    public Filme atualizar(@PathVariable Long id, @RequestBody Filme dados) {
        return repository.findById(id).map(filme -> {
            filme.setTitulo(dados.getTitulo());
            filme.setDiretor(dados.getDiretor());
            filme.setAnoLancamento(dados.getAnoLancamento());
            filme.setNota(dados.getNota());
            return repository.save(filme);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}