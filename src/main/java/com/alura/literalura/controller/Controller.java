package com.alura.literalura.controller;

import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.UsaGutendex;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Controller {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final UsaGutendex gutendexAPI;
    private final Scanner entrada = new Scanner(System.in);
    private final List<String> idiomasSuportados;

    private final String URL_BASE = "https://gutendex.com/books";
    private String parametroBuscaTitulo = "?search=";
    private String parametroBuscaIdioma = "?languages=";

    public Controller(LivroRepository livroRepository,
                      AutorRepository autorRepository,
                      UsaGutendex gutendexAPI) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.gutendexAPI = gutendexAPI;

        this.idiomasSuportados = new ArrayList<>();
        this.idiomasSuportados.add("pt");
        this.idiomasSuportados.add("en");
        this.idiomasSuportados.add("es");
        this.idiomasSuportados.add("fr");
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    |////////////////////////////////////////////////|
                    
                    [1] - Buscar Livro pelo Título
                    [2] - Listar Todos os Livros
                    [3] - Listar Todos os Autores
                    [4] - Listar Autores por Ano
                    [5] - Listar Livros por Idioma
                    [0] - Sair
                    
                    |////////////////////////////////////////////////|
                    """;

            System.out.println(menu);
            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosCadastrados();
                case 3 -> listarAutores();
                case 4 -> listarAutoresPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Saindo do programa...");
                default -> System.out.println("\n[!] Opção inválida! Tente novamente.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("\nBUSCA POR TÍTULO ********************************************");
        System.out.print("Digite o título do livro: ");
        var titulo = entrada.nextLine();

        System.out.println("\nPesquisando...\n");
        var dadosLivro = gutendexAPI.search(titulo).stream().findFirst();

        if (dadosLivro.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título informado.\n");
        } else {
            var livroDTO = dadosLivro.get();
            var livro = Livro.fromLivroDTO(livroDTO);

            livroRepository.save(livro);

            System.out.println("Livro encontrado e salvo: " + livro + "\n");
        }
    }

    private void listarLivrosCadastrados() {
        System.out.println("\nLIVROS CADASTRADOS ********************************************");
        var livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.\n");
        } else {
            livros.forEach(livro -> System.out.println(livro + "\n"));
        }
    }

    private void listarAutores() {
        System.out.println("\nLISTAGEM DE AUTORES ********************************************");
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado no banco de dados.\n");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresPorAno() {
        System.out.println("\nBUSCAR AUTORES POR ANO ********************************************");
        System.out.print("Digite o ano desejado: ");
        var ano = entrada.nextInt();

        System.out.println("\nPesquisando...\n");
        var autores = autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado para o ano informado.\n");
        } else {
            autores.forEach(System.out::println);
        }
        System.out.println(" ");
    }

    private void listarLivrosPorIdioma() {
        System.out.println("\nBUSCAR LIVROS POR IDIOMA ********************************************");

        boolean idiomaValido = false;
        String idioma = "";
        while (!idiomaValido) {
            System.out.println("Escolha um idioma suportado:");
            System.out.println("[pt] - Português");
            System.out.println("[en] - Inglês");
            System.out.println("[es] - Espanhol");
            System.out.println("[fr] - Francês");
            System.out.print("Informe o idioma: ");

            var entradaUsuario = entrada.nextLine();
            idiomaValido = idiomasSuportados.stream()
                    .anyMatch(id -> id.equalsIgnoreCase(entradaUsuario));

            if (!idiomaValido) System.out.println("\nIdioma inválido. Tente novamente.\n");
            idioma = entradaUsuario;
        }
        var livros = livroRepository.findByIdioma(idioma);
        var quantidade = livroRepository.countByIdioma(idioma);

        System.out.println("\nForam encontrados " + quantidade + " livro(s) no idioma selecionado.");
        livros.forEach(livro -> System.out.println(livro + "\n"));
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma selecionado.\n");
        }
    }
}

