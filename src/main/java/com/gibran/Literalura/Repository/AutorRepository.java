package com.gibran.Literalura.Repository;

import com.gibran.Literalura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByFechaDeNacimientoContainingIgnoreCase(String fecha);
}
