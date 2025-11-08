package com.sistemas.projeto_banco_ifsertaope.DTO;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(UUID id,
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String cpf) {
}