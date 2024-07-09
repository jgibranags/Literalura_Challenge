package com.gibran.Literalura.Model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Titulo")
public class Titulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Enumerated(EnumType.STRING)
    @Column(name = "idioma")
    private Idioma idiomas;

    public Titulo() {}


    public Titulo(DatosLibros datosLibros, Autor autor) {
        this.titulo = datosLibros.titulo();
        this.autor = autor;
        this.idiomas = Idioma.fromDiminutivo(datosLibros.idiomas().toString());
    }


    private static List<Idioma> procesarIdiomas(String idiomasStr) {
        // Eliminar corchetes y dividir por comas
        return Arrays.stream(idiomasStr.replace("[", "").replace("]", "").split(","))
                .map(Idioma::fromDiminutivo)
                .collect(Collectors.toList());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }
}
