package com.jh.catalogolibros.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDatos(
    @JsonAlias("title")String nombre,
    @JsonAlias("authors") List<AutoresDatos> autores,
    @JsonAlias("languages") List<String> lenguajes,
    @JsonAlias("download_count") Integer descargas

) {
    
}
