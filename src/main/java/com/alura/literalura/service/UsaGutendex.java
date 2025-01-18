package com.alura.literalura.service;

import com.alura.literalura.model.AutorDTO;
import com.alura.literalura.model.LivroDTO;
import com.alura.literalura.model.UsaGutendexRetorno;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UsaGutendex {
    private final String baseUrl = "https://gutendex.com/books/";

    public List<LivroDTO> search(String text) {
        var json = APIConsumer.getData(
                baseUrl + "?search="
                        + URLEncoder.encode(text.trim().toLowerCase(), StandardCharsets.UTF_8)
        );
        return new TratadorDeDados().convert(json, UsaGutendexRetorno.class).livros();
    }

    public List<AutorDTO> getAuthorsAliveOnYear(Integer year) {
        var endpoint = baseUrl + "?author_year_start=" + year
                + "&author_year_end=" + year;
        var json = APIConsumer.getData(endpoint);
        return new TratadorDeDados()
                .convert(json,  UsaGutendexRetorno.class)
                .livros()
                .stream()
                .flatMap(l -> l.autores().stream())
                .toList();
    }
}
