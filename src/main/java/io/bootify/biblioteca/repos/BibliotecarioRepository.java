package io.bootify.biblioteca.repos;

import io.bootify.biblioteca.domain.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}
