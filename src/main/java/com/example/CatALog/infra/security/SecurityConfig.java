package com.example.CatALog.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//classe para configurar o security
@Configuration //anotacoes para indicar que ela é uma classe de configuração e deve ser carregada primeiro
@EnableWebSecurity// ativando o websecurity do springsecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //aplicações rest são STATELESS(não guardar o estado), o usuario precisa ser autenticado toda vez, porque não como saber os usuarios que já autenticaram
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()//dizendo que esses endpoints não precisam de verficicação
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers("/api/books/**").permitAll()
                        .anyRequest().authenticated()//e dizendo que qualquer outra requisição a não ser login e register precisa de autenticação
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);//adicionao filtro do security filter pra pegar e validar o token do usuario para ser logado
        return http.build();
    }

    @Bean //criando beans de password enconder para não salvar uma string no banco (usado no controller)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //bean necessario para o security funcionar
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
