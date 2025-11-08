package com.sistemas.projeto_banco_ifsertaope.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findAllByAtivoTrue();
    Optional<Cliente> findByCpf(String cpf);
}
