package com.sistemas.projeto_banco_ifsertaope.infra.security;

import com.sistemas.projeto_banco_ifsertaope.domain.user.Cliente;
import com.sistemas.projeto_banco_ifsertaope.domain.user.ClienteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class FiltroDeSeguranca extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = recuperarToken(request);
        String cpf = tokenService.validateToken(token); // retorna o CPF contido no JWT

        if (cpf != null) {
        Cliente user = userRepository.findByCpf(cpf)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Define a autoridade padrão; pode ser substituída pela role do usuário
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            // Cria o objeto de autenticação e insere no contexto de segurança
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
