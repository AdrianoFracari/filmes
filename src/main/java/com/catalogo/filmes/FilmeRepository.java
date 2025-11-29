package com.catalogo.filmes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByUsuarioLogin(String login);
}