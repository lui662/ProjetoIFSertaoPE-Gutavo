package com.sistemas.projeto_banco_ifsertaope.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistemas.projeto_banco_ifsertaope.domain.user.ClienteRepository;

@Service
public class CustomUserDetalisServer implements UserDetailsService {

    @Autowired
    private ClienteRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com CPF: " + username));
    return org.springframework.security.core.userdetails.User.withUsername(user.getCpf())
        .password(user.getSenha())
        .roles("CLIENTE")
        .disabled(!user.getAtivo())
        .build();
    }

}