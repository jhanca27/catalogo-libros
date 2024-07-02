package com.jh.catalogolibros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jh.catalogolibros.model.Autor;

public interface AutorRepository extends JpaRepository<Autor,Long>{
    boolean existsByNombre(String nombre);

    Optional<Autor> findByNombreIgnoreCase(String nombre);
    

    @Query("SELECT a from Autor a where a.annoNacimiento < :fecha AND a.annoMuerte > :fecha")
    List<Autor> fechaVivioEntre(Integer fecha);
}
