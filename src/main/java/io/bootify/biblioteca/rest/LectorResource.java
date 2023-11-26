package io.bootify.biblioteca.rest;

import io.bootify.biblioteca.model.LectorDTO;
import io.bootify.biblioteca.service.LectorService;
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
@RequestMapping(value = "/api/lectors", produces = MediaType.APPLICATION_JSON_VALUE)
public class LectorResource {

    private final LectorService lectorService;

    public LectorResource(final LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @GetMapping
    public ResponseEntity<List<LectorDTO>> getAllLectors() {
        return ResponseEntity.ok(lectorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectorDTO> getLector(@PathVariable final Long id) {
        return ResponseEntity.ok(lectorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLector(@RequestBody @Valid final LectorDTO lectorDTO) {
        final Long createdId = lectorService.create(lectorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLector(@PathVariable final Long id,
            @RequestBody @Valid final LectorDTO lectorDTO) {
        lectorService.update(id, lectorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLector(@PathVariable final Long id) {
        lectorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
