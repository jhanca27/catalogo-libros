package com.jh.catalogolibros.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer annoNacimiento;
    private Integer annoMuerte;
    private String nombre;
    @ManyToMany(mappedBy = "autores") 
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(AutoresDatos a){
        this.annoNacimiento = a.annoNacimiento();
        this.annoMuerte = a.annoMuerte();
        this.nombre = a.nombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnoNacimiento() {
        return annoNacimiento;
    }

    public void setAnnoNacimiento(Integer annoNacimiento) {
        this.annoNacimiento = annoNacimiento;
    }

    public Integer getAnnoMuerte() {
        return annoMuerte;
    }

    public void setAnnoMuerte(Integer annoMuerte) {
        this.annoMuerte = annoMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

	@Override
	public String toString() {
		return "Autor [nombre=" + nombre + "]";
	}

    
}
