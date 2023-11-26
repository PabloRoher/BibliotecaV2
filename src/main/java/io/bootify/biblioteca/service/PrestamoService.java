package io.bootify.biblioteca.service;

import io.bootify.biblioteca.domain.Lector;
import io.bootify.biblioteca.domain.Libro;
import io.bootify.biblioteca.domain.Prestamo;
import io.bootify.biblioteca.model.PrestamoDTO;
import io.bootify.biblioteca.repos.LectorRepository;
import io.bootify.biblioteca.repos.LibroRepository;
import io.bootify.biblioteca.repos.PrestamoRepository;
import io.bootify.biblioteca.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(final PrestamoRepository prestamoRepository,
            final LectorRepository lectorRepository, final LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    public List<PrestamoDTO> findAll() {
        final List<Prestamo> prestamoes = prestamoRepository.findAll(Sort.by("idPrestamo"));
        return prestamoes.stream()
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .toList();
    }

    public PrestamoDTO get(final Long idPrestamo) {
        return prestamoRepository.findById(idPrestamo)
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = new Prestamo();
        mapToEntity(prestamoDTO, prestamo);
        return prestamoRepository.save(prestamo).getIdPrestamo();
    }

    public void update(final Long idPrestamo, final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(NotFoundException::new);
        mapToEntity(prestamoDTO, prestamo);
        prestamoRepository.save(prestamo);
    }

    public void delete(final Long idPrestamo) {
        prestamoRepository.deleteById(idPrestamo);
    }

    private PrestamoDTO mapToDTO(final Prestamo prestamo, final PrestamoDTO prestamoDTO) {
        prestamoDTO.setIdPrestamo(prestamo.getIdPrestamo());
        prestamoDTO.setFechaInicioPrestamo(prestamo.getFechaInicioPrestamo());
        prestamoDTO.setFechaFinPrestamo(prestamo.getFechaFinPrestamo());
        prestamoDTO.setEstadoPrestamo(prestamo.getEstadoPrestamo());
        prestamoDTO.setLectorID(prestamo.getLectorID() == null ? null : prestamo.getLectorID().getId());
        prestamoDTO.setLector(prestamo.getLector() == null ? null : prestamo.getLector().getId());
        prestamoDTO.setLibro(prestamo.getLibro() == null ? null : prestamo.getLibro().getId());
        prestamoDTO.setLibroAsociado(prestamo.getLibroAsociado() == null ? null : prestamo.getLibroAsociado().getId());
        return prestamoDTO;
    }

    private Prestamo mapToEntity(final PrestamoDTO prestamoDTO, final Prestamo prestamo) {
        prestamo.setFechaInicioPrestamo(prestamoDTO.getFechaInicioPrestamo());
        prestamo.setFechaFinPrestamo(prestamoDTO.getFechaFinPrestamo());
        prestamo.setEstadoPrestamo(prestamoDTO.getEstadoPrestamo());
        final Lector lectorID = prestamoDTO.getLectorID() == null ? null : lectorRepository.findById(prestamoDTO.getLectorID())
                .orElseThrow(() -> new NotFoundException("lectorID not found"));
        prestamo.setLectorID(lectorID);
        final Lector lector = prestamoDTO.getLector() == null ? null : lectorRepository.findById(prestamoDTO.getLector())
                .orElseThrow(() -> new NotFoundException("lector not found"));
        prestamo.setLector(lector);
        final Libro libro = prestamoDTO.getLibro() == null ? null : libroRepository.findById(prestamoDTO.getLibro())
                .orElseThrow(() -> new NotFoundException("libro not found"));
        prestamo.setLibro(libro);
        final Libro libroAsociado = prestamoDTO.getLibroAsociado() == null ? null : libroRepository.findById(prestamoDTO.getLibroAsociado())
                .orElseThrow(() -> new NotFoundException("libroAsociado not found"));
        prestamo.setLibroAsociado(libroAsociado);
        return prestamo;
    }

}
