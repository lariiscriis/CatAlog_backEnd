package com.example.CatALog.infra.security;

import com.example.CatALog.domain.user.User;
import com.example.CatALog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//classe para o security consultar os usuarios
@Component
public class CustomUserDetailsService implements UserDetailsService {//implementação da interface UserDetailsService que vem do spring security
    @Autowired
    private UserRepository userRepository;//dependencia da classe

    @Override //implementando metodo da interface
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
