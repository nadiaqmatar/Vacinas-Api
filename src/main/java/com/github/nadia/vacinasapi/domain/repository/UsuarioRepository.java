package com.github.nadia.vacinasapi.domain.repository;

import com.github.nadia.vacinasapi.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByCpf(String cpf);

    public Optional<Usuario> findByEmail(String email);

}
