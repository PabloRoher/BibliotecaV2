package io.bootify.biblioteca.repos;

import io.bootify.biblioteca.domain.Lector;
import io.bootify.biblioteca.domain.Libro;
import io.bootify.biblioteca.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Prestamo findFirstByLectorID(Lector lector);

    Prestamo findFirstByLector(Lector lector);

    Prestamo findFirstByLibro(Libro libro);

    Prestamo findFirstByLibroAsociado(Libro libro);

}
