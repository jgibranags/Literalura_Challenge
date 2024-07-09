package com.gibran.Literalura.Service;

import com.gibran.Literalura.Model.Autor;
import com.gibran.Literalura.Model.Idioma;
import com.gibran.Literalura.Model.Titulo;
import com.gibran.Literalura.Repository.AutorRepository;
import com.gibran.Literalura.Repository.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TituloService {
    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private AutorRepository autorRepository;

    private List<Titulo> titulos;
    private List<Autor> autores;

    public void InsertarTituloAutor(Titulo titulo){
        try {
            Autor autor = titulo.getAutor();
            autorRepository.save(autor);
            tituloRepository.save(titulo);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Este libro ya esta registrado");
        } catch (Exception e){
            System.out.println("Error con el registro");
        }
    }

    public void ObtenerLibros(){
        titulos = tituloRepository.findAll();

        titulos.stream()
                .map(titulo -> """
                           ------------------
                           
                           Título:\s""" + titulo.getTitulo() + """
                           \nAutor:\s""" + titulo.getAutor().getNombre() + """
                           \nIdiomas:\s""" + titulo.getIdiomas() + """
                           \n
                           ------------------
                           """)
                .forEach(System.out::println);

    }

    public void ObtenerAutores(){
        autores = autorRepository.findAll();

        autores.stream()
                .map(autor -> """
                        -------------------
                        \nNombre:\s""" + autor.getNombre() + """
                        \nNacimiento:\s"""  + autor.getFechaDeNacimiento() + """
                        \n
                        -------------------
                        """)
                .forEach(System.out::println);
    }

    public void buscarPorAno(String fecha) {
        List<Autor> autores = autorRepository.findByFechaDeNacimientoContainingIgnoreCase(fecha);

        if (!autores.isEmpty()) {
            System.out.println("Se encontraron los siguientes autores con ese año:");
            for (Autor autor : autores) {
                System.out.println("- " + autor.getNombre());
            }
        } else {
            System.out.println("No se encontró ningún autor con ese año.");
        }
    }

    public void buscarPorIdioma(Idioma idioma) {
        List<Titulo> titulos = tituloRepository.findByIdiomas(idioma);

        if (!titulos.isEmpty()) {
            System.out.println("Se encontraron los siguientes títulos en " + idioma.getCompleto() + ":");
            for (Titulo titulo : titulos) {
                System.out.println("- " + titulo.getTitulo());
            }
        } else {
            System.out.println("No se encontraron títulos en " + idioma.getCompleto());
        }
    }
}
