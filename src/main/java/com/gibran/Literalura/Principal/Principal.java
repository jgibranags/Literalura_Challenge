package com.gibran.Literalura.Principal;

import com.gibran.Literalura.Model.*;
import com.gibran.Literalura.Repository.TituloRepository;
import com.gibran.Literalura.Service.ConsumoAPI;
import com.gibran.Literalura.Service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

import com.gibran.Literalura.Service.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner datosIngreso = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos convierte = new ConvierteDatos();
    private final TituloService tituloService;

    @Autowired
    public Principal(TituloService tituloService) {
        this.tituloService = tituloService;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                ------- MENU -------

                1.- Buscar libro por titulo
                2.- Mostrar todas las busquedas
                3.- Mostrar todos los autores
                4.- Mostrar autores por año de nacimiento
                5.- Mostrar libros por idioma
                0.- Salir

                -------------------

                Selecciona una opcion:
                """);
            if (datosIngreso.hasNextInt()) {
                opcion = datosIngreso.nextInt();
                datosIngreso.nextLine();
                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        mostarLibros();
                        break;
                    case 3:
                        mostrarAutores();
                        break;
                    case 4:
                        mostrarAutoresAno();
                        break;
                    case 5:
                        mostrarLibrosIdioma();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Ingresa una opcion valida");
                        break;
                }
            } else {
                datosIngreso.nextLine(); // Consumir la entrada inválida
                System.out.println("Ingresa una opcion valida");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Ingresa el nombre del titulo a buscar: ");
        var titulo = datosIngreso.nextLine();
        var json = ConsumoAPI.obtenerDatos(URL_BASE + "?search=" + titulo.toLowerCase().replace(" ","+"));
        System.out.println(json);

        // Asumiendo que 'convierte' es un objeto que convierte JSON a un objeto Java
        var datosBusqueda = convierte.obtenerDatos(json, Datos.class);

        // Filtra los resultados para encontrar el libro
        Optional<DatosLibros> librosBuscados = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();


        if (librosBuscados.isPresent()) {
            System.out.println("Libro encontrado");

            DatosLibros datosLibros = librosBuscados.get();
            String nombres = datosLibros.autor().stream()
                    .map(DatosAutor::nombre)
                    .collect(Collectors.joining(", "));


            String fechasDeNacimiento = datosLibros.autor().stream()
                    .map(DatosAutor::fechaDeNacimiento)
                    .collect(Collectors.joining(", "));

            Autor autor = new Autor(nombres, fechasDeNacimiento);

            Titulo tituloEncontrado = new Titulo(datosLibros, autor);
            tituloService.InsertarTituloAutor(tituloEncontrado);

        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostarLibros() {
        tituloService.ObtenerLibros();
    }

    private void mostrarAutores() {
        tituloService.ObtenerAutores();
    }

    private void mostrarAutoresAno() {
        System.out.println("Ingresa el año del autor");
        var fecha = datosIngreso.nextLine();
        tituloService.buscarPorAno(fecha);

    }

    private void mostrarLibrosIdioma() {
        System.out.println("""
                De la siguiente lista ingresa el idioma que quieras ver:
                es- Español
                en- Ingles
                fr- Frances
                pt- Portugues
                
                Ingresa el idioma:
                """);
        String idiomaInput = datosIngreso.nextLine().trim().toLowerCase(); // Lee la entrada del usuario y elimina espacios en blanco

        Idioma idioma = null;

        // Intenta buscar por nombre completo
        try {
            idioma = Idioma.fromCompleto(idiomaInput);
        } catch (IllegalArgumentException ignored) {}

        // Si no se encontró por nombre completo, intenta buscar por diminutivo
        if (idioma == null) {
            try {
                idioma = Idioma.fromDiminutivo(idiomaInput);
            } catch (IllegalArgumentException ignored) {}
        }

        // Si se encontró un idioma válido, llama al servicio para buscar por idioma
        if (idioma != null) {
            tituloService.buscarPorIdioma(idioma);
        } else {
            System.out.println("Idioma no válido. Por favor, ingresa un idioma válido.");
        }
    }
}


