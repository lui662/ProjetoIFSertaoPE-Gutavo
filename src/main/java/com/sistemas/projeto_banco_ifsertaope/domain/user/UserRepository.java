package com.sistemas.projeto_banco_ifsertaope.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByAtivoTrue();
    Optional<User> findByCpf(String cpf);
}
