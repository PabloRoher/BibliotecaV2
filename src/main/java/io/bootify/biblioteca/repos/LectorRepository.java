package io.bootify.biblioteca.repos;

import io.bootify.biblioteca.domain.Lector;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectorRepository extends JpaRepository<Lector, Long> {
}
