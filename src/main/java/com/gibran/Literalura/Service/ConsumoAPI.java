package com.gibran.Literalura.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;

public class ConsumoAPI {
    public static String obtenerDatos(String url) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(Redirect.ALWAYS)  // Permitir seguimiento de redirecciones
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
}
