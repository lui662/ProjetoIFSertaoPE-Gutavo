 package com.sistemas.projeto_banco_ifsertaope.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sistemas.projeto_banco_ifsertaope.domain.user.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${token.personalizado.para.criacao.da.chave.de.autenticacao}")
    private String chavePrivada;


    public String generateToken(Cliente user){
        try {

            Algorithm algorithm = Algorithm.HMAC256(chavePrivada);

            String token = JWT.create()
                    .withIssuer("autenticacao-login")
                    .withSubject(user.getCpf())
                    .withExpiresAt(this.enviarHoraDeExpiracao())
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(chavePrivada);
            return JWT.require(algorithm)
                    .withIssuer("autenticacao-login")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token inválido ou expirado.", e);
        }
    }

    private Instant enviarHoraDeExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
