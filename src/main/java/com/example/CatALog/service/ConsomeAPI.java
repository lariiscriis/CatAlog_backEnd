package com.example.CatALog.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ConsomeAPI {

    private static final String API_KEY = "AIzaSyAxgTPQuPWbwD0qg0lKK1-qHV2PMKjLjLk";

    public static JsonObject buscarLivrosPorGenero(String genero) throws IOException, InterruptedException {
        String encodedGenero = URLEncoder.encode(genero, StandardCharsets.UTF_8);
        String url = "https://www.googleapis.com/books/v1/volumes?q=subject:" + encodedGenero
            + "&maxResults=40&langRestrict=pt&orderBy=relevance&key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return JsonParser.parseString(response.body()).getAsJsonObject();
    }
}
