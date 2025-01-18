package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
    public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Livro> livros = new HashSet<>();

    public Autor() {
    }
    public static Autor fromAutorDto(AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.nome = autorDTO.getNome();        // Usa o getter customizado que lida com null
        autor.anoNascimento = autorDTO.getAnoNascimento();
        autor.anoFalecimento = autorDTO.getAnoMorte();
        return autor;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        var texto = nome;
        if (anoNascimento != null && anoFalecimento != null) {
            texto += " (" + anoNascimento + "-" + anoFalecimento + ")";
        } else if (anoNascimento != null) {
            texto += " (" + anoNascimento + "-)";
        } else if (anoFalecimento != null) {
            texto += " (-" + anoFalecimento + ")";
        }
        return texto;
    }
}
