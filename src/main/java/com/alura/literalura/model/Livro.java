package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
   @Table(name = "livros")
   public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer downloads;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Autor autor;

    @Version
    private Integer version; // Controle de concorrência otimista

    public static Livro fromLivroDTO(LivroDTO livroDTO) {
        Livro livro = new Livro();

        livro.titulo = Optional.ofNullable(livroDTO.titulo())
                .filter(t -> !t.isBlank())
                .orElse("Título não informado");


        livro.downloads = Optional.ofNullable(livroDTO.downloads())
                .orElse(0);

        livro.idioma = livroDTO.idiomas().stream()
                .findFirst()
                .orElse("Idioma desconhecido");

        livro.autor = livroDTO.autores().stream()
                .findFirst()
                .map(Autor::fromAutorDto)
                .orElse(null);

        return livro;
    }
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo +
                "\nAutor(a): " + (autor == null ? "Desconhecido" : autor.toString()) +
                "\nIdioma: " + (idioma == null ? "Desconhecido" : idioma) +
                "\nNúmero de downloads: " + downloads;
    }
}
