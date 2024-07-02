package com.jh.catalogolibros.service;


import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDatos implements IConversDatos{
    
    ObjectMapper mapeador = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            JsonNode jsonNode = mapeador.readValue(json, JsonNode.class);
            JsonNode results = jsonNode.get("results");
            List<T> listaDatos = mapeador.readValue(results.toString(), mapeador.getTypeFactory().constructCollectionType(List.class, clase));
            Optional<T> optional = listaDatos.stream().findFirst();
            if(optional.isPresent()){
                return optional.get();
            }else{
                System.out.println("no se encontró nada relacionado.");
                return null;
            } 
        } catch (Exception e) {
            throw new RuntimeException("Error en el mapeo de datos: " + e);        
        }
    }
    


}
