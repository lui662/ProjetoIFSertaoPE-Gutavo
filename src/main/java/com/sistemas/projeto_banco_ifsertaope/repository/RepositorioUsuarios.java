package com.sistemas.projeto_banco_ifsertaope.repository;

import com.sistemas.projeto_banco_ifsertaope.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuarios extends JpaRepository<User, String> {
}
