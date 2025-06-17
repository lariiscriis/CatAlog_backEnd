package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.User;
import com.example.CatALog.dto.AtualizarUsuarioDTO;
import com.example.CatALog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(user);
    }

    @Value("${upload.directory:./uploads}")
    private String uploadDirectory;
    @PutMapping( "/{email}")
    public ResponseEntity<?> atualizarPerfil(
            @PathVariable String email,
            @RequestBody AtualizarUsuarioDTO dados)
       {

           User user = userRepository.findByEmail(email).orElseThrow();

           user.setName(dados.getNome());
           user.setEmail(dados.getEmail());
           if (dados.getSenha() != null && !dados.getSenha().isEmpty()) {
               user.setPassword(passwordEncoder.encode(dados.getSenha())); // HASHEAR A NOVA SENHA
           }
           user.setFotoPerfil(dados.getFotoPerfil());
           user.setFotoBackground(dados.getFotoBackground());
           user.setBio(dados.getBio());

        userRepository.save(user);
        return ResponseEntity.ok(user);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletaUser(@PathVariable String id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok("Usuario deletado");
        }).orElse(ResponseEntity.notFound().build());
    }
}


