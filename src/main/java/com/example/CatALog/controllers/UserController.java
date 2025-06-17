package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.User;
import com.example.CatALog.dto.AtualizarUsuarioDTO;
import com.example.CatALog.repositories.UserRepository;
import com.example.CatALog.service.ImgurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImgurService imgurService;
    private UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, ImgurService imgurService) {
        this.userRepository = userRepository;
        this.imgurService =  imgurService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarPerfilComFoto(
        @PathVariable String email,
        @RequestPart("dados") String dadosJson,
        @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
        @RequestPart(value = "fotoBackground", required = false) MultipartFile fotoBackground
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AtualizarUsuarioDTO dados = mapper.readValue(dadosJson, AtualizarUsuarioDTO.class);
        User user = userRepository.findByEmail(email).orElseThrow();

        user.setName(dados.getNome());
        user.setEmail(dados.getEmail());
        if (dados.getSenha() != null && !dados.getSenha().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dados.getSenha()));
        }
        user.setBio(dados.getBio());

        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            String urlPerfil = imgurService.uploadImage(fotoPerfil);
            user.setFotoPerfil(urlPerfil);
        }

        if (fotoBackground != null && !fotoBackground.isEmpty()) {
            String urlBackground = imgurService.uploadImage(fotoBackground);
            user.setFotoBackground(urlBackground);
        }

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


