package com.alura.literalura.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(
        Integer id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDTO> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer downloads
) {
    // Construtor auxiliar para inicializar campos com valores padrão (null-safe)
    public LivroDTO {
        titulo = titulo != null ? titulo : "";
        autores = autores != null ? autores : List.of();
        idiomas = idiomas != null ? idiomas : List.of();
        downloads = downloads != null ? downloads : 0;
    }
}
