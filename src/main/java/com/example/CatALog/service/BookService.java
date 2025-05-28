package com.example.CatALog.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private static final String API_KEY = "AIzaSyAxgTPQuPWbwD0qg0lKK1-qHV2PMKjLjLk";

    public String searchBooks(String query) throws IOException, InterruptedException {

        String encodeQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + encodeQuery
                + "&maxResults=20&langRestrict=en&key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

}
