package com.example.CatALog.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.CatALog.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//classe especifica para cuidar da segurança da aplicação, gerenciamento de tokens
@Service //indica que é um componente para o spring, para injeção de dependencias
public class TokenService {
    @Value("${api.security.token.secret}") // pegando a secret do aplication properties
    private String secret;
    //metodo de geração do token para quando o usuario fazer login etc
    public String generateToken(User user){
        try{
            //definindo algoritmos(criptografia) usado pra gerar o token
             Algorithm algorithm = Algorithm.HMAC256(secret); //passando secretkey, "chave" para conseguir acessar as informacoes hashs

            //microservico que gerou o token
            String token = JWT.create()
                    .withIssuer("CatALog_BackEnd")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generationExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
     }

     //validação do token
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("CatALog_BackEnd")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return null;
        }
    }

     //funcao para dizer o tempo de expiracao do token
    private Instant generationExpirationDate(){
        return LocalDateTime.now()
                .plusHours(2).toInstant(ZoneOffset.of("-03:00"));    }
}
