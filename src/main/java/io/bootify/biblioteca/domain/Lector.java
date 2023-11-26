package io.bootify.biblioteca.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Lectors")
@Getter
@Setter
public class Lector extends Usuario {

    @OneToMany(mappedBy = "lector")
    private Set<Prestamo> prestamos;

}
