package com.catalogo.filmes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column; // Importar List
import jakarta.persistence.Entity; // Importante para evitar loop infinito
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String login;
    
    private String senha;
    private String email;
    private String telefone;

    @JsonIgnore 
    @OneToMany(mappedBy = "usuario") 
    private List<Filme> filmes;
}