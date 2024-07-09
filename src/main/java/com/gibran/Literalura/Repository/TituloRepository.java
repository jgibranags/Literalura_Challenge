package com.gibran.Literalura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.gibran.Literalura.Model.Titulo;
import com.gibran.Literalura.Model.Idioma;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
    List<Titulo> findByIdiomas(Idioma idioma);
}