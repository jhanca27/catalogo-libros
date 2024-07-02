package com.jh.catalogolibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutoresDatos(
    @JsonAlias("birth_year") Integer annoNacimiento,
    @JsonAlias("death_year") Integer annoMuerte,
    @JsonAlias("name")String nombre
) {

}
