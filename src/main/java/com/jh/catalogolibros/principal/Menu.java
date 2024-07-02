package com.jh.catalogolibros.principal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.modelmapper.ModelMapper;

import com.jh.catalogolibros.model.Autor;
import com.jh.catalogolibros.model.AutoresDatos;
import com.jh.catalogolibros.model.Idioma;
import com.jh.catalogolibros.model.Libro;
import com.jh.catalogolibros.model.LibroDatos;
import com.jh.catalogolibros.repository.AutorRepository;
import com.jh.catalogolibros.repository.IdiomaRepository;
import com.jh.catalogolibros.repository.LibroRepository;
import com.jh.catalogolibros.service.ConexionApi;
import com.jh.catalogolibros.service.ConversorDatos;

import jakarta.transaction.Transactional;

public class Menu {
    private Scanner teclado = new Scanner(System.in);
    private ConexionApi api = new ConexionApi();
    private ConversorDatos conversor = new ConversorDatos();
    private LibroRepository repository;
    private ModelMapper mapper;
    private IdiomaRepository idiomaRepository;
    private AutorRepository autorRepository;

    public Menu(LibroRepository s , IdiomaRepository i ,AutorRepository a){
        this.repository = s;
        this.idiomaRepository = i;
        this.autorRepository = a;
    }


    public void mostrarMenu(){
        var opcion = -1;
        System.out.println("Elije una opción:");
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma          
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    getLibros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    autoresViviosEn();
                    break;
                case 5:
                    buscarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void autoresViviosEn(){
        System.out.println("Ingrese fecha: ");
        Integer fecha = Integer.valueOf(teclado.nextLine());
        List<Autor> autores = autorRepository.fechaVivioEntre(fecha);
        autores.forEach(System.out::println);
    }

	private void buscarLibrosIdioma() {
		System.out.println("Ingresa el idioma : ");
        String idioma = teclado.nextLine();

        List<Libro> librosIdioma = repository.findLibrosByIdioma(idioma);
        librosIdioma.forEach(System.out::println);
	}


	private void buscarAutores() {
		List<Autor> autores = autorRepository.findAll();
        autores.forEach(a -> System.out.println(a.getNombre()));
	}


	public void buscarLibros(){
        mapper = new ModelMapper();
        System.out.println("Ingresa el nombre de un libro: ");
        String nombreLibro = teclado.nextLine();
        try {
			nombreLibro = URLEncoder.encode(nombreLibro, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Codificación del código no se ha logardo: "+ nombreLibro);
            nombreLibro = "";
		} 
        System.out.println(nombreLibro);
        if(nombreLibro != ""){
            String json = api.conexion(nombreLibro);
            LibroDatos libroDatos = conversor.obtenerDatos(json, LibroDatos.class);

            if(libroDatos!= null){
                Libro libro = new Libro(libroDatos);
                Libro libroSave;
                if(!repository.existsByNombre(libro.getNombre())){
                    libroSave = validarIdioma(libro);
                    libroSave = validarAutor(libroSave);
                    System.out.println(repository.save(libroSave));
                }else{
                    System.out.println("El libro ya esta en la base de datos.");
                }
                
            }else{
                System.out.println("No se encontró ningún libro ");
            }
        }
    }

    public void getLibros(){
        List<Libro> listaLibros = repository.todosLibros();
        for (Libro libro : listaLibros) {
            System.out.println(libro);
        }
    }
    @Transactional
    private Libro validarIdioma(Libro libro){
        List<Idioma> listaIdioma = new ArrayList<>();
        for(Idioma idioma : libro.getIdiomas()){
            Optional<Idioma> selectIdioma = repository.findIdioma(idioma.getNombre());
            if(selectIdioma.isPresent()){
                listaIdioma.add(selectIdioma.get());
            }else{
                idiomaRepository.save(idioma);
                listaIdioma.add(idioma);
            }
        }
        libro.setIdiomas(listaIdioma);

        return libro;
    } 

    private Libro validarAutor(Libro libro){
        List<Autor> listaAutores = new ArrayList<>();
        for(Autor autor : libro.getAutores()){
            if(autorRepository.existsByNombre(autor.getNombre())){
                System.out.println("existe");
                listaAutores.add(autorRepository.findByNombreIgnoreCase(autor.getNombre()).get());
            }else{
                autorRepository.save(autor);
                listaAutores.add(autor);
            }
        }
        libro.setAutores(listaAutores);

        return libro;
    } 

}
