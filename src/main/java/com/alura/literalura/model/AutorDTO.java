package com.alura.literalura.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias("birth_year") Integer anoNascimento,
        @JsonAlias("death_year") Integer anoMorte,
        @JsonAlias("name") String nome
) {
    // Retorna nome ou string vazia se vier nulo
    public String getNome() {
        return nome != null ? nome : "";
    }

    // Se preferir, retorne 0, -1 ou null:
    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }
}
