package com.sistemas.projeto_banco_ifsertaope.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.sistemas.projeto_banco_ifsertaope.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServicy {

    @Value("${token.personalizado.para.criacao.da.chave.de.autenticacao}")
    private String chavePrivada;


    public String gerandoToken(User user){
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

    private Instant enviarHoraDeExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
