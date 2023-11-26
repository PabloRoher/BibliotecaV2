package io.bootify.biblioteca.rest;

import io.bootify.biblioteca.model.PrestamoDTO;
import io.bootify.biblioteca.service.PrestamoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/prestamos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrestamoResource {

    private final PrestamoService prestamoService;

    public PrestamoResource(final PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public ResponseEntity<List<PrestamoDTO>> getAllPrestamos() {
        return ResponseEntity.ok(prestamoService.findAll());
    }

    @GetMapping("/{idPrestamo}")
    public ResponseEntity<PrestamoDTO> getPrestamo(@PathVariable final Long idPrestamo) {
        return ResponseEntity.ok(prestamoService.get(idPrestamo));
    }

    @PostMapping
    public ResponseEntity<Long> createPrestamo(@RequestBody @Valid final PrestamoDTO prestamoDTO) {
        final Long createdIdPrestamo = prestamoService.create(prestamoDTO);
        return new ResponseEntity<>(createdIdPrestamo, HttpStatus.CREATED);
    }

    @PutMapping("/{idPrestamo}")
    public ResponseEntity<Long> updatePrestamo(@PathVariable final Long idPrestamo,
            @RequestBody @Valid final PrestamoDTO prestamoDTO) {
        prestamoService.update(idPrestamo, prestamoDTO);
        return ResponseEntity.ok(idPrestamo);
    }

    @DeleteMapping("/{idPrestamo}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable final Long idPrestamo) {
        prestamoService.delete(idPrestamo);
        return ResponseEntity.noContent().build();
    }

}
