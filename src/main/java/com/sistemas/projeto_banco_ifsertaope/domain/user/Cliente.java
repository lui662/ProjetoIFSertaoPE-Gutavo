package com.sistemas.projeto_banco_ifsertaope.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import com.sistemas.projeto_banco_ifsertaope.DTO.ClienteDTO;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean ativo;

    public Cliente(ClienteDTO data) {
        this.nome = data.nome();
        this.email = data.email();
        this.cpf = data.cpf();
        this.ativo = true;
    }

    private Cliente(String cpf, String senha, String nome, String email, Boolean ativo) {
        this.cpf = cpf;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.ativo = true;
    }

    public static class ClienteBuilder {
        private String cpf;
        private String senha;
        private String nome;
        private String email;
        private Boolean ativo;

        public ClienteBuilder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public ClienteBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public ClienteBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public ClienteBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ClienteBuilder ativo(Boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public Cliente build() {
            return new Cliente(cpf, senha, nome, email, ativo);
        }
    }
}
