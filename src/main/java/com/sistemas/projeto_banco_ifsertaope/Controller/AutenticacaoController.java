package com.sistemas.projeto_banco_ifsertaope.Controller;

import java.security.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.projeto_banco_ifsertaope.DTO.ClienteDTO;
import com.sistemas.projeto_banco_ifsertaope.domain.user.Cliente;
import com.sistemas.projeto_banco_ifsertaope.domain.user.ClienteRepository;
import com.sistemas.projeto_banco_ifsertaope.infra.security.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {
    
    private  final ClienteRepository clienteRepository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity autenticar(@RequestBody ClienteDTO body) {
        // Lógica de autenticação será implementada aqui
        Cliente cliente = clienteRepository.findByCpf(body.cpf())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(!cliente.getSenha().equals(body.senha())) {
            return ResponseEntity.status(401).body("Senha inválida");
        } 

        String token = tokenService.generateToken(cliente);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/Registro")
    public ResponseEntity registrar(@RequestBody ClienteDTO body) {
        // Lógica de registro será implementada aqui
        if (clienteRepository.findByCpf(body.cpf()).isPresent()) {
            return ResponseEntity.status(400).body("CPF já cadastrado");
        }

        Cliente novoCliente = new Cliente.ClienteBuilder()
            .nome(body.nome())
            .email(body.email())
            .cpf(body.cpf())
            .senha(body.senha())
            .ativo(true)
            .build();

        clienteRepository.save(novoCliente);
        return ResponseEntity.status(201).body("Cliente registrado com sucesso");
    }


}
