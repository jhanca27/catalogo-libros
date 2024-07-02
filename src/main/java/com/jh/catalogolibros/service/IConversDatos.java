package com.jh.catalogolibros.service;

public interface IConversDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
