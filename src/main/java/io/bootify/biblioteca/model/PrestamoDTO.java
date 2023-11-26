package io.bootify.biblioteca.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrestamoDTO {

    private Long idPrestamo;

    @NotNull
    private LocalDate fechaInicioPrestamo;

    @NotNull
    private LocalDate fechaFinPrestamo;

    private EstadoPrestamo estadoPrestamo;

    @NotNull
    private Long lectorID;

    private Long lector;

    private Long libro;

    private Long libroAsociado;

}
