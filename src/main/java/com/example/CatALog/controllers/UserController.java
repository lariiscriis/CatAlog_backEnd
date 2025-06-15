package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.User;
import com.example.CatALog.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("Sucesso!");
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(user);
    }


}
