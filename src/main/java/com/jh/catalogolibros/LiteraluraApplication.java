package com.jh.catalogolibros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jh.catalogolibros.principal.Menu;
import com.jh.catalogolibros.repository.AutorRepository;
import com.jh.catalogolibros.repository.IdiomaRepository;
import com.jh.catalogolibros.repository.LibroRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner{
	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private IdiomaRepository idiomaRepository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu(libroRepository, idiomaRepository, autorRepository);
		menu.mostrarMenu();
	}

}
