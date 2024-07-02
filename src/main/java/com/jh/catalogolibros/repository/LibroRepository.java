package com.jh.catalogolibros.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jh.catalogolibros.model.Idioma;
import com.jh.catalogolibros.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>{
    boolean existsByNombre(String nombre);

    @Query("Select l from Libro l")
    List<Libro> todosLibros();

    @Query("SELECT i FROM Libro l JOIN l.idiomas i where i.nombre =:nombreIdioma")
    Optional<Idioma> findIdioma(String nombreIdioma);

    @Query("SELECT l FROM Libro l JOIN l.idiomas i where i.nombre =:nombreIdioma")
    List<Libro> findLibrosByIdioma(String nombreIdioma);
}
