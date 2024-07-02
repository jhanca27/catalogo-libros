package com.jh.catalogolibros.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ConexionApi {
    
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    public String conexion(String nombreLibro){
        String url = URL_BASE + nombreLibro;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                            .uri(URI.create(url))
                                            .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            
            System.out.println("Error enviando la solicitud: " + e);
            return null;
        }
        System.out.println((response.statusCode()));
        return response.body();
           
    }
}
