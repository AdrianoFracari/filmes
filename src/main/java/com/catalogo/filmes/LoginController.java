package com.catalogo.filmes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

// Repositório
interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}

@RestController
@CrossOrigin(origins = "*") 
public class LoginController {

    @Autowired private UsuarioRepository repository;
    @Autowired private PasswordEncoder passwordEncoder;

    // --- REDIRECIONAMENTO ---
    @GetMapping("/")
    public RedirectView irParaLogin() {
        System.out.println("--> Alguém acessou a raiz. Redirecionando para login.html...");
        return new RedirectView("/login.html");
    }

    // 1. Criar Conta
    @PostMapping("/auth/register")
    public String registrar(@RequestBody Usuario usuario) {
        System.out.println("--> Tentativa de cadastro: " + usuario.getLogin());
        
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        
        repository.save(usuario);
        return "Usuário criado com sucesso!";
    }

    // 2. Fazer Login (RETORNA O USUÁRIO COMPLETO COM ID)
    @PostMapping("/auth/login")
    public Usuario login(@RequestBody Usuario usuario) {
        System.out.println("--> Tentativa de login: " + usuario.getLogin());

        Optional<Usuario> userBanco = repository.findByLogin(usuario.getLogin());

        if (userBanco.isPresent()) {
            if (passwordEncoder.matches(usuario.getSenha(), userBanco.get().getSenha())) {
                System.out.println("--> Login SUCESSO! ID: " + userBanco.get().getId());
                return userBanco.get(); // Retorna o usuário com ID, Email, etc.
            }
        }
        System.out.println("--> Login FALHOU");
        return null; // Retorna nulo se a senha estiver errada
    }
}