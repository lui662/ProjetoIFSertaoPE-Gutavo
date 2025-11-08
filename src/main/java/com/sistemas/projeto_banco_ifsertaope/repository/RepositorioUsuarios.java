package com.sistemas.projeto_banco_ifsertaope.repository;

import com.sistemas.projeto_banco_ifsertaope.domain.user.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RepositorioUsuarios extends JpaRepository<Cliente, UUID> {
}
